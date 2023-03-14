package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.logic.renovator.Updater;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GiftCertificateServiceImpl extends AbstractService<GiftCertificate> implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final Updater<GiftCertificate> updater;
    private final Updater<Tag> tagUpdater;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao,
                                      Updater<GiftCertificate> updater, Updater<Tag> tagUpdater) {
        super(giftCertificateDao);
        this.giftCertificateDao = giftCertificateDao;
        this.updater = updater;
        this.tagUpdater = tagUpdater;
    }

    @Override
    @Transactional
    public GiftCertificate insert(GiftCertificate giftCertificate) {

        String giftCertificateName = giftCertificate.getName();

        giftCertificateDao.findByName(giftCertificateName).ifPresent(giftCertificate1 -> {
            throw new DuplicateEntityException(ExceptionMessageKey.GIFT_CERTIFICATE_EXIST);
        });

        removeDuplicateTags(giftCertificate);
        List<Tag> tagsToPersist = tagUpdater.updateListFromDatabase(giftCertificate.getTags());
        giftCertificate.setTags(tagsToPersist);
        return dao.insert(giftCertificate);
    }

    @Override
    @Transactional
    public void removeById(long id) {

        giftCertificateDao.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY));
        giftCertificateDao.removeGiftCertificateHasTag(id);
        giftCertificateDao.removeById(id);
    }

    @Override
    @Transactional
    public GiftCertificate update(long id, GiftCertificate giftCertificate) {
        GiftCertificate oldGiftCertificate = dao.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY));

        String giftCertificateName = giftCertificate.getName();
        boolean isGiftCertificateExist = giftCertificateDao.findByName(giftCertificateName).isPresent();
        if (isGiftCertificateExist && !oldGiftCertificate.getName().equals(giftCertificateName)) {
            throw new DuplicateEntityException(ExceptionMessageKey.GIFT_CERTIFICATE_EXIST);
        }

        removeDuplicateTags(giftCertificate);
        giftCertificate.setTags(tagUpdater.updateListFromDatabase(giftCertificate.getTags()));
        return giftCertificateDao.update(updater.updateObject(giftCertificate, oldGiftCertificate));
    }




    @Override
    public List<GiftCertificate> search(MultiValueMap<String, String> requestParams, int page, int size) {
        Pageable pageRequest = createPageRequest(page, size);
        return giftCertificateDao.search(requestParams, pageRequest);
    }

    private void removeDuplicateTags(GiftCertificate giftCertificate) {
        List<Tag> tags = giftCertificate.getTags();
        if (tags != null) {
            Set<Tag> uniqueTags = new HashSet<>(tags);
            giftCertificate.setTags(new ArrayList<>(uniqueTags));
        }
    }

}


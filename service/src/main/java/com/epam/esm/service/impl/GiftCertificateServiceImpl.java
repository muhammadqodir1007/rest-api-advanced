package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.converter.impl.GiftDtoConverter;
import com.epam.esm.dto.response.ApiResponse;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.*;
import com.epam.esm.logic.renovator.Updater;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final Updater<GiftCertificate> updater;
    private final Updater<Tag> tagUpdater;
    private final GiftDtoConverter giftDtoConverter;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao,
                                      Updater<GiftCertificate> updater, Updater<Tag> tagUpdater, GiftDtoConverter giftDtoConverter) {

        this.giftCertificateDao = giftCertificateDao;
        this.updater = updater;
        this.tagUpdater = tagUpdater;


        this.giftDtoConverter = giftDtoConverter;
    }

    @Override
    public GiftCertificateDto getById(long id) {

        GiftCertificate giftCertificate = giftCertificateDao.findById(id).orElseThrow(() -> new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY));
        return giftDtoConverter.convertToDto(giftCertificate);

    }

    @Override
    public List<GiftCertificateDto> getAll(int page, int size) {
        Pageable pageRequest = createPageRequest(page, size);
        List<GiftCertificate> certificates = giftCertificateDao.findAll(pageRequest);
        return certificates.stream()
                .map(giftDtoConverter::convertToDto)
                .collect(Collectors.toList());


    }

    @Override
    @Transactional
    public GiftCertificateDto insert(GiftCertificateDto giftCertificateDto) {
        String giftCertificateName = giftCertificateDto.getName();
        giftCertificateDao.findByName(giftCertificateName)
                .ifPresent(existingGiftCertificate -> {
                    throw new DuplicateEntityException(ExceptionMessageKey.GIFT_CERTIFICATE_EXIST);
                });

        GiftCertificate newGiftCertificate = giftDtoConverter.convertToEntity(giftCertificateDto);
        removeDuplicateTags(newGiftCertificate);
        List<Tag> tagsToPersist = tagUpdater.updateListFromDatabase(newGiftCertificate.getTags());
        newGiftCertificate.setTags(tagsToPersist);

        return giftDtoConverter.convertToDto(giftCertificateDao.insert(newGiftCertificate));
    }


    @Override
    @Transactional
    public ApiResponse removeById(long id) {
        giftCertificateDao.removeGiftCertificateHasTag(id);
        giftCertificateDao.removeById(id);
        return new ApiResponse(true, "deleted");
    }

    @Override
    @Transactional
    public GiftCertificateDto update(long id, GiftCertificateDto updatedGiftCertificate) {
        GiftCertificate oldGiftCertificate = giftCertificateDao.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY));
        GiftCertificate updatedGiftCertificateEntity = giftDtoConverter.convertToEntity(updatedGiftCertificate);
        removeDuplicateTags(updatedGiftCertificateEntity);
        List<Tag> updatedTags = tagUpdater.updateListFromDatabase(updatedGiftCertificateEntity.getTags());
        updatedGiftCertificateEntity.setTags(updatedTags);
        GiftCertificate updatedCertificate = giftCertificateDao.update(
                updater.updateObject(updatedGiftCertificateEntity, oldGiftCertificate));
        return giftDtoConverter.convertToDto(updatedCertificate);
    }


    @Override
    public List<GiftCertificateDto> search(MultiValueMap<String, String> requestParams, int page, int size) {
        if (requestParams == null || giftCertificateDao == null) {
            return Collections.emptyList();
        }
        Pageable pageable = createPageRequest(page, size);
        List<GiftCertificate> certificates = giftCertificateDao.search(requestParams, pageable);

        return certificates.stream()
                .map(giftDtoConverter::convertToDto)
                .collect(Collectors.toList());
    }


    private void removeDuplicateTags(GiftCertificate giftCertificate) {
        List<Tag> tags = giftCertificate.getTags();
        if (tags != null) {
            Set<Tag> uniqueTags = new HashSet<>(tags);
            giftCertificate.setTags(new ArrayList<>(uniqueTags));
        }
    }

    protected Pageable createPageRequest(int page, int size) {
        try {
            return PageRequest.of(page, size);
        } catch (IllegalArgumentException e) {
            ExceptionResult exceptionResult = new ExceptionResult();
            exceptionResult.addException(ExceptionMessageKey.INVALID_PAGINATION, page, size);
            throw new IncorrectParameterException(exceptionResult);
        }
    }

}


package com.epam.esm.service.impl;

import com.epam.esm.dao.BasicDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.util.List;

import static com.epam.esm.exception.ExceptionMessageKey.TAG_NOT_FOUND;

@Service
public class TagServiceImpl extends AbstractService<Tag> implements TagService {
    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(BasicDao<Tag> dao, TagDao tagDao) {
        super(dao);
        this.tagDao = tagDao;
    }

    @Override
    public Tag insert(Tag tag) {
        String tagName = tag.getName();
        tagDao.findByName(tagName).ifPresent(tag1 -> {
            throw new DuplicateEntityException(ExceptionMessageKey.TAG_EXIST);
        });
        return dao.insert(tag);
    }

    @Override
    public Tag update(long id, Tag entity) {
        return tagDao.update(entity);
    }

    @Override
    @Transactional
    public void removeById(long id) {
        tagDao.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY));

        tagDao.removeGiftCertificateHasTag(id);
        tagDao.removeById(id);
    }


    @Override
    public List<Tag> search(MultiValueMap<String, String> requestParams, int page, int size) {
        Pageable pageRequest = createPageRequest(page, size);
        return tagDao.search(requestParams, pageRequest);
    }


    @Override
    public Tag getMostPopularTagOfUserWithHighestCostOfAllOrders() {

        return tagDao.findMostPopularTagOfUserWithHighestCostOfAllOrders().orElseThrow(() -> {
            throw new NoSuchEntityException(TAG_NOT_FOUND);
        });
    }
}

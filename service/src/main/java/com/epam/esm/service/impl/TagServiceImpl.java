package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.impl.TagDtoConverter;
import com.epam.esm.dto.response.ApiResponse;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.*;
import com.epam.esm.logic.renovator.impl.TagUpdater;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final TagDtoConverter tagDtoConverter;
    private final TagUpdater updater;

    @Autowired
    public TagServiceImpl(TagDao tagDao, TagDtoConverter tagDtoConverter, TagUpdater updater) {
        this.tagDao = tagDao;
        this.tagDtoConverter = tagDtoConverter;
        this.updater = updater;
    }

    @Override
    public TagDto getById(long id) {
        Tag tag = tagDao.findById(id).orElseThrow(() -> new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY));
        return tagDtoConverter.convertToDto(tag);
    }

    @Override
    public List<TagDto> getAll(int page, int size) {
        Pageable pageRequest = createPageRequest(page, size);

        List<Tag> all = tagDao.findAll(pageRequest);

        return all.stream().map(tagDtoConverter::convertToDto).collect(Collectors.toList());
    }

    @Override
    public TagDto insert(TagDto tagDto) {
        tagDao.findByName(tagDto.getName()).ifPresent(tag1 -> {
            throw new DuplicateEntityException(ExceptionMessageKey.TAG_EXIST);
        });

        Tag tag = tagDtoConverter.convertToEntity(tagDto);
        Tag insert = tagDao.insert(tag);

        return tagDtoConverter.convertToDto(insert);
    }

    @Override
    public TagDto update(long id, TagDto tagDto) {
        Tag oldTag = tagDao.findById(id).orElseThrow(() -> new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY));

        Tag newTag = tagDtoConverter.convertToEntity(tagDto);
        Tag update = tagDao.update(updater.updateObject(newTag, oldTag));
        return tagDtoConverter.convertToDto(update);
    }

    @Override
    @Transactional
    public ApiResponse removeById(long id) {
        tagDao.removeGiftCertificateHasTag(id);
        tagDao.removeById(id);
        return new ApiResponse(true, "deleted");
    }

    @Override
    public List<TagDto> search(MultiValueMap<String, String> requestParams, int page, int size) {
        Pageable pageRequest = createPageRequest(page, size);

        List<Tag> all = tagDao.search(requestParams, pageRequest);

        return all.stream().map(tagDtoConverter::convertToDto).collect(Collectors.toList());
    }

    @Override
    public TagDto getMostPopularTagOfUserWithHighestCostOfAllOrders() {
        Tag tag = tagDao.findMostPopularTagOfUserWithHighestCostOfAllOrders().orElseThrow(() -> {
            throw new NoSuchEntityException(ExceptionMessageKey.TAG_NOT_FOUND);
        });

        return tagDtoConverter.convertToDto(tag);
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

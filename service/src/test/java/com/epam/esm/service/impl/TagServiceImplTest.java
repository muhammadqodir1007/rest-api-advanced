package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.impl.TagDtoConverter;
import com.epam.esm.entity.Tag;
import com.epam.esm.logic.renovator.impl.TagUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagDao tagDao;
    @Mock

    private TagDtoConverter tagDtoConverter;

    @Mock
    private TagUpdater updater;
    @InjectMocks

    private TagServiceImpl tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tagService = new TagServiceImpl(tagDao, tagDtoConverter, updater);
    }

    @Test
    void shouldGetById() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("tag");
        when(tagDao.findById(1L)).thenReturn(Optional.of(tag));
        TagDto result = tagService.getById(1L);
        assertEquals(tagDtoConverter.convertToDto(tag), result);
    }

    @Test
    void shouldGetAll() {
        List<Tag> tagList = new ArrayList<>();
        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setName("tag1");
        Tag tag2 = new Tag();
        tag2.setId(2L);
        tag2.setName("tag2");
        tagList.add(tag1);
        tagList.add(tag2);
        when(tagDao.findAll(any())).thenReturn(tagList);
        List<TagDto> result = tagService.getAll(0, 2);
        assertEquals(tagDtoConverter.convertToDto(tagList.get(0)), result.get(0));
        assertEquals(tagDtoConverter.convertToDto(tagList.get(1)), result.get(1));
    }

    @Test
    void shouldInsert() {
        TagDto tagDto = new TagDto();
        tagDto.setName("tag");
        Tag tag = tagDtoConverter.convertToEntity(tagDto);
        when(tagDao.findByName("tag")).thenReturn(Optional.empty());
        when(tagDao.insert(tag)).thenReturn(tag);
        TagDto result = tagService.insert(tagDto);
        assertEquals(tagDtoConverter.convertToDto(tag), result);
    }

    @Test
    void shouldUpdate() {
        long id = 1L;
        TagDto tagDto = new TagDto();
        Tag oldTag = new Tag();
        Tag newTag = new Tag();

        when(tagDao.findById(id)).thenReturn(Optional.of(oldTag));
        when(tagDao.update(any(Tag.class))).thenReturn(newTag);

        when(tagDtoConverter.convertToEntity(tagDto)).thenReturn(newTag);
        when(tagDtoConverter.convertToDto(newTag)).thenReturn(tagDto);

        when(updater.updateObject(newTag, oldTag)).thenReturn(newTag);

        TagDto result = tagService.update(id, tagDto);

        assertNotNull(result);
    }

    @Test
    void shouldRemoveById() {
        tagService.removeById(1L);
    }

    @Test
    void shouldSearch() {
        List<Tag> tagList = new ArrayList<>();
        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setName("tag1");
        Tag tag2 = new Tag();
        tag2.setId(2L);
        tag2.setName("tag2");
        tagList.add(tag1);
        tagList.add(tag2);
        when(tagDao.search(any(), any())).thenReturn(tagList);

        List<TagDto> result = tagService.search(null, 0, 2);

        assertEquals(tagDtoConverter.convertToDto(tagList.get(0)), result.get(0));
    }


}

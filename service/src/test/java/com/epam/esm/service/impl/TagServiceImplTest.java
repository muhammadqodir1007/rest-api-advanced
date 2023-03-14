package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.NoSuchEntityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @Mock
    private TagDaoImpl tagDao = Mockito.mock(TagDaoImpl.class);

    @InjectMocks
    private TagServiceImpl tagService;

    private static final Tag TAG_1 = new Tag(1, "tagName1");
    private static final Tag TAG_2 = new Tag(2, "tagName3");
    private static final Tag TAG_3 = new Tag(3, "tagName5");
    private static final Tag TAG_4 = new Tag(4, "tagName4");
    private static final Tag TAG_5 = new Tag(5, "tagName2");


    private static final String SORT_PARAMETER = "ASC";
    private static final int PAGE = 0;
    private static final int SIZE = 5;

    @Test
    public void shouldGetById() {
        when(tagDao.findById(anyLong())).thenReturn(Optional.of(TAG_3));

        Tag actual = tagService.getById(TAG_3.getId());

        assertEquals(TAG_3, actual);
    }

    @Test
    public void shouldGetAll() {
        List<Tag> tags = Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4, TAG_5);
        when(tagDao.findAll(any())).thenReturn(tags);
        List<Tag> actual = tagService.getAll(PAGE, SIZE);
        assertEquals(tags, actual);
    }


    @Test
    public void shouldSearch() {
        List<Tag> tags = Arrays.asList(TAG_1, TAG_5, TAG_2, TAG_4, TAG_3);
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("sortByTagName", SORT_PARAMETER);
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        when(tagDao.search(requestParams, pageRequest)).thenReturn(tags);

        List<Tag> actual = tagService.search(requestParams, PAGE, SIZE);

        assertEquals(tags, actual);
    }


    @Test
    public void shouldInsertTagSuccess() {
        Tag tag = new Tag("new_tag");

        when(tagDao.findByName("new_tag")).thenReturn(Optional.empty());
        when(tagDao.insert(tag)).thenReturn(tag);

        Tag insertedTag = tagService.insert(tag);

        assertEquals(tag, insertedTag);
        verify(tagDao, times(1)).findByName("new_tag");
        verify(tagDao, times(1)).insert(tag);
    }


    @Test
    public void shouldNotInsertTagDuplicate() {
        Tag tag = new Tag("new_tag");

        when(tagDao.findByName("new_tag")).thenReturn(Optional.of(tag));

        assertThrows(DuplicateEntityException.class, () -> tagService.insert(tag));


    }

    @Test
    public void shouldUpdate() {
        Tag tag = new Tag("new_tag");
        when(tagDao.update(tag)).thenReturn(tag);
        Tag updatedTag = tagService.update(1, tag);
        assertEquals(tag, updatedTag);
    }

    @Test
    public void shouldRemoveTagSuccess() {

        when(tagDao.findById(1)).thenReturn(Optional.of(new Tag("tag_name")));

        tagService.removeById(1);

    }


    @Test
    public void shouldNotRemoveTagNotFound() {
        when(tagDao.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> tagService.removeById(1));

    }


}
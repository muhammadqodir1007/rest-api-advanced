package com.epam.esm;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TestConfig.class)
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class TagDaoImplTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    TagDao tagDao;

    private static final Tag TAG_1 = new Tag("tagName");

    @Test
    @Transactional
    void shouldInsert() {
        Tag tag1 = tagDao.insert(TAG_1);
        assertEquals(TAG_1.getName(), tag1.getName());
    }

    @Test
    @Transactional
    void shouldNotInsert() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> tagDao.insert(null));
    }

    @Test
    @Transactional
    void shouldGetById() {
        Tag tag = new Tag("firstTag");
        entityManager.persist(tag);
        entityManager.flush();
        Tag foundedTag = tagDao.findById(tag.getId()).orElse(null);
        assertNotNull(foundedTag);
        assertEquals("firstTag", foundedTag.getName());
    }

    @Test
    @Transactional
    void shouldNotGetById() {
        int randomNumber = 34;
        Tag foundedTag = tagDao.findById(randomNumber).orElse(null);
        assertNull(foundedTag);
    }

    @Test
    @Transactional
    void shouldGetByName() {
        Tag tag = new Tag("searchingTag");
        entityManager.persist(tag);
        entityManager.flush();
        Tag tag1 = tagDao.findByName(tag.getName()).orElse(null);
        assertNotNull(tag1);
        assertEquals(tag.getName(), tag1.getName());
    }

    @Test
    @Transactional
    void shouldRemove() {

        Tag tag = new Tag("TagForDelete");
        entityManager.persist(tag);
        entityManager.flush();
        tagDao.removeById(tag.getId());
        Tag tag1 = tagDao.findByName(tag.getName()).orElse(null);
        assertNull(tag1);

    }

    @Test
    @Transactional
    void shouldGetAll() {
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        Tag tag3 = new Tag("tag3");
        entityManager.persist(tag1);
        entityManager.persist(tag2);
        entityManager.persist(tag3);
        entityManager.flush();
        Pageable pageable = PageRequest.of(0, 5);
        List<Tag> actual = tagDao.findAll(pageable);

        assertEquals(Arrays.asList(tag1, tag2, tag3), actual);

    }


}
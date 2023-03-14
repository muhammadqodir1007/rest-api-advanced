package com.epam.esm;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TestConfig.class)
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class UserDaoTest {


    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UserDao userDao;


    private static final User USER_1 = new User("Jack");
    private static final User USER_2 = new User("Simon");
    private static final User USER_3 = new User("Alba");


    @Test
    @Transactional
    void shouldGetAll() {

        entityManager.persist(USER_1);
        entityManager.persist(USER_2);
        entityManager.persist(USER_3);
        entityManager.flush();

        List<User> all = userDao.findAll(PageRequest.of(0, 5));

        assertEquals(Arrays.asList(USER_1, USER_2, USER_3), all);


    }

    @Test
    @Transactional
    void shouldGetById() {
        User user = new User("kylie");
        entityManager.persist(user);
        entityManager.flush();
        User actual = userDao.findById(user.getId()).orElse(null);
        assertNotNull(actual);
        assertEquals(user, actual);
    }


}

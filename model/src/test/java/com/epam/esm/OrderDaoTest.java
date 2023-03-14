package com.epam.esm;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TestConfig.class)
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Testcontainers
@ActiveProfiles("test")
public class OrderDaoTest {


    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    OrderDao orderDao;


    private static final GiftCertificate GIFT_CERTIFICATE = new GiftCertificate("name",
            "description", BigDecimal.valueOf(34), 34, LocalDateTime.now(), LocalDateTime.now());


    @Transactional
    @Test
    void shouldInsert() {
        Order order = new Order();
        User user = new User();
        user.setName("jack");
        entityManager.persist(user);
        entityManager.flush();
        order.setUser(user);
        order.setPurchaseTime(LocalDateTime.now());
        order.setPrice(BigDecimal.valueOf(34));
        order.setGiftCertificate(GIFT_CERTIFICATE);
        Order order1 = orderDao.insert(order);
        assertEquals("jack", order1.getUser().getName());

    }

    @Transactional
    @Test
    void shouldNotInsert() {
        Order order = new Order();
        order.setUser(null);
        order.setPurchaseTime(LocalDateTime.now());
        order.setPrice(BigDecimal.valueOf(34));
        order.setGiftCertificate(GIFT_CERTIFICATE);
        assertThrows(DataIntegrityViolationException.class, () -> orderDao.insert(order));


    }

    @Transactional
    @Test
    void shouldGetById() {

        Order order = new Order();
        User user = new User();
        user.setName("jack");
        entityManager.persist(user);
        order.setUser(user);
        order.setPurchaseTime(LocalDateTime.now());
        order.setPrice(BigDecimal.valueOf(34));
        order.setGiftCertificate(GIFT_CERTIFICATE);
        Order insertedOrder = orderDao.insert(order);
        Order actual = orderDao.findById(insertedOrder.getId()).orElse(null);
        assertNotNull(actual);
        assertEquals(insertedOrder, actual);

    }

    @Transactional
    @Test
    void shouldNotGetById() {
        assertThrows(NoSuchElementException.class, () -> orderDao.findById(450).get());
    }

    @Transactional
    @Test
    void shouldNotGetAll() {
        assertThrows(UnsupportedOperationException.class, () -> orderDao.findAll(PageRequest.of(0, 5)));


    }


}

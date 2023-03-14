package com.epam.esm;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TestConfig.class)
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Testcontainers
@ActiveProfiles("test")
public class GiftDaoTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    GiftCertificateDao giftCertificateDao;

    private static final GiftCertificate GIFT_CERTIFICATE = new GiftCertificate("name",
            "description", BigDecimal.valueOf(34), 34, LocalDateTime.now(), LocalDateTime.now());
    private static final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate("name1",
            "description1", BigDecimal.valueOf(34), 34, LocalDateTime.now(), LocalDateTime.now());
    private static final GiftCertificate GIFT_CERTIFICATE_4 = new GiftCertificate("name2",
            "description2", BigDecimal.valueOf(34), 34, LocalDateTime.now(), LocalDateTime.now());
    private static final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate();


    @Test
    @Transactional
    void shouldInsert() {
        GiftCertificate giftCertificate = new GiftCertificate("name",
                "description", BigDecimal.valueOf(34), 45, LocalDateTime.now(), LocalDateTime.now());


        GiftCertificate insertedGift = giftCertificateDao.insert(giftCertificate);
        assertEquals(giftCertificate.getName(), insertedGift.getName());
    }

    @Test
    @Transactional
    void shouldNotInsert() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> giftCertificateDao.insert(null));
    }

    @Test
    @Transactional
    void shouldGetById() {
        GiftCertificate giftCertificate = new GiftCertificate("name",
                "description", BigDecimal.valueOf(34), 56, LocalDateTime.now(), LocalDateTime.now());
        entityManager.persist(giftCertificate);
        entityManager.flush();
        GiftCertificate giftCertificate1 = giftCertificateDao.findById(giftCertificate.getId()).orElse(null);
        assertNotNull(giftCertificate1);
        assertEquals(giftCertificate.getName(), giftCertificate1.getName());
    }

    @Test
    @Transactional
    void shouldNotGetById() {
        int randomNumber = 235;
        GiftCertificate giftCertificate = giftCertificateDao.findById(randomNumber).orElse(null);
        assertNull(giftCertificate);
    }

    @Test
    @Transactional
    void shouldGetByName() {
        GiftCertificate giftCertificate = new GiftCertificate("name",
                "description", BigDecimal.valueOf(34), 33, LocalDateTime.now(), LocalDateTime.now());
        entityManager.persist(giftCertificate);
        entityManager.flush();
        GiftCertificate giftCertificate1 = giftCertificateDao.findByName(giftCertificate.getName()).orElse(null);
        assert giftCertificate1 != null;
        assertEquals(giftCertificate.getName(), giftCertificate1.getName());

    }

    @Test
    @Transactional
    void shouldRemove() {
        GiftCertificate giftCertificate = new GiftCertificate("name",
                "description", BigDecimal.valueOf(45), 43, LocalDateTime.now(), LocalDateTime.now());
        entityManager.persist(giftCertificate);
        entityManager.flush();
        giftCertificateDao.removeById(giftCertificate.getId());
        GiftCertificate giftCertificate1 = giftCertificateDao.findByName(giftCertificate.getName()).orElse(null);
        assertNull(giftCertificate1);
    }

    @Test
    @Transactional
    void shouldGetAll() {
        entityManager.persist(GIFT_CERTIFICATE);
        entityManager.persist(GIFT_CERTIFICATE_3);
        entityManager.persist(GIFT_CERTIFICATE_4);
        entityManager.flush();
        List<GiftCertificate> actual = giftCertificateDao.findAll(PageRequest.of(0, 5));
        assertEquals(Arrays.asList(GIFT_CERTIFICATE, GIFT_CERTIFICATE_3, GIFT_CERTIFICATE_4), actual);

    }

}

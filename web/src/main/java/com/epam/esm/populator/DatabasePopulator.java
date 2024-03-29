package com.epam.esm.populator;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.service.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Collections;

@Component
public class DatabasePopulator implements CommandLineRunner {
    private final GiftCertificateDao giftCertificateDao;
    private final OrderService orderController;
    @PersistenceContext
    EntityManager entityManager;


    public DatabasePopulator(GiftCertificateDao giftCertificateDao, OrderService orderController) {
        this.giftCertificateDao = giftCertificateDao;
        this.orderController = orderController;
    }


    @Override
    @Transactional
    public void run(String... args) {


        for (int i = 1; i < 1000; i++) {
            User user = new User();
            user.setId(i);
            user.setName("name" + i);
            entityManager.persist(user);
        }

        for (int i = 1; i < 10000; i++) {
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setName("gift" + i);
            giftCertificate.setDescription("description" + i);
            giftCertificate.setPrice(BigDecimal.valueOf(i * 10));
            giftCertificate.setDuration(67);
            giftCertificate.setTags(Collections.singletonList(new Tag("tag" + i)));
            giftCertificateDao.insert(giftCertificate);
        }


        for (int i = 1; i < 1000; i++) {
            OrderDto orderDto = new OrderDto();
            orderDto.setUserId(i);
            orderDto.setPrice(BigDecimal.valueOf(i * 10));
            orderDto.setGiftCertificateId(i);
            orderController.insert(orderDto);

        }


    }
}

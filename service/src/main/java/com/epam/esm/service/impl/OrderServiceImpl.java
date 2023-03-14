package com.epam.esm.service.impl;

import com.epam.esm.dao.BasicDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.util.List;

import static com.epam.esm.exception.ExceptionMessageKey.GIFT_CERTIFICATE_NOT_FOUND;
import static com.epam.esm.exception.ExceptionMessageKey.USER_NOT_FOUND;

@Service
public class OrderServiceImpl extends AbstractService<Order> implements OrderService {
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final GiftCertificateDao giftCertificateDao;

    @Autowired
    public OrderServiceImpl(BasicDao<Order> dao, OrderDao orderDao, UserDao userDao, GiftCertificateDao giftCertificateDao) {
        super(dao);
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public List<Order> getAll(int page, int size) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional
    public Order insert(Order order) {
        User user = userDao.findById(order.getUser().getId())
                .orElseThrow(() -> new NoSuchEntityException(USER_NOT_FOUND));
        GiftCertificate giftCertificate = giftCertificateDao.findById(order.getGiftCertificate().getId())
                .orElseThrow(() -> new NoSuchEntityException(GIFT_CERTIFICATE_NOT_FOUND));
        order.setUser(user);
        order.setGiftCertificate(giftCertificate);
        order.setPrice(giftCertificate.getPrice());
        return dao.insert(order);
    }


    @Override
    public Order update(long id, Order entity) {
        return orderDao.update(entity);
    }

    @Override
    public void removeById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> search(MultiValueMap<String, String> requestParams, int page, int size) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Order> getOrdersByUserId(long userId, int page, int size) {
        Pageable pageRequest = createPageRequest(page, size);
        return orderDao.findByUserId(userId, pageRequest);
    }
}

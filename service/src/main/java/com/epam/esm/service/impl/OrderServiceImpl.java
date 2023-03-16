package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.converter.impl.OrderDtoConverter;
import com.epam.esm.dto.response.ApiResponse;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.ExceptionResult;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final GiftCertificateDao giftCertificateDao;
    private final OrderDtoConverter orderDtoConverter;

    public OrderServiceImpl(OrderDao orderDao, UserDao userDao, GiftCertificateDao giftCertificateDao, OrderDtoConverter orderDtoConverter) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.giftCertificateDao = giftCertificateDao;
        this.orderDtoConverter = orderDtoConverter;
    }

    @Override
    public OrderDto getById(long id) {
        Order order = orderDao.findById(id).orElseThrow(() -> new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY));
        return orderDtoConverter.convertToDto(order);
    }

    @Override
    public List<OrderDto> getAll(int page, int size) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OrderDto update(long id, OrderDto entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional
    public OrderDto insert(OrderDto orderDto) {
        Order order = orderDtoConverter.convertToEntity(orderDto);
        User user = userDao.findById(order.getUser().getId())
                .orElseThrow(() -> new NoSuchEntityException(ExceptionMessageKey.USER_NOT_FOUND));
        GiftCertificate giftCertificate = giftCertificateDao.findById(order.getGiftCertificate().getId())
                .orElseThrow(() -> new NoSuchEntityException(ExceptionMessageKey.GIFT_CERTIFICATE_NOT_FOUND));
        order.setUser(user);
        order.setGiftCertificate(giftCertificate);
        order.setPrice(giftCertificate.getPrice());
        Order createdOrder = orderDao.insert(order);
        return orderDtoConverter.convertToDto(createdOrder);
    }

    @Override
    public ApiResponse removeById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<OrderDto> search(MultiValueMap<String, String> requestParams, int page, int size) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<OrderDto> getOrdersByUserId(long userId, int page, int size) {
        Pageable pageRequest = createPageRequest(page, size);
        List<Order> orders = orderDao.findByUserId(userId, pageRequest);
        return orders.stream()
                .map(orderDtoConverter::convertToDto)
                .collect(Collectors.toList());
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


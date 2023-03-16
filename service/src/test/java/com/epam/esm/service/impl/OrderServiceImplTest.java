package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.converter.impl.OrderDtoConverter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.NoSuchEntityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderDaoImpl orderDao = Mockito.mock(OrderDaoImpl.class);
    @Mock
    private OrderDtoConverter orderDtoConverter = Mockito.mock(OrderDtoConverter.class);


    @Mock
    private UserDaoImpl userDao = Mockito.mock(UserDaoImpl.class);


    @Mock
    private GiftCertificateDaoImpl giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);


    @InjectMocks
    private OrderServiceImpl orderService;


    @Test
    void shouldGetById() {
        long id = 1L;
        Order order = new Order();
        order.setId(id);
        OrderDto orderDto = new OrderDto();
        orderDto.setId(id);
        Mockito.when(orderDao.findById(id)).thenReturn(Optional.of(order));
        Mockito.when(orderDtoConverter.convertToDto(order)).thenReturn(orderDto);
        OrderDto result = orderService.getById(id);
        Assertions.assertEquals(id, result.getId());
    }

    @Test
    void shouldInsert() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setUserId(1L);
        orderDto.setGiftCertificateId(1L);
        User user = new User();
        user.setId(1L);
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        giftCertificate.setPrice(BigDecimal.valueOf(10.0));
        Order order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setGiftCertificate(giftCertificate);
        order.setPrice(BigDecimal.valueOf(10.0));
        Mockito.when(userDao.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(giftCertificateDao.findById(1L)).thenReturn(Optional.of(giftCertificate));
        Mockito.when(orderDtoConverter.convertToEntity(orderDto)).thenReturn(order);
        Mockito.when(orderDao.insert(order)).thenReturn(order);
        Mockito.when(orderDtoConverter.convertToDto(order)).thenReturn(orderDto);
        OrderDto result = orderService.insert(orderDto);
        Assertions.assertEquals(1L, result.getId());
    }


    @Test
    public void shouldGetOrdersByUserId() {
        long userId = 1L;
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        order1.setId(1L);
        orders.add(order1);
        Order order2 = new Order();
        order2.setId(2L);
        orders.add(order2);
        OrderDto orderDto1 = new OrderDto();
        orderDto1.setId(1L);
        OrderDto orderDto2 = new OrderDto();
        orderDto2.setId(2L);
        Mockito.when(orderDao.findByUserId(userId, pageable)).thenReturn(orders);
        Mockito.when(orderDtoConverter.convertToDto(order1)).thenReturn(orderDto1);
        Mockito.when(orderDtoConverter.convertToDto(order2)).thenReturn(orderDto2);
        List<OrderDto> result = orderService.getOrdersByUserId(userId, page, size);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(1L, result.get(0).getId());
        Assertions.assertEquals(2L, result.get(1).getId());
    }

    @Test
    void shouldThrowNoSuchEntityException() {
        long orderId = 1L;
        when(orderDao.findById(orderId)).thenReturn(java.util.Optional.empty());
        assertThrows(NoSuchEntityException.class, () -> orderService.getById(orderId));
    }


    @Test
    void shouldThrowsException() {
        int page = -1;
        int size = 10;
        assertThrows(IncorrectParameterException.class, () -> orderService.createPageRequest(page, size));
    }


}

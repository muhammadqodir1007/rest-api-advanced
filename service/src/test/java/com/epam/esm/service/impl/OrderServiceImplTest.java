package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderDaoImpl orderDao = Mockito.mock(OrderDaoImpl.class);

    @Mock
    private UserDaoImpl userDao = Mockito.mock(UserDaoImpl.class);


    @Mock
    private GiftCertificateDaoImpl giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);

    private static final LocalDateTime UPDATED_DATE = LocalDateTime.parse("2018-08-29T06:12:15.156");

    @InjectMocks
    private OrderServiceImpl orderService;

    private static final Order ORDER_1 = new Order(1, new BigDecimal("15.2"), UPDATED_DATE, new User(1, "name1"),
            new GiftCertificate(1, "giftCertificate1", "description1", new BigDecimal("10.1"),
                    1, LocalDateTime.parse("2020-08-29T06:12:15"), LocalDateTime.parse("2020-08-29T06:12:15"), null));
    private static final Order ORDER_5 = new Order(1, new BigDecimal("15.2"), UPDATED_DATE, new User(1, "name1"),
            new GiftCertificate());
    private static final Order ORDER_2 = new Order(2, new BigDecimal("30.4"), UPDATED_DATE, new User(1, "name1"),
            new GiftCertificate(2, "giftCertificate3", "description3", new BigDecimal("30.3"),
                    3, LocalDateTime.parse("2019-08-29T06:12:15"), LocalDateTime.parse("2019-08-29T06:12:15"), null));

    private static final int PAGE = 0;
    private static final int SIZE = 5;


    @Test
    public void shouldGetOrdersByUserId() {
        List<Order> orders = Arrays.asList(ORDER_1, ORDER_2);
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        when(orderDao.findByUserId(ORDER_1.getUser().getId(), pageRequest)).thenReturn(orders);
        List<Order> actual = orderService.getOrdersByUserId(ORDER_1.getUser().getId(), PAGE, SIZE);
        assertEquals(orders, actual);
    }

    @Test
    public void shouldNotInsertWithNonExistingUserThrowsNoSuchEntityException() {
        Order order = new Order();
        order.setUser(new User(1L, "test_user"));

        when(userDao.findById(anyLong())).thenReturn(Optional.empty());


        assertThrows(NoSuchEntityException.class, () -> orderService.insert(order));
    }

    @Test
    public void shouldNotInsertWithNonExistingGiftCertificateThrowsNoSuchEntityException() {
        Order order = new Order();
        order.setUser(new User(1L, "test_user"));
        order.setGiftCertificate(new GiftCertificate(1L, "test_certificate", "test_description",
                BigDecimal.TEN, 30, null, null, null));

        when(userDao.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> orderService.insert(order));

    }


    @Test
    public void removeByIdShouldThrowUnsupportedOperationException() {
        long id = 1L;
        assertThrows(UnsupportedOperationException.class, () -> orderService.removeById(id));
    }

    @Test
    public void searchShouldThrowUnsupportedOperationException() {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        int page = 0;
        int size = 10;

        assertThrows(UnsupportedOperationException.class, () -> orderService.search(requestParams, page, size));

    }

    @Test
    public void getOrdersByUserIdShouldReturnOrders() {
        long userId = 1L;
        int page = 0;
        int size = 10;
        Pageable pageRequest = PageRequest.of(page, size);
        List<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(new Order());
        expectedOrders.add(new Order());

        when(orderDao.findByUserId(userId, pageRequest)).thenReturn(expectedOrders);

        List<Order> actualOrders = orderService.getOrdersByUserId(userId, page, size);

        assertEquals(expectedOrders, actualOrders);
        verify(orderDao).findByUserId(userId, pageRequest);
    }

}

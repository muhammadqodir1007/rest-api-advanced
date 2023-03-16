package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;

import java.util.List;
public interface OrderService extends CRUDService<OrderDto> {

    /**
     * Method for getting list of {@link Order} from database by user ID.
     *
     * @param userId ID of user
     * @param page   page number for pagination
     * @param size   page size for pagination
     * @return list of orders
     */
    List<OrderDto> getOrdersByUserId(long userId, int page, int size);
}

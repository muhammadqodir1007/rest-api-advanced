package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderDao extends BasicDao<Order> {

    /**
     * Method for getting list of {@link Order} from a table by user ID.
     *
     * @param userId   ID of user
     * @param pageable object with pagination info (page number, page size)
     * @return list of orders
     */
    List<Order> findByUserId(long userId, Pageable pageable);
}

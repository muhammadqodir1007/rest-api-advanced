package com.epam.esm.dao;

import com.epam.esm.entity.User;

import java.util.Optional;
public interface UserDao extends BasicDao<User> {

    /**
     * @return Optional of found user
     * @deprecated Method for finding user by highest cost of all orders.
     */
    Optional<User> findByHighestCostOfAllOrders();
}

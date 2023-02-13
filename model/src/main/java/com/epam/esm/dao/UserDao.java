package com.epam.esm.dao;

import com.epam.esm.entity.User;

import java.util.Optional;
public interface UserDao extends CRUDDao<User> {

    /**
     * @return Optional of found user
     * @deprecated Method for finding user by highest cost of all orders.
     */
    @Deprecated
    Optional<User> findByHighestCostOfAllOrders();
}

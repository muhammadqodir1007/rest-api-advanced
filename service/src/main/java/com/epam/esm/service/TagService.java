package com.epam.esm.service;

import com.epam.esm.entity.Tag;
public interface TagService extends CRUDService<Tag> {

    /**
     * Find most popular tag of user with the highest cost of all orders.
     *
     * @return the founded tag
     */
    Tag getMostPopularTagOfUserWithHighestCostOfAllOrders();
}

package com.epam.esm.service;

import com.epam.esm.entity.Tag;
public interface TagService extends CRDService<Tag> {

    /**
     * Find most popular tag of user with highest cost of all orders.
     *
     * @return the found tag
     */
    Tag getMostPopularTagOfUserWithHighestCostOfAllOrders();
}

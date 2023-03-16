package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
public interface TagService extends CRUDService<TagDto> {

    /**
     * Find most popular tag of user with the highest cost of all orders.
     *
     * @return the founded tag
     */
    TagDto getMostPopularTagOfUserWithHighestCostOfAllOrders();
}

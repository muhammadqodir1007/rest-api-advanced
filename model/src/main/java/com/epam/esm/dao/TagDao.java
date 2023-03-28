package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.Optional;
public interface TagDao extends BasicDao<Tag> {

    /**
     * Method for getting a tag from a table with a specific name.
     *
     * @param name name of tag to get
     * @return Optional of tag entity
     */
    Optional<Tag> findByName(String name);



    /**
     * Method for finding the most popular tag of user with the highest cost of all orders in database.
     *
     * @return Optional of found tag
     */
    Optional<Tag> findMostPopularTagOfUserWithHighestCostOfAllOrders();

    /**
     * Method for deleting links between gift certificates and tags.
     *
     * @param id ID of tag by which the deletion will be
     */
    void removeGiftCertificateHasTag(long id);
}

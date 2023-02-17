package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.Optional;

public interface GiftCertificateDao extends BasicDao<GiftCertificate> {

    /**
     * Method for getting a gift certificate from a table with a specific name.
     *
     * @param name name of gift certificate to get
     * @return Optional of gift certificate entity
     */
    Optional<GiftCertificate> findByName(String name);

    /**
     * Method for deleting links between gift certificates and tags.
     *
     * @param id ID of gift certificate by which the deletion will be
     */
    void removeGiftCertificateHasTag(long id);
}

package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.creator.QueryCreator;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Service
@Transactional
public class TagDaoImpl extends AbstractDao<Tag> implements TagDao {
    private final QueryCreator<Tag> queryCreator;


    private static final String FIND_MOST_WIDELY_USED_TAG_OF_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS_QUERY =
            String.format("SELECT t FROM GiftCertificate g INNER JOIN g.tags t WHERE g.id IN (SELECT o.giftCertificate.id" +
                    " FROM Order o WHERE o.user.id IN (SELECT o.user.id FROM Order o GROUP BY o.user.id ORDER BY SUM(o.price) DESC)) " +
                    "GROUP BY t.id ORDER BY COUNT(t.id) DESC");

    private static final String REMOVE_GIFT_CERTIFICATES_HAS_TAGS_QUERY = "DELETE FROM gift_certificates_has_tags "
            + "WHERE tag_id = :tag_id_fk";

    @Autowired
    public TagDaoImpl(QueryCreator<Tag> queryCreator) {
        super(Tag.class);
        this.queryCreator = queryCreator;
    }

    @Override
    protected QueryCreator<Tag> getQueryCreator() {
        return queryCreator;
    }


    @Override
    public Optional<Tag> findMostPopularTagOfUserWithHighestCostOfAllOrders() {
        return entityManager.createQuery(FIND_MOST_WIDELY_USED_TAG_OF_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS_QUERY, entityType)
                .setMaxResults(1)
                .getResultList().stream()
                .findFirst();
    }

    @Override
    public void removeGiftCertificateHasTag(long id) {
        entityManager.createNativeQuery(REMOVE_GIFT_CERTIFICATES_HAS_TAGS_QUERY)
                .setParameter("tag_id_fk", id)
                .executeUpdate();
    }

    @Override
    public Tag update(Tag item) {
        return entityManager.merge(item);
    }
}

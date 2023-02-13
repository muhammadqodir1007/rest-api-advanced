package com.epam.esm.dao.creator.impl;

import com.epam.esm.dao.creator.AbstractQueryCreator;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
@Component
public class TagQueryCreator extends AbstractQueryCreator<Tag> {
    private static final Class<Tag> TAG_CLASS = Tag.class;

    @Override
    public CriteriaQuery<Tag> createGetQuery(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(TAG_CLASS);
        Root<Tag> tagRoot = criteriaQuery.from(TAG_CLASS);
        List<Predicate> restrictions = new ArrayList<>();

        restrictions.addAll(addName(fields, criteriaBuilder, tagRoot));
        restrictions.addAll(addPartOfName(fields, criteriaBuilder, tagRoot));
        criteriaQuery.select(tagRoot).where(restrictions.toArray(new Predicate[]{}));
        addSortByName(fields, criteriaBuilder, criteriaQuery, tagRoot);

        return criteriaQuery;
    }
}

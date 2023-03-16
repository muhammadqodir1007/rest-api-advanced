package com.epam.esm.dao.creator;

import org.springframework.util.MultiValueMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.epam.esm.dao.creator.SearchParameters.*;

public abstract class AbstractQueryCreator<T> implements QueryCreator<T> {
    private static final String PERCENT = "%";

    protected List<Predicate> addName(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder, Root<T> root) {
        List<Predicate> restrictions = new ArrayList<>();

        String name = getSingleParameter(fields, NAME);
        if (name != null) {
            restrictions.add(criteriaBuilder.equal(root.get(NAME), name));
        }

        return restrictions;
    }

    protected List<Predicate> addPartOfName(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder, Root<T> root) {
        List<Predicate> restrictions = new ArrayList<>();

        String partOfName = getSingleParameter(fields, PART_OF_NAME);
        if (partOfName != null) {
            restrictions.add(criteriaBuilder.like(root.get(NAME), PERCENT + partOfName + PERCENT));
        }

        return restrictions;
    }

    protected List<Predicate> addPartOfDescription(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder, Root<T> root) {
        List<Predicate> restrictions = new ArrayList<>();

        String partOfDescription = getSingleParameter(fields, PART_OF_DESCRIPTION);
        if (partOfDescription != null) {
            restrictions.add(criteriaBuilder.like(root.get("description"), PERCENT + partOfDescription + PERCENT));
        }

        return restrictions;
    }

    protected void addSortByName(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder,
                                 CriteriaQuery<T> criteriaQuery, Root<T> root) {
        String sortType = getSingleParameter(fields, SORT_BY_NAME);
        if (sortType != null) {
            criteriaQuery.orderBy(Objects.equals(sortType, SortType.DESC.getSortTypeName()) ?
                    criteriaBuilder.desc(root.get(NAME)) : criteriaBuilder.asc(root.get(NAME)));
        }
    }

    protected void addSortByCreateDate(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder,
                                       CriteriaQuery<T> criteriaQuery, Root<T> root) {
        String sortType = getSingleParameter(fields, SORT_BY_CREATE_DATE);
        if (sortType != null) {
            String createDate = "createDate";
            criteriaQuery.orderBy(Objects.equals(sortType, SortType.DESC.getSortTypeName()) ?
                    criteriaBuilder.desc(root.get(createDate)) : criteriaBuilder.asc(root.get(createDate)));
        }
    }

    private String getSingleParameter(MultiValueMap<String, String> fields, String parameter) {
        return fields.getFirst(parameter);
    }
}

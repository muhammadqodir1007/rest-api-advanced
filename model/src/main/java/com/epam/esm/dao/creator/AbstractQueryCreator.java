package com.epam.esm.dao.creator;

import org.springframework.util.MultiValueMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.epam.esm.dao.creator.FilterParameters.*;


public abstract class AbstractQueryCreator<T> implements QueryCreator<T> {
    private final String PERCENT = "%";

    protected List<Predicate> addName(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder, Root<T> root) {
        List<Predicate> restrictions = new ArrayList<>();

        String name = getSingleMultiValueMapParameter(fields, NAME);
        if (name != null) {
            restrictions.add(criteriaBuilder.equal(root.get(NAME), name));
        }
        return restrictions;
    }

    protected List<Predicate> addPartOfName(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder, Root<T> root) {
        List<Predicate> restrictions = new ArrayList<>();

        String partOfName = getSingleMultiValueMapParameter(fields, PART_OF_NAME);
        if (partOfName != null) {
            restrictions.add(criteriaBuilder.like(root.get(NAME), PERCENT + partOfName + PERCENT));
        }
        return restrictions;
    }

    protected List<Predicate> addPartOfDescription(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder, Root<T> root) {
        List<Predicate> restrictions = new ArrayList<>();

        String partOfDescription = getSingleMultiValueMapParameter(fields, PART_OF_DESCRIPTION);
        if (partOfDescription != null) {
            String DESCRIPTION = "description";
            restrictions.add(criteriaBuilder.like(root.get(DESCRIPTION), PERCENT + partOfDescription + PERCENT));
        }
        return restrictions;
    }

    protected void addSortByName(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder,
                                 CriteriaQuery<T> criteriaQuery, Root<T> root) {

        String sortType = getSingleMultiValueMapParameter(fields, SORT_BY_NAME);
        if (sortType != null) {
            if (Objects.equals(sortType, SortType.DESC.getSortTypeName())) {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(NAME)));
            }
            if (Objects.equals(sortType, SortType.ASC.getSortTypeName())) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(NAME)));
            }
        }
    }

    protected void addSortByCreateDate(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder,
                                       CriteriaQuery<T> criteriaQuery, Root<T> root) {
        String sortType = getSingleMultiValueMapParameter(fields, SORT_BY_CREATE_DATE);
        if (sortType != null) {
            String CREATE_DATE = "create_date";
            if (Objects.equals(sortType, SortType.DESC.getSortTypeName())) {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(CREATE_DATE)));
            }
            if (Objects.equals(sortType, SortType.ASC.getSortTypeName())) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(CREATE_DATE)));
            }
        }
    }

    private String getSingleMultiValueMapParameter(MultiValueMap<String, String> fields, String parameter) {
        if (fields.containsKey(parameter)) {
            return fields.get(parameter).get(0);
        } else {
            return null;
        }
    }
}

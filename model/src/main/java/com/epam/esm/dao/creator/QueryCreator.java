package com.epam.esm.dao.creator;

import org.springframework.util.MultiValueMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
public interface QueryCreator<T> {

    /**
     * Method for creating a database query from {@link MultiValueMap} to get entities.
     *
     * @param fields          query parameters
     * @param criteriaBuilder the criteria builder
     * @return the criteria query
     */
    CriteriaQuery<T> createGetQuery(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder);
}

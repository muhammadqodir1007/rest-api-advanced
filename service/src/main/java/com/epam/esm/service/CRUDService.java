package com.epam.esm.service;

public interface CRUDService<T> extends CRDService<T> {

    /**
     * Method for updating an entity.
     *
     * @param id     ID of entity to update
     * @param entity entity, which include information to update
     * @return updated entity
     */
    T update(long id, T entity);
}

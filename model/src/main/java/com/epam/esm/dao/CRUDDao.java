package com.epam.esm.dao;

public interface CRUDDao<T> extends CRDDao<T> {

    /**
     * Method for updating an entity in a table.
     *
     * @param item entity object to update
     * @return updated entity
     */
    T update(T item);
}

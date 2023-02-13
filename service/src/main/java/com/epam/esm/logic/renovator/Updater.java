package com.epam.esm.logic.renovator;

import java.util.List;

public interface Updater<T> {

    /**
     * Method for updating object.
     *
     * @param oldEntity entity with old parameters
     * @param newEntity entity with new parameters
     * @return updating entity object
     */
    T updateObject(T newEntity, T oldEntity);

    List<T> updateListFromDatabase(List<T> newList);
}

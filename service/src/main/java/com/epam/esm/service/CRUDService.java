package com.epam.esm.service;

import com.epam.esm.dto.response.ApiResponse;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * A generic CRUD service interface for managing entities.
 *
 * @param <T> the type of entity this service manages
 */
public interface CRUDService<T> {

    /**
     * Retrieves an entity object by ID.
     *
     * @param id ID of the entity to retrieve
     * @return the entity object
     */
    T getById(long id);

    /**
     * Retrieves all entities.
     *
     * @param page the page number for pagination
     * @param size the page size for pagination
     * @return a list of all entities
     */
    List<T> getAll(int page, int size);

    /**
     * Saves an entity.
     *
     * @param entity the entity to save
     * @return the saved entity
     */
    T insert(T entity);

    /**
     * Updates an entity by ID.
     *
     * @param id     the ID of the entity to update
     * @param entity the entity information to update
     * @return the updated entity
     */
    T update(long id, T entity);

    /**
     * Removes an entity by ID.
     *
     * @param id the ID of the entity to remove
     */
    ApiResponse removeById(long id);

    /**
     * Searches for a list of entities by specific parameters.
     *
     * @param requestParams the request parameters from the URL
     * @param page          the page number for pagination
     * @param size          the page size for pagination
     * @return a list of entities matching the search criteria
     */
    List<T> search(MultiValueMap<String, String> requestParams, int page, int size);
}

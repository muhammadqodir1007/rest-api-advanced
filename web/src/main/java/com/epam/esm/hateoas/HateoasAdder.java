package com.epam.esm.hateoas;

import org.springframework.hateoas.RepresentationModel;

public interface HateoasAdder<T extends RepresentationModel<T>> {

    /**
     * Method for adding links to entity object.
     *
     * @param entity entity to which links will be added
     */
    void addLinks(T entity);
}

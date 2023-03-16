package com.epam.esm.dto.converter;

public interface DtoConverter<E, D> {

    /**
     * Method for converting dto to entity
     *
     * @param dto dto entity to convert
     * @return converted entity
     */

    E convertToEntity(D dto);

    /**
     * Method for onverting entity to dto
     *
     * @param entity source entity to convert
     * @return converted dto entity
     */
    D convertToDto(E entity);
}

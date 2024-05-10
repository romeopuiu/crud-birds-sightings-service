package com.romeo.birdssightingsservice.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 *  This class provides a flexible and reusable foundation for mapping
 *  between entity objects and DTO
 *
 */

public abstract class BaseMapper<E, D> {

    // Method to convert a DTO (Data Transfer Object) to an entity
    public abstract E convertToEntity(D dto, Object... args);

    // Method to convert an entity to a DTO (Data Transfer Object)
    public abstract D convertToDto(E entity, Object... args);

    // Method to convert a collection of DTOs to a collection of entities
    public Collection<E> convertToEntity(Collection<D> dto, Object... args) {
        // Utilizes Java Streams to map each DTO to its corresponding entity
        return dto.stream().map(d -> convertToEntity(d, args)).collect(Collectors.toList());
    }

    // Method to convert a collection of DTOs to a collection of entities
    public Collection<D> convertToDto(Collection<E> entity, Object... args) {
        // Utilizes Java Streams to map each entity to its corresponding DTO
        return entity.stream().map(e -> convertToDto(e, args)).collect(Collectors.toList());
    }
}

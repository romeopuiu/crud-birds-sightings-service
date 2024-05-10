package com.romeo.birdssightingsservice.mapper;

import com.romeo.birdssightingsservice.domain.Bird;
import com.romeo.birdssightingsservice.domain.Sighting;
import com.romeo.birdssightingsservice.dto.BirdDTO;
import com.romeo.birdssightingsservice.dto.SightingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  This class serves as a mapper between Bird entity objects and BirdDTO
 *  data transfer objects, providing methods for converting between the two representations
 */

@Component
@RequiredArgsConstructor
public class BirdMapper extends BaseMapper<Bird, BirdDTO>{

    private final SightingMapper sightingMapper;

    // Method to convert a BirdDTO to a Bird entity
    @Override
    public Bird convertToEntity(BirdDTO dto, Object... args) {
        if (dto == null) {
            return null;
        }

        List<Sighting> sightings = null;
        if (dto.sightings() != null) {
            sightings = dto.sightings().stream()
                    .map(sightingMapper::convertToEntity)
                    .collect(Collectors.toList());
        }

        return new Bird(
                dto.id(),
                dto.name(),
                sightings,
                dto.color(),
                dto.weight(),
                dto.height()
        );
    }

    @Override
    public BirdDTO convertToDto(Bird entity, Object... args) {
        if (entity == null) {
            return null;
        }

        List<SightingDTO> sightingDTOs = null;
        if (entity.getSightings() != null) {
            sightingDTOs = entity.getSightings().stream()
                    .map(sightingMapper::convertToDto)
                    .collect(Collectors.toList());
        }

        return new BirdDTO(
                entity.getId(),
                entity.getName(),
                sightingDTOs,
                entity.getColor(),
                entity.getWeight(),
                entity.getHeight()
        );
    }

}

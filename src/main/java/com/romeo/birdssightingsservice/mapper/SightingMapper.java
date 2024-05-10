package com.romeo.birdssightingsservice.mapper;


import com.romeo.birdssightingsservice.domain.Sighting;
import com.romeo.birdssightingsservice.dto.BirdDTO;
import com.romeo.birdssightingsservice.dto.SightingDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


@Component
public class SightingMapper extends BaseMapper<Sighting, SightingDTO> {


    @Override
    public Sighting convertToEntity(SightingDTO dto, Object... args) {
        Sighting sighting = new Sighting();

        if (dto != null) {
            BeanUtils.copyProperties(dto, sighting);
        }
        return sighting;
    }

    @Override
    public SightingDTO convertToDto(Sighting entity, Object... args) {
        if (entity == null) {
            return null;
        }

        return new SightingDTO(
                entity.getId().toString(),
                entity.getLocation(),
                new BirdDTO(entity.getBird().getId(), entity.getBird().getName(),
                        null, entity.getBird().getColor(), entity.getBird().getWeight(),
                        entity.getBird().getHeight()), entity.getDateTime()
        );
    }



}

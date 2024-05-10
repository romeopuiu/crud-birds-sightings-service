package com.romeo.birdssightingsservice.service;


import com.romeo.birdssightingsservice.domain.Bird;
import com.romeo.birdssightingsservice.domain.Sighting;
import com.romeo.birdssightingsservice.dto.BirdDTO;
import com.romeo.birdssightingsservice.exception.ResourceNotFoundException;
import com.romeo.birdssightingsservice.mapper.BirdMapper;
import com.romeo.birdssightingsservice.mapper.SightingMapper;
import com.romeo.birdssightingsservice.repository.IBirdRepository;
import com.romeo.birdssightingsservice.repository.ISightingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * This class represents a service component responsible
 * for managing business logic related to birds
 */
@Transactional
@Service
@RequiredArgsConstructor
public class BirdService {
    private final IBirdRepository iBirdRepository;
    private final BirdMapper birdMapper;
    private final ISightingRepository iSightingRepository;
    private final SightingMapper sightingMapper;


    public BirdDTO saveBird(BirdDTO birdDTO) {
        // Convert BirdDTO to Bird entity
        var bird = birdMapper.convertToEntity(birdDTO);

        // Convert and save Sightings
        var sightingDTOS = birdDTO.sightings();
        if (sightingDTOS != null && !sightingDTOS.isEmpty()) {
            List<Sighting> sightings = (List<Sighting>) sightingMapper.convertToEntity(sightingDTOS);
            Bird finalBird = bird;
            sightings.forEach(sighting -> {
                // Set the associated bird for each sighting
                sighting.setBird(finalBird);
            });
         //   iSightingRepository.insert(sightings);
            // Update the bird with the saved sightings
           // bird.setSightings(sightings);
            bird = iBirdRepository.save(bird);
        }

        return birdMapper.convertToDto(bird);
    }

    public List<BirdDTO> getAllBirds() {
        // Retrieve all Bird entities from the repository
        List<Bird> birds = iBirdRepository.findAll();

        // Convert Bird entities to BirdDTO objects
        List<BirdDTO> birdDTOs = new ArrayList<>();
        for (Bird bird : birds) {
            BirdDTO birdDTO = birdMapper.convertToDto(bird);
            birdDTOs.add(birdDTO);
        }

        return birdDTOs;

    }


    /**
     * This method is used for update a bird.
     */
    public BirdDTO updateBird(String id, BirdDTO birdDTO) {
        var bird = iBirdRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bird not exist with id :" + id));

        bird.setName(birdDTO.name());
        bird.setColor(birdDTO.color());
        bird.setHeight(birdDTO.height());
        bird.setWeight(birdDTO.weight());
        List<Sighting> sightings = (List<Sighting>) sightingMapper.convertToEntity(birdDTO.sightings());
        sightings.forEach(sighting -> {
            // Set the associated bird for each sighting
            sighting.setBird(bird);
        });
        iSightingRepository.saveAll(sightings);
        bird.setSightings(sightings);
        iBirdRepository.save(bird);

        return birdMapper.convertToDto(bird);
    }

    /**
     * This method is used to returns a bird by name
     */
    public BirdDTO findBirdByName(String name) {
        var bird = iBirdRepository.findByName(name);
        if (bird == null) {
            throw new ResourceNotFoundException("Bird not found with name: " + name);
        }

        return birdMapper.convertToDto(bird);
    }

    /**
     * This method is used to returns a bird by color
     */
    public BirdDTO findBirdByColor(String color) {
        Bird bird = iBirdRepository.findByColor(color);
        if (bird == null) {
            throw new ResourceNotFoundException("Bird not found with color: " + color);
        }
        return birdMapper.convertToDto(bird);

    }

    /**
     * This method is used to delete a bird by id
     */
    public void deleteBird(String id) {
        if (iBirdRepository.existsById(id)) {
            iBirdRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Bird not exist with id :" + id);
        }
    }


}

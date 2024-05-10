package com.romeo.birdssightingsservice.service;


import com.romeo.birdssightingsservice.domain.Sighting;
import com.romeo.birdssightingsservice.dto.SightingDTO;
import com.romeo.birdssightingsservice.exception.ResourceNotFoundException;
import com.romeo.birdssightingsservice.mapper.SightingMapper;
import com.romeo.birdssightingsservice.repository.IBirdRepository;
import com.romeo.birdssightingsservice.repository.ISightingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class SightingService {

    private final ISightingRepository iSightingRepository;
    // Dependency on SightingMapper for entity-DTO mapping
    private final SightingMapper sightingMapper;
    // Dependency on IBirdRepository for database operations
    private final IBirdRepository iBirdRepository;



    /**
     * This method is used to save a sighting
     */
    public SightingDTO createSighting(String birdId, SightingDTO sightingDTO) {
        log.info("Save a SightingDTO: {}", sightingDTO);
        // Retrieve the Bird entity from the database
        var bird = iBirdRepository.findById(birdId)
                .orElseThrow(() -> new ResourceNotFoundException("Bird with birdId not found " + birdId));
        // Convert SightingDTO to Sighting entity
        var sighting = sightingMapper.convertToEntity(sightingDTO);
        // Set the associated Bird for the Sighting
        sighting.setBird(bird);
        // Save the Sighting entity
        sighting = iSightingRepository.save(sighting);

        return sightingMapper.convertToDto(sighting);
    }


    /**
     * This method is used to returns all sightings allocated to id of bird.
     */
    public List<SightingDTO> findAllSightingsByBirdId(String birdId) {
        if (!iBirdRepository.existsById(birdId)) {
            throw new ResourceNotFoundException("Bird not exist with id: " + birdId);
        }

        var sightings = iSightingRepository.findByBirdId(birdId);
        List<SightingDTO> sightingDTOs = sightings.stream()
                .map(sightingMapper::convertToDto)
                .toList();

        return new ArrayList<>(sightingDTOs);
    }

    /**
     * This method is used to returns all sightings.
     */
    public List<SightingDTO> findAllSightings() {
        var sightings = iSightingRepository.findAll();
        return getAllSightings(sightings);
    }

    private List<SightingDTO> getAllSightings(List<Sighting> sightings) {
        List<SightingDTO> sightingDTOs = new ArrayList<>();
        for (Sighting sighting : sightings) {
            var sightingDTO = sightingMapper.convertToDto(sighting);
            sightingDTOs.add(sightingDTO);
        }
        return sightingDTOs;
    }

    /**
     * This method is used to update sighting.
     */
    public SightingDTO updateSighting(String id, SightingDTO sightingDTO) {
        log.info("Update a SightingDTO: {}", sightingDTO);
        var sighting = iSightingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sighting not exist with id :" + id));

        sighting.setLocation(sightingDTO.location());
        sighting.setDateTime(sightingDTO.dateTime());
        iSightingRepository.save(sighting);
        sightingDTO = sightingMapper.convertToDto(sighting);

        return sightingDTO;
    }

    /**
     * This method is used to returns a SightingDTO object by id.
     */
    public SightingDTO findSighting(String id) {
        var sighting = iSightingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sighting not exist with id:" + id));

        return sightingMapper.convertToDto(sighting);
    }

    /**
     * This method is used to returns all sightings by location.
     */
    public List<SightingDTO> findByLocation(String location) {
        log.info("Get all sightings by location: {}", location);
        var birds = iSightingRepository.findByLocation(location);
        return new ArrayList<>(sightingMapper.convertToDto(birds));
    }

    /**
     * This method is used to returns all sightings by date time.
     */
    public List<SightingDTO> findByDateTime(LocalDateTime dateTime) {
        log.info("Get all sightings by dateTime: {}", dateTime);
        var birds = iSightingRepository.findByDateTime(dateTime);
        return new ArrayList<>(sightingMapper.convertToDto(birds));
    }


    /**
     * This method is used to delete sighting by id
     */
    public void deleteSighting(String id) {
        if (iSightingRepository.existsById(id)) {
            iSightingRepository.deleteById(id);
        } else  {
            throw new ResourceNotFoundException("Sighting not exist with id :" + id);
        }
    }
}

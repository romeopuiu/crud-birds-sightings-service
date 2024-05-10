package com.romeo.birdssightingsservice.controller;


import com.romeo.birdssightingsservice.dto.SightingDTO;
import com.romeo.birdssightingsservice.service.SightingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SightingController {

    private final SightingService sightingService;

    @GetMapping("/birds/{birdId}/sightings")
    public ResponseEntity<List<SightingDTO>> getAllSightingsByBirdId(@PathVariable(value = "birdId") Long birdId) {
        log.info("REST request to get sightings by birdId : {}", birdId);
        return ResponseEntity.ok(sightingService.findAllSightingsByBirdId(String.valueOf(birdId)));

    }

    /**
     * This method is used for save a Sighting allocated to a bird
     */
    @PostMapping("/birds/{birdId}/sightings")
    public ResponseEntity<SightingDTO> saveSighting(@PathVariable(value = "birdId") Long birdId,
                                                    @RequestBody SightingDTO sightingDTO) {
        log.info("REST request to save sightingDTO: {}", sightingDTO);
        return ResponseEntity.ok(sightingService.createSighting(String.valueOf(birdId), sightingDTO));
    }

    /**
     * This method is used for update a sighting
     */
    @PutMapping("/sighting/{id}")
    public ResponseEntity<SightingDTO> updateSighting(@PathVariable("id") String id,
                                                      @RequestBody SightingDTO sightingDTO) {
        log.info("REST request to update a sightingDTO: {}", sightingDTO);
        return ResponseEntity.ok(sightingService.updateSighting(id, sightingDTO));
    }

    /**
     * This method is used for get a single Sighting by Id
     */
    @GetMapping("/sightings/{id}")
    public ResponseEntity<SightingDTO> getSightingById(@PathVariable(value = "id") String id) {
        log.info("REST request to get sighting by id : {}", id);
        return  ResponseEntity.ok(sightingService.findSighting(id));
    }

    /**
     * This method is used for get all sightings by location
     */
    @GetMapping("/sightings/location")
    public ResponseEntity<List<SightingDTO>> getSightingByLocation(@RequestParam String location) {
        log.info("REST request to get sighting by location : {}", location);
        return  ResponseEntity.ok(sightingService.findByLocation(location));
    }

    /**
     * This method is used for get all sightings by dateTime
     */
    @GetMapping("/sightings/date-time")
    public ResponseEntity<List<SightingDTO>> getSightingByLocation(@RequestParam("dateTime")
                                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                   LocalDateTime dateTime) {
        return  ResponseEntity.ok(sightingService.findByDateTime(dateTime));
    }



    /**
     * This method is used for returns all sightings
     */
    @GetMapping("/sightings")
    public ResponseEntity<List<SightingDTO>> getAllSightings() {
        log.info("REST request for returns all sightings");
        return ResponseEntity.ok(sightingService.findAllSightings());
    }


    /**
     * This method is used for delete a Sighting by id
     */
    @DeleteMapping("/sightings/{id}")
    public void deleteSighting(@PathVariable String id) {
        log.info("REST request to delete Sighting : {}", id);
        sightingService.deleteSighting(id);
    }

}

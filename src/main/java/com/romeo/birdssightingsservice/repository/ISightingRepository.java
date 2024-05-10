package com.romeo.birdssightingsservice.repository;

import com.romeo.birdssightingsservice.domain.Bird;
import com.romeo.birdssightingsservice.domain.Sighting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ISightingRepository extends MongoRepository<Sighting, String> {


    /**
     * This method is used to find all Sightings by birdId
     */
    List<Sighting> findByBirdId(String birdId);
    List<Sighting> findByBird(@Param("bird") Bird bird);


    /**
     * This method is used to find all Sightings by location
     */
    List<Sighting> findByLocation(String location);

    /**
     * This method is used to find all Sightings by dateTime
     */
    List<Sighting> findByDateTime(LocalDateTime dateTime);
}

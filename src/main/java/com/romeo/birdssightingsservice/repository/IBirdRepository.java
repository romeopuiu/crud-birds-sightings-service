package com.romeo.birdssightingsservice.repository;

import com.romeo.birdssightingsservice.domain.Bird;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface IBirdRepository extends MongoRepository<Bird, String> {

    /**
     * This method is used to find bird by color
     */
    Bird findByColor(String color);

    /**
     * This method is used to find bird by name
     */
    Bird findByName(String name);
}

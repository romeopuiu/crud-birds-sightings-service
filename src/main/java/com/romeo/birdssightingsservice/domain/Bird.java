package com.romeo.birdssightingsservice.domain;



import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


@Document(collection = "bird")
@Data
@AllArgsConstructor
public class Bird {

    @Id
    private String id;
    @Field(value = "name")
    private String name;
    @DBRef(lazy = true)
    private List<Sighting> sightings;
    @Field(value = "color")
    private String color;
    @Field(value = "weight")
    private int weight;
    @Field(value = "height")
    private int height;

}

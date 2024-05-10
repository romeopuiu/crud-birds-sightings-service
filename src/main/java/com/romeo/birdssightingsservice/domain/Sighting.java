package com.romeo.birdssightingsservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "sighting")
public class Sighting {

    @Id
    private String id;
    private Bird bird;
    @Field(value = "location")
    private String location;
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    @Field(value = "date_time")
    private LocalDateTime dateTime;
}

package com.romeo.birdssightingsservice.dto;


import java.time.LocalDateTime;


public record SightingDTO(String id,
                          String location,
                          BirdDTO bird,
                          LocalDateTime dateTime
) {
}

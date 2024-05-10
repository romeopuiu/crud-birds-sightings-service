package com.romeo.birdssightingsservice.dto;


import java.util.List;


public record BirdDTO(String id,
                      String name,
                      List<SightingDTO> sightings,
                      String color,
                      int weight,
                      int height) {

}

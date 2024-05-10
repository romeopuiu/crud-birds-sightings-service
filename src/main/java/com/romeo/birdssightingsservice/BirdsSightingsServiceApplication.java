package com.romeo.birdssightingsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories
@SpringBootApplication
public class BirdsSightingsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BirdsSightingsServiceApplication.class, args);
	}

}

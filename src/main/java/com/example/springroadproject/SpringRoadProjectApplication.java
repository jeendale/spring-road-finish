package com.example.springroadproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringRoadProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRoadProjectApplication.class, args);
    }

}

package com.example.mealfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class MealFinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MealFinderApplication.class, args);
    }

}

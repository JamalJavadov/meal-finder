package com.example.mealfinder.repository;

import com.example.mealfinder.model.entity.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChefRepository extends JpaRepository<Chef,Long> {
    Optional<Chef> findByEmail(String email);
}

package com.example.mealfinder.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.example.mealfinder.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}

package com.example.mealfinder.repository;
import com.example.mealfinder.model.entity.Chef;
import com.example.mealfinder.model.entity.ChefRefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChefRefreshTokenRepository extends JpaRepository<ChefRefreshToken,Long> {
    Optional<ChefRefreshToken> findRefreshTokenByToken(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM ChefRefreshToken r WHERE r.chef = :chef")
    void deleteByChef(@Param("chef") Chef chef);
}

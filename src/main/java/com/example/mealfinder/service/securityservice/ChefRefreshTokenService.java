package com.example.mealfinder.service.securityservice;
import com.example.mealfinder.model.entity.Chef;
import com.example.mealfinder.model.entity.ChefRefreshToken;
import com.example.mealfinder.repository.ChefRefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChefRefreshTokenService {
    private final long refreshTokenDurationMs = 7L * 24 * 60 * 60 * 1000;
    private final ChefRefreshTokenRepository chefRefreshTokenRepository;

    @Transactional
    public ChefRefreshToken createRefreshToken(Chef chef){
        deleteByChef(chef);
        ChefRefreshToken refreshToken = ChefRefreshToken.builder()
                .chef(chef)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .build();
        return chefRefreshTokenRepository.save(refreshToken);
    }

    public boolean isExpired(ChefRefreshToken refreshToken){
        return refreshToken.getExpiryDate().isBefore(Instant.now());
    }

    @Transactional
    public void deleteByChef(Chef chef){
        chefRefreshTokenRepository.deleteByChef(chef);
    }
}

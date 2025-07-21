package com.example.mealfinder.controller;

import com.example.mealfinder.model.dto.auth.AuthenticationRequestDto;
import com.example.mealfinder.model.dto.auth.AuthenticationResponseDto;
import com.example.mealfinder.model.dto.chefDto.ChefRequestDto;
import com.example.mealfinder.model.dto.code.AuthCodeVerficationDto;
import com.example.mealfinder.model.dto.refreshtoken.TokenRefreshRequestDto;
import com.example.mealfinder.model.dto.refreshtoken.TokenRefreshResponseDto;
import com.example.mealfinder.model.entity.Chef;
import com.example.mealfinder.repository.ChefRefreshTokenRepository;
import com.example.mealfinder.service.securityservice.AuthenticationService;
import com.example.mealfinder.service.securityservice.ChefRefreshTokenService;
import com.example.mealfinder.service.securityservice.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authchef")
public class ChefAuthenticationController {
    private final ChefRefreshTokenService chefRefreshTokenService;
    private final AuthenticationService authenticationService;
    private final ChefRefreshTokenRepository chefRefreshTokenRepository;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody ChefRequestDto chefRequestDto){
        return ResponseEntity.ok(authenticationService.chefRegister(chefRequestDto));
    }

    @PostMapping("/send-code")
    public ResponseEntity<String> authenticate(@Valid @RequestBody AuthenticationRequestDto request) {
        return ResponseEntity.ok(authenticationService.authenticateSendCode(request));
    }

    @PostMapping("/verify-code")
    public ResponseEntity<AuthenticationResponseDto> verifyCode(@Valid @RequestBody AuthCodeVerficationDto authCodeVerfication) {
        return ResponseEntity.ok(authenticationService.verifyCode(authCodeVerfication));
    }

    @PreAuthorize("hasRole('CHEF')")
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequestDto tokenRefresh){
        return chefRefreshTokenRepository.findRefreshTokenByToken(tokenRefresh.getRefreshToken())
                .map(token->{
                    if (chefRefreshTokenService.isExpired(token)){
                        chefRefreshTokenRepository.delete(token);
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired. Please log in again.");
                    }
                    Chef chef = token.getChef();
                    String newAccessToken = jwtService.generatedToken(chef);
                    return ResponseEntity.ok(new TokenRefreshResponseDto(newAccessToken,token.getToken()));

                })
                .orElseGet(()->ResponseEntity.status(HttpStatus.NOT_FOUND).body("Refresh Token Not Found"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody TokenRefreshRequestDto tokenRefreshRequestDto){
        return chefRefreshTokenRepository.findRefreshTokenByToken(tokenRefreshRequestDto.getRefreshToken())
                .map(refreshToken -> {
                    chefRefreshTokenService.deleteByChef(refreshToken.getChef());
                    return ResponseEntity.ok("Chef logged out successfully");
                }).orElseGet(()->ResponseEntity.status(HttpStatus.NOT_FOUND).body("Refresh Token Not Found"));
    }
}

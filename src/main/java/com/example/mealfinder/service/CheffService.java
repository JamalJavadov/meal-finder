package com.example.mealfinder.service;

import com.example.mealfinder.exception.ChefNotFoundException;
import com.example.mealfinder.model.dto.chefDto.ChefResponseDto;
import com.example.mealfinder.model.dto.chefDto.ChefUpdateDto;
import com.example.mealfinder.model.entity.Chef;
import com.example.mealfinder.model.entity.Meal;
import com.example.mealfinder.model.mapper.ChefMapper;
import com.example.mealfinder.repository.ChefRepository;
import com.example.mealfinder.service.notification.EmailService;
import com.example.mealfinder.service.notification.VerificationCodeStore;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CheffService {
    private final ChefRepository chefRepository;
    private final ChefMapper chefMapper;
    private final VerificationCodeStore verificationCodeStore;
    private final EmailService emailService;

    public ChefResponseDto getChefDeatils(String email){
        Chef chef = chefRepository.findByEmail(email).orElseThrow(()->new ChefNotFoundException("Chef Not Found"));
        ChefResponseDto chefResponseDto = chefMapper.toDto(chef);
        chefResponseDto.setMeals(
                chef.getMeals().stream()
                        .map(Meal::getMealName)
                        .toList()
        );
        return chefResponseDto;
    }

    @Transactional
    public ChefResponseDto update(String email, ChefUpdateDto chefUpdateDto) {
        Chef chef = chefRepository.findByEmail(email).orElseThrow(()->new ChefNotFoundException("Chef Not Found"));
        Chef updatedChef = chefMapper.toEntity(chefUpdateDto,chef);
        return chefMapper.toDto( chefRepository.save(updatedChef));
    }



}

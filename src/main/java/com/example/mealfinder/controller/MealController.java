package com.example.mealfinder.controller;

import com.example.mealfinder.model.dto.mealDto.MealCreateDto;
import com.example.mealfinder.model.dto.mealDto.MealFindDto;
import com.example.mealfinder.model.dto.mealDto.MealResponseDto;
import com.example.mealfinder.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/meals")
public class MealController {
    private final MealService mealService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/get-meals-filter")
    public ResponseEntity<List<MealResponseDto>> getMeals(@RequestBody MealFindDto mealFindDto){
        return ResponseEntity.ok(mealService.getMeals(mealFindDto));
    }

    @PreAuthorize("hasRole('CHEF')")
    @PostMapping( "/create-meals")
    public ResponseEntity<MealResponseDto> createMeal(@RequestBody MealCreateDto mealCreateDto, Authentication authentication){
        return ResponseEntity.ok(mealService.createMeal(mealCreateDto, authentication.getName()));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get-all-meals")
    public ResponseEntity<List<MealResponseDto>> getMeals(){
        return ResponseEntity.ok(mealService.getAllMeals());
    }

}

package com.example.mealfinder.controller;

import com.example.mealfinder.model.dto.chefDto.ChefResponseDto;
import com.example.mealfinder.model.dto.chefDto.ChefUpdateDto;
import com.example.mealfinder.service.CheffService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/chef")
@PreAuthorize("hasRole('CHEF')")
public class ChefController {

    private final CheffService cheffService;

    @PreAuthorize("hasRole('CHEF')")
    @GetMapping("/profile")
    public ResponseEntity<ChefResponseDto> getChefProfile(Authentication authentication){
        return ResponseEntity.ok(cheffService.getChefDeatils(authentication.getName()   ));
    }

    @PreAuthorize("hasRole('CHEF')")
    @PutMapping("/update")
    public ResponseEntity<ChefResponseDto> update(Authentication authentication, @RequestBody ChefUpdateDto chefUpdateDto){
        return ResponseEntity.ok(cheffService.update(authentication.getName(),chefUpdateDto));
    }
}

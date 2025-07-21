package com.example.mealfinder.controller;

import com.example.mealfinder.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageUploadController {

    private final ImageUploadService imageUploadService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, Authentication authentication) {
        String imageUrl = imageUploadService.uploadImage(file,authentication.getName());
        return ResponseEntity.ok(imageUrl);
    }

    @PostMapping("/upload-meal/{id}")
    public ResponseEntity<String> uploadMealImage(@RequestParam("file") MultipartFile file,@RequestParam("id") long id){
        return ResponseEntity.ok(imageUploadService.uploadImageMeal(file,id));
    }

}

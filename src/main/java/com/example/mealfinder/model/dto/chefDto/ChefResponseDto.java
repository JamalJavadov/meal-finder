package com.example.mealfinder.model.dto.chefDto;

import com.example.mealfinder.model.entity.Meal;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChefResponseDto {
    private String chefName;

    private String email;

    private String phoneNumber;

    private LocalDateTime localDateTime;

    private List<String> meals;

    private String imagePath;
}

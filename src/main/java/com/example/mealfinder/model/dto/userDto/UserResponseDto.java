package com.example.mealfinder.model.dto.userDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private String userName;
    private String email;
    private String imagePath;
}


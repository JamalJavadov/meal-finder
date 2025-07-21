package com.example.mealfinder.model.dto.mealDto;

import com.example.mealfinder.model.dto.productDto.ProductResponseDto;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealResponseDto {

    private  long id;

    private String mealName;

    private List<ProductResponseDto> products;

    private String mealImage;

    private String chefName;
}

package com.example.mealfinder.model.dto.mealDto;

import com.example.mealfinder.model.dto.productDto.ProductRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealFindDto {
    @NotNull(message = "Məhsullar siyahısı boş ola bilməz.")
    @Size(min = 1, message = "Ən azı bir məhsul əlavə olunmalıdır.")
    private List<@Valid ProductRequestDto> products;
}

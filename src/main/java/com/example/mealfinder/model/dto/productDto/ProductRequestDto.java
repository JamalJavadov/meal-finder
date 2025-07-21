package com.example.mealfinder.model.dto.productDto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {
    @NotBlank(message = "Məhsulun adı boş ola bilməz.")
    @Size(max = 50, message = "Məhsulun adı maksimum 50 simvol ola bilər.")
    @Pattern(
            regexp = "^[^<>%]{1,50}$",
            message = "Məhsulun adında '<', '>' və '%' simvolları olmamalıdır."
    )
    private String productName;

    @Positive(message = "Məhsulun çəkisi 0-dan böyük olmalıdır.")
    private double productWeight;

    @Min(value = 0, message = "Məhsulun sayı ən azı 1 olmalıdır.")
    private int productCount;

    @Size(max = 200, message = "Təsvir maksimum 200 simvol ola bilər.")
    @Pattern(
            regexp = "^[^<>%]*$",
            message = "Təsvir sahəsində '<', '>' və '%' simvolları olmamalıdır."
    )
    private String description;

}

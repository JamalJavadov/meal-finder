package com.example.mealfinder.model.dto.productDto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {
    private String productName;

    private double productWeight;

    private int productCount;

    private String description;
}

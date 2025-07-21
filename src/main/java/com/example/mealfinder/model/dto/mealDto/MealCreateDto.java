package com.example.mealfinder.model.dto.mealDto;

import com.example.mealfinder.model.dto.productDto.ProductRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealCreateDto {
    @NotBlank(message = "Yeməyin adı boş ola bilməz.")
    @Size(max = 50, message = "Yeməyin adı maksimum 50 simvol ola bilər.")
    @Pattern(
            regexp = "^[^<>%]{1,50}$",
            message = "Yeməyin adında '<', '>' və '%' simvolları olmamalıdır."
    )
    private String mealName;

    @NotNull(message = "Məhsullar siyahısı boş ola bilməz.")
    @Size(min = 1, message = "Ən azı bir məhsul əlavə olunmalıdır.")
    private List<@Valid ProductRequestDto> products;
}

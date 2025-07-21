package com.example.mealfinder.model.dto.chefDto;

import com.example.mealfinder.model.entity.Meal;
import com.example.mealfinder.model.entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChefUpdateDto {
    @NotBlank(message = "Ad boş ola bilməz.")
    @Size(max = 50, message = "Ad maksimum 50 simvol ola bilər.")
    @Pattern(
            regexp = "^[^<>%]{1,50}$",
            message = "Ad '<', '>' və '%' simvollarını ehtiva etməməlidir."
    )
    private String chefName;
}

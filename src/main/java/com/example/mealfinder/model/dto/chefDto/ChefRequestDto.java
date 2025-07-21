package com.example.mealfinder.model.dto.chefDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChefRequestDto {

    @NotBlank(message = "Ad boş ola bilməz.")
    @Size(max = 50, message = "Ad maksimum 50 simvol ola bilər.")
    @Pattern(
            regexp = "^[^<>%]{1,50}$",
            message = "Ad '<', '>' və '%' simvollarını ehtiva etməməlidir."
    )
    private String chefName;

    @NotBlank(message = "Email boş ola bilməz.")
    @Email(message = "Düzgün email formatı tələb olunur.")
    private String email;

    @NotBlank(message = "Telefon nömrəsi boş ola bilməz.")
    @Pattern(
            regexp = "^(\\+994|0)(50|51|55|70|77)[0-9]{7}$",
            message = "Telefon nömrəsi Azərbaycan mobil formatına uyğun olmalıdır (məs: +994501234567)."
    )
    private String phoneNumber;

    @NotBlank(message = "Parol boş ola bilməz.")
    @Pattern(
            regexp = "^(?=.*[a-zA-ZəöüıçşğƏÖÜIÇŞĞ])(?=.*[A-ZƏÖÜIÇŞĞ])(?=.*[a-zəöüıçşğ])(?=.*\\d)(?=.*[^a-zA-ZəöüıçşğƏÖÜIÇŞĞ\\d<>%\\s])[^<>%\\s]{8,50}$",
            message = "Parol 8-50 simvol arasında olmalı, ən azı bir böyük hərf, bir kiçik hərf, bir rəqəm və bir xüsusi simvol daxil etməlidir. '<', '>', '%', və boşluq simvolları qadağandır."
    )
    private String password;

    // getter və setter-lər (lombok ilə istifadə edirsənsə @Data əlavə edə bilərsən)
}

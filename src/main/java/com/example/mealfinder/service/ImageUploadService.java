package com.example.mealfinder.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.mealfinder.model.entity.Chef;
import com.example.mealfinder.model.entity.Meal;
import com.example.mealfinder.model.entity.User;
import com.example.mealfinder.repository.ChefRepository;
import com.example.mealfinder.repository.MealRepository;
import com.example.mealfinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageUploadService {
    private final UserRepository userRepository;
    private final ChefRepository chefRepository;
    private final Cloudinary cloudinary;
    private final MealRepository mealRepository;

    public String uploadImage(MultipartFile file, String email) {
        // 1. Fayl növü yoxlaması: Yalnız PNG və JPEG formatlarına icazə verilir.
        String contentType = file.getContentType();
        if (contentType == null || (!contentType.equals("image/png") && !contentType.equals("image/jpeg"))) {
            throw new IllegalArgumentException("Yalnız PNG və JPEG formatlı şəkillər yükləyə bilərsiniz.");
        }

        try {
            // Cloudinary-yə şəkli yüklə
            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.emptyMap() // Əlavə Cloudinary parametrləri burada təyin edilə bilər
            );

            // 2. İstifadəçi və ya aşpaz obyektini tap və şəkil yolunu yenilə
            Optional<User> user = userRepository.findByEmail(email);
            Optional<Chef> chef = chefRepository.findByEmail(email);

            String imageUrl = uploadResult.get("secure_url").toString();

            if (user.isPresent()) {
                user.get().setImagePath(imageUrl);
                userRepository.save(user.get());
            } else if (chef.isPresent()) {
                chef.get().setImagePath(imageUrl);
                chefRepository.save(chef.get());
            }

            return imageUrl;
        } catch (IOException e) {
            // Şəkil yüklənərkən baş verən IO xətalarını idarə et
            throw new RuntimeException("Şəkil yüklənərkən daxili xəta baş verdi.", e);
        }
    }

    public String uploadImageMeal(MultipartFile file, long id) {
        String contentType = file.getContentType();
        if (contentType == null || (!contentType.equals("image/png") && !contentType.equals("image/jpeg"))) {
            throw new IllegalArgumentException("Yalnız PNG və JPEG formatlı şəkillər yükləyə bilərsiniz.");
        }

        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.emptyMap()
            );

            // 2. İstifadəçi və ya aşpaz obyektini tap və şəkil yolunu yenilə
            Optional<Meal> meal = mealRepository.findById(id);

            String imageUrl = uploadResult.get("secure_url").toString();

            if (meal.isPresent()) {
                meal.get().setMealImage(imageUrl);
                mealRepository.save(meal.get());
            }
            return imageUrl;

        } catch (IOException e) {
            // Şəkil yüklənərkən baş verən IO xətalarını idarə et
            throw new RuntimeException("Şəkil yüklənərkən daxili xəta baş verdi.", e);
        }
    }
}
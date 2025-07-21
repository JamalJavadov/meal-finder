package com.example.mealfinder.service;

import com.example.mealfinder.exception.ChefNotFoundException;
import com.example.mealfinder.model.dto.mealDto.MealCreateDto;
import com.example.mealfinder.model.dto.mealDto.MealFindDto;
import com.example.mealfinder.model.dto.mealDto.MealResponseDto;
import com.example.mealfinder.model.dto.productDto.ProductRequestDto;
import com.example.mealfinder.model.entity.Chef;
import com.example.mealfinder.model.entity.Meal;
import com.example.mealfinder.model.entity.Product;
import com.example.mealfinder.model.mapper.MealMapper;
import com.example.mealfinder.repository.ChefRepository;
import com.example.mealfinder.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealMapper mealMapper;
    private final MealRepository mealRepository;
    private final ChefRepository chefRepository;

    public MealResponseDto createMeal(MealCreateDto mealRequestDto,String email){
        Meal meal = mealMapper.toEntity(mealRequestDto);
        Chef chef = chefRepository.findByEmail(email).orElseThrow(()->new ChefNotFoundException("Chef not found with this gmail"));
        meal.setChef(chef);
        return mealMapper.toDto(mealRepository.save(meal));
    }

    public List<MealResponseDto> getMeals(MealFindDto mealFindDto){
        return mealRepository.findAll().stream()
                .filter(meal -> {
                    List<Product> products = meal.getProducts();
                    long productCount = products.stream().filter(product -> {
                        List<ProductRequestDto> productRequestDtos = mealFindDto.getProducts();
                         long productsForMeal =  productRequestDtos.stream().filter(productRequestDto -> {
                            if (productRequestDto.getProductName().equals(product.getProductName())){
                                return productRequestDto.getProductWeight() >= product.getProductWeight();
                            }else {
                            return false;
                            }
                        }).count();

                         return productsForMeal>=1;

                    }).count();
                    return products.size()<=productCount;
                })
                .map(mealMapper::toDto)
                .toList();
    }

    public List<MealResponseDto> getAllMeals(){
        return mealRepository.findAll()
                .stream()
                .map(meal -> {
                    MealResponseDto mealResponseDto = mealMapper.toDto(meal);
                    mealResponseDto.setChefName(meal.getChef().getChefName());
                    return mealResponseDto;
                })
                .toList();
    }


}

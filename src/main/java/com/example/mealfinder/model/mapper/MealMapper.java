package com.example.mealfinder.model.mapper;

import com.example.mealfinder.model.dto.mealDto.MealCreateDto;
import com.example.mealfinder.model.dto.mealDto.MealResponseDto;
import com.example.mealfinder.model.entity.Meal;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MealMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Meal toEntity(MealCreateDto mealRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MealResponseDto toDto (Meal meal);
}

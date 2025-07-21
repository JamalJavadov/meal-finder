package com.example.mealfinder.model.mapper;

import com.example.mealfinder.model.dto.chefDto.ChefResponseDto;
import com.example.mealfinder.model.dto.chefDto.ChefUpdateDto;
import com.example.mealfinder.model.entity.Chef;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ChefMapper {

    @Mapping(target = "meals",ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ChefResponseDto toDto(Chef chef);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Chef toEntity(ChefUpdateDto chefUpdateDto, @MappingTarget Chef chef);
}

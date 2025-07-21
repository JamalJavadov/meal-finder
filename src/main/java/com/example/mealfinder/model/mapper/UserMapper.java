package com.example.mealfinder.model.mapper;

import com.example.mealfinder.model.dto.userDto.UserRequestDto;
import com.example.mealfinder.model.dto.userDto.UserResponseDto;
import com.example.mealfinder.model.dto.userDto.UserUpdateDto;
import com.example.mealfinder.model.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User toEntity(UserRequestDto userRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "userName",source = "fullName")
    UserResponseDto toDto (User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User toDto (UserUpdateDto userUpdateDto, @MappingTarget User user);
}

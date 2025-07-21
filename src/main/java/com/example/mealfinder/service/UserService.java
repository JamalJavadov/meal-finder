package com.example.mealfinder.service;

import com.example.mealfinder.model.dto.userDto.UserResponseDto;
import com.example.mealfinder.model.dto.userDto.UserUpdateDto;
import com.example.mealfinder.model.entity.User;
import com.example.mealfinder.model.mapper.UserMapper;
import com.example.mealfinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto profile(String email){
        User user = userRepository.findByEmail(email).orElseThrow();
        return userMapper.toDto(user);
    }

    public UserResponseDto update(UserUpdateDto userUpdateDto, String email){
        User user = userRepository.findByEmail(email).orElseThrow();
        user = userMapper.toDto(userUpdateDto,user);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

}

package com.example.mealfinder.service.securityservice;


import com.example.mealfinder.exception.CodeExpireException;
import com.example.mealfinder.exception.EmailNotFound;
import com.example.mealfinder.exception.VerifyCodeFailedException;
import com.example.mealfinder.model.dto.auth.AuthenticationRequestDto;
import com.example.mealfinder.model.dto.auth.AuthenticationResponseDto;
import com.example.mealfinder.model.dto.chefDto.ChefRequestDto;
import com.example.mealfinder.model.dto.code.AuthCodeVerficationDto;
import com.example.mealfinder.model.dto.userDto.UserRequestDto;
import com.example.mealfinder.model.entity.Chef;
import com.example.mealfinder.model.entity.Role;
import com.example.mealfinder.model.entity.User;
import com.example.mealfinder.repository.ChefRepository;
import com.example.mealfinder.repository.UserRepository;
import com.example.mealfinder.service.notification.EmailService;
import com.example.mealfinder.service.notification.VerificationCodeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final VerificationCodeStore verificationCodeStore;
    private final UserRefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final ChefRepository chefRepository;
    private final ChefRefreshTokenService chefRefreshTokenService;

    public String userRegister(UserRequestDto request) {
        if (chefRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("This Email Is Used");
        }
        var user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return "Register Successfully";
    }
    public String chefRegister(ChefRequestDto chefRequestDto){
        if (userRepository.findByEmail(chefRequestDto.getEmail()).isPresent()){
            throw new RuntimeException("This Email Is Used");
        }
        Chef chef = Chef.builder()
                .chefName(chefRequestDto.getChefName())
                .email(chefRequestDto.getEmail())
                .password(passwordEncoder.encode(chefRequestDto.getPassword()))
                .phoneNumber(chefRequestDto.getPhoneNumber())
                .role(Role.CHEF)
                .build();
        chefRepository.save(chef);
        return "Register Successfully";
    }

    public String authenticateSendCode(AuthenticationRequestDto request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        Optional<Chef> chef = chefRepository.findByEmail(request.getEmail());
        if (user.isPresent() || chef.isPresent()){
        String code = String.valueOf(new Random().nextInt(900000)+100000);
        verificationCodeStore.saveCode(request.getEmail(),code);
        emailService.sendVerificationCode(request.getEmail(),code);
        return "Verification Code Send";
        }else {
            throw new EmailNotFound("Not Found");
        }
    }

    public AuthenticationResponseDto verifyCode(AuthCodeVerficationDto authCodeVerfication) {
        if (verificationCodeStore.isCodeValid(authCodeVerfication.getEmail(), authCodeVerfication.getCode())) {
            verificationCodeStore.removeCode(authCodeVerfication.getEmail());
            Optional<User> user = userRepository.findByEmail(authCodeVerfication.getEmail());
            Optional<Chef> chef = chefRepository.findByEmail(authCodeVerfication.getEmail());
            if (user.isPresent()){
                userRepository.save(user.get());
                var jwtToken = jwtService.generatedToken(user.get());
                String refreshToken = refreshTokenService.createRefreshToken(user.get()).getToken();
                return AuthenticationResponseDto.builder().token(jwtToken).refreshToken(refreshToken).build();
            }else if(chef.isPresent()){
                var jwtToken = jwtService.generatedToken(chef.get());
                String refreshToken = chefRefreshTokenService.createRefreshToken(chef.get()).getToken();
                return AuthenticationResponseDto.builder().token(jwtToken).refreshToken(refreshToken).build();
            }else {
                throw new VerifyCodeFailedException("Email or Code is wrong");
            }
        } else {
            throw new CodeExpireException("Code expire try to send code again");
        }
    }
}

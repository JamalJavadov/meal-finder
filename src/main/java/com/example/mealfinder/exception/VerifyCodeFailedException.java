package com.example.mealfinder.exception;

public class VerifyCodeFailedException extends RuntimeException{
    public VerifyCodeFailedException(String message){
        super(message);
    }
}

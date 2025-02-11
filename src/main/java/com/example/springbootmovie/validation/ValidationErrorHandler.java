package com.example.springbootmovie.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class ValidationErrorHandler {
    public String handleValidationErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessages.append(error.getDefaultMessage()).append("\n"));
            return errorMessages.toString();
        }
        return null;
    }
}

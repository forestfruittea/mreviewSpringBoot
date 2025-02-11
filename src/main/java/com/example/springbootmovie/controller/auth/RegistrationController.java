package com.example.springbootmovie.controller.auth;

import com.example.springbootmovie.exception.ResourceAlreadyExistsException;
import com.example.springbootmovie.model.dto.UserDto;
import com.example.springbootmovie.model.role.Role;
import com.example.springbootmovie.service.UserService;
import com.example.springbootmovie.validation.ValidationErrorHandler;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
@Slf4j
public class RegistrationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ValidationErrorHandler validationErrorHandler;
    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder, ValidationErrorHandler validationErrorHandler) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.validationErrorHandler = validationErrorHandler;
    }

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {

        String errorMessages = validationErrorHandler.handleValidationErrors(bindingResult);
        if (errorMessages != null) {
            return ResponseEntity.badRequest().body("Validation failed: \n" + errorMessages);
        }

        userService.findByUsername(userDto.getUsername())
                .ifPresent(user -> {
                    throw new ResourceAlreadyExistsException("Username " + user.getUsername() + " already exists");
                });

        String hashedPassword = passwordEncoder.encode(userDto.getPassword());

        userDto.setPassword(hashedPassword);
        userDto.setRole(Role.ROLE_CUSTOMER);

        userService.registerUser(userDto);
        log.info("User registered successfully: {}", userDto.getUsername());

        return ResponseEntity.status(201).body(userDto);
    }
}

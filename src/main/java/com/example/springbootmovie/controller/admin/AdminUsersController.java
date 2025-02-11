package com.example.springbootmovie.controller.admin;

import com.example.springbootmovie.exception.ResourceAlreadyExistsException;
import com.example.springbootmovie.exception.ResourceNotFoundException;
import com.example.springbootmovie.model.dto.UserDto;
import com.example.springbootmovie.service.UserService;
import com.example.springbootmovie.validation.ValidationErrorHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/tool/users")
@Slf4j
@Tag(name = "Admin - User Management", description = "Admin operations for managing users, including registration and deletion.")
public class AdminUsersController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ValidationErrorHandler validationErrorHandler;

    @Autowired
    public AdminUsersController(UserService userService, PasswordEncoder passwordEncoder, ValidationErrorHandler validationErrorHandler) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.validationErrorHandler = validationErrorHandler;
    }

    @Operation(
            summary = "Register a new user",
            description = "Register a new user with a unique username and hashed password. If the username already exists, an error will be thrown."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User successfully registered"),
            @ApiResponse(responseCode = "400", description = "Username already exists")
    })
    @PostMapping("/create")
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

        userService.registerUser(userDto);
        log.info("User registered successfully: {}", userDto.getUsername());

        return ResponseEntity.status(201).body(userDto);
    }

    @Operation(
            summary = "Delete a user",
            description = "Delete a user by their ID. If the user does not exist, a ResourceNotFoundException will be thrown."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID"),
            @ApiResponse(responseCode = "500", description = "Error occurred while deleting the user")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam("userId") Long userId) {
        try {
            userService.delete(userId);
            List<UserDto> users = userService.findAllSorted();
            log.info("User with ID '{}' deleted successfully.", userId);
            return ResponseEntity.ok(users);
        } catch (ResourceNotFoundException e) {
            log.error("User not found with ID: {}", userId);
            return ResponseEntity.badRequest().body("Invalid user ID.");
        } catch (Exception e) {
            log.error("Error occurred while deleting user with ID: {}", userId, e);
            return ResponseEntity.status(500).body("An error occurred while deleting the user.");
        }
    }
}

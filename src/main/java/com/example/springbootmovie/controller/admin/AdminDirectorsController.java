package com.example.springbootmovie.controller.admin;

import com.example.springbootmovie.model.dto.DirectorDto;
import com.example.springbootmovie.service.DirectorService;
import com.example.springbootmovie.service.ImageService;
import com.example.springbootmovie.validation.ValidationErrorHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@Tag(name = "Admin - Director Management", description = "CRUD operations for directors by admin")
@RestController
@RequestMapping("/api/admin/tool/directors")
public class AdminDirectorsController {

    private final DirectorService directorService;
    private final ImageService imageService;
    private final ValidationErrorHandler validationErrorHandler;

    public AdminDirectorsController(DirectorService directorService, ImageService imageService, ValidationErrorHandler validationErrorHandler) {
        this.directorService = directorService;
        this.imageService = imageService;
        this.validationErrorHandler = validationErrorHandler;
    }

    @Operation(
            summary = "Create Director",
            description = "Create a new director with a photo upload."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Director created successfully"),
            @ApiResponse(responseCode = "400", description = "Error uploading photo or validation errors")
    })
    @PostMapping("/create")
    public ResponseEntity<String> createDirector(
            @Valid @RequestBody DirectorDto directorDto,
            BindingResult bindingResult,
            @RequestParam("image") @Parameter(description = "Director's photo file") MultipartFile photoFile) {

        String errorMessages = validationErrorHandler.handleValidationErrors(bindingResult);
        if (errorMessages != null) {
            return ResponseEntity.badRequest().body("Validation failed: \n" + errorMessages);
        }

        try {
            String photoPath = imageService.saveFile(photoFile, "static/uploads");
            directorDto.setPhotoPath(photoPath);
            directorService.save(directorDto);

            return ResponseEntity.ok("Director created successfully!");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading photo: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Delete Director",
            description = "Delete an existing director by their ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Director deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Error deleting the director")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteDirector(@RequestParam Long directorId) {
        try {
            directorService.delete(directorId);
            return ResponseEntity.status(HttpStatus.OK).body("Director deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting the director: " + e.getMessage());
        }
    }
}

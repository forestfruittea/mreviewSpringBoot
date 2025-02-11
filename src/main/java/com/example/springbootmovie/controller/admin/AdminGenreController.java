package com.example.springbootmovie.controller.admin;

import com.example.springbootmovie.model.dto.GenreDto;
import com.example.springbootmovie.service.GenreService;
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

@Tag(name = "Admin - Genre Management", description = "CRUD operations for genres by admin")
@RestController
@RequestMapping("/api/admin/tool/genres")
public class AdminGenreController {

    private final GenreService genreService;
    private final ImageService imageService;
    private final ValidationErrorHandler validationErrorHandler;

    public AdminGenreController(GenreService genreService, ImageService imageService, ValidationErrorHandler validationErrorHandler) {
        this.genreService = genreService;
        this.imageService = imageService;
        this.validationErrorHandler = validationErrorHandler;
    }

    @Operation(
            summary = "Create Genre",
            description = "Create a new genre with an image upload."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Genre created successfully"),
            @ApiResponse(responseCode = "400", description = "Error uploading image or validation errors")
    })
    @PostMapping("/create")
    public ResponseEntity<String> createGenre(
            @Valid @RequestBody GenreDto genreDto,  // Use @Valid here to trigger validation
            BindingResult bindingResult,  // Capture validation errors
            @RequestParam("image") @Parameter(description = "Genre image file") MultipartFile imageFile) {

        String errorMessages = validationErrorHandler.handleValidationErrors(bindingResult);
        if (errorMessages != null) {
            return ResponseEntity.badRequest().body("Validation failed: \n" + errorMessages);
        }

        try {
            // Handle image upload
            String imagePath = imageService.saveFile(imageFile, "static/uploads");

            // Update DTO with image path
            genreDto.setImagePath(imagePath);

            // Save genre
            genreService.save(genreDto);

            return ResponseEntity.ok("Genre created successfully!");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading image: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Delete Genre",
            description = "Delete an existing genre by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Genre deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Error deleting the genre")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteGenre(@RequestParam Long genreId) {
        try {
            genreService.delete(genreId);
            return ResponseEntity.status(HttpStatus.OK).body("Genre deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting the genre: " + e.getMessage());
        }
    }
}

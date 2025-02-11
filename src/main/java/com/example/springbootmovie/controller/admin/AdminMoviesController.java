package com.example.springbootmovie.controller.admin;

import com.example.springbootmovie.model.dto.*;
import com.example.springbootmovie.service.*;
import com.example.springbootmovie.validation.ValidationErrorHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Admin - Movie Management", description = "CRUD operations for movies by admin")
@RestController
@RequestMapping("/api/admin/tool/movies")
public class AdminMoviesController {

    private final MovieService movieService;
    private final ImageService imageService;
    private final ValidationErrorHandler validationErrorHandler;

    @Autowired
    public AdminMoviesController(MovieService movieService, ImageService imageService, ValidationErrorHandler validationErrorHandler) {
        this.movieService = movieService;
        this.imageService = imageService;
        this.validationErrorHandler = validationErrorHandler;
    }

    @Operation(
            summary = "Create Movie",
            description = "Create a new movie with associated actors, genres, director, and poster image upload."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movie created successfully"),
            @ApiResponse(responseCode = "400", description = "Error uploading the poster or validation failed"),
            @ApiResponse(responseCode = "500", description = "Error creating the movie")
    })
    @PostMapping("/create")
    public ResponseEntity<String> createMovie(
            @Valid @RequestBody MovieDto movieDto,
            BindingResult bindingResult,
            @RequestParam("image") @Parameter(description = "Movie poster image file") MultipartFile imageFile) {

        String errorMessages = validationErrorHandler.handleValidationErrors(bindingResult);
        if (errorMessages != null) {
            return ResponseEntity.badRequest().body("Validation failed: \n" + errorMessages);
        }

        try {
            String imagePath = imageService.saveFile(imageFile, "static/uploads");
            movieDto.setPosterPath(imagePath);
            movieService.save(movieDto);

            return ResponseEntity.ok("Movie created successfully!");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading the poster: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating the movie: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Delete Movie",
            description = "Delete an existing movie by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movie deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Error deleting the movie")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMovie(@RequestParam Long id) {
        try {
            movieService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Movie deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting the movie: " + e.getMessage());
        }
    }
}

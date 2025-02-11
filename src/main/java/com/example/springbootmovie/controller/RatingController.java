package com.example.springbootmovie.controller;

import com.example.springbootmovie.exception.ResourceNotFoundException;
import com.example.springbootmovie.model.dto.MovieDto;
import com.example.springbootmovie.model.dto.RatingDto;
import com.example.springbootmovie.model.dto.UserDto;
import com.example.springbootmovie.service.MovieService;
import com.example.springbootmovie.service.RatingService;
import com.example.springbootmovie.service.UserService;
import com.example.springbootmovie.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Ratings", description = "Endpoints for managing movie ratings")
@RestController
@RequestMapping("/api/ratings")
@Slf4j
public class RatingController {

    private final RatingService ratingService;
    private final UserService userService;
    private final MovieService movieService;

    public RatingController(RatingService ratingService, UserService userService, MovieService movieService) {
        this.ratingService = ratingService;
        this.userService = userService;
        this.movieService = movieService;
    }

    @Operation(
            summary = "Rate a movie",
            description = "Allows an authenticated user to rate a movie. If a rating already exists, it updates the existing rating."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Rating added successfully"),
            @ApiResponse(responseCode = "200", description = "Rating updated successfully"),
            @ApiResponse(responseCode = "404", description = "User or Movie not found")
    })
    @PostMapping("/rate/{movieId}")
    public ResponseEntity<?> rateMovie(
            @Parameter(description = "ID of the movie to be rated", example = "5")
            @PathVariable Long movieId,

            @Parameter(description = "Rating value (1-10)", example = "8")
            @RequestParam Integer rating) {

        Long userId = SecurityUtil.getLoggedInUserId();

        UserDto user = userService.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));
        MovieDto movie = movieService.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie with ID " + movieId + " not found"));

        Optional<RatingDto> existingRating = ratingService.findByUserAndMovie(userId, movieId);
        RatingDto ratingDto = existingRating.orElseGet(RatingDto::new);

        ratingDto.setRating(rating);
        ratingDto.setUser(user);
        ratingDto.setMovie(movie);

        if (existingRating.isPresent()) {
            ratingService.update(ratingDto);
            log.info("Rating updated successfully for user {} and movie {}", userId, movieId);
            return ResponseEntity.ok("Rating updated successfully");
        } else {
            ratingService.save(ratingDto);
            log.info("Rating added successfully for user {} and movie {}", userId, movieId);
            return ResponseEntity.status(201).body("Rating added successfully");
        }
    }
}

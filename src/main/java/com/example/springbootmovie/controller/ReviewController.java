package com.example.springbootmovie.controller;

import com.example.springbootmovie.exception.ResourceNotFoundException;
import com.example.springbootmovie.model.dto.MovieDto;
import com.example.springbootmovie.model.dto.ReviewDto;
import com.example.springbootmovie.model.dto.UserDto;
import com.example.springbootmovie.service.MovieService;
import com.example.springbootmovie.service.ReviewService;
import com.example.springbootmovie.service.UserService;
import com.example.springbootmovie.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Reviews", description = "Endpoints for managing movie reviews")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;
    private final MovieService movieService;

    public ReviewController(ReviewService reviewService, UserService userService, MovieService movieService) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.movieService = movieService;
    }

    @Operation(
            summary = "Add a new review",
            description = "Creates a new review for a specified movie by the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Review added successfully"),
            @ApiResponse(responseCode = "404", description = "User or Movie not found")
    })
    @PostMapping("/add/{movieId}")
    public ResponseEntity<?> addReview(
            @Parameter(description = "ID of the movie to review", example = "10")
            @PathVariable Long movieId,

            @Parameter(description = "Content of the review", example = "Amazing movie, must watch!")
            @RequestParam String content) {

        Long userId = SecurityUtil.getLoggedInUserId();

        UserDto user = userService.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));

        MovieDto movie = movieService.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie with ID " + movieId + " not found"));

        ReviewDto review = new ReviewDto();
        review.setContent(content);
        review.setUser(user);
        review.setMovie(movie);
        reviewService.save(review);

        return ResponseEntity.status(201).body(review);
    }
}

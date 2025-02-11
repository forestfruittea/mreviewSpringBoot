package com.example.springbootmovie.controller;

import com.example.springbootmovie.exception.ResourceNotFoundException;
import com.example.springbootmovie.model.dto.MovieDto;
import com.example.springbootmovie.model.dto.RatingDto;
import com.example.springbootmovie.model.dto.ReviewDto;
import com.example.springbootmovie.model.dto.UserDto;
import com.example.springbootmovie.service.RatingService;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "Users", description = "Endpoints for managing users, their ratings, and reviews")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RatingService ratingService;
    private final ReviewService reviewService;

    public UserController(UserService userService, RatingService ratingService, ReviewService reviewService) {
        this.userService = userService;
        this.ratingService = ratingService;
        this.reviewService = reviewService;
    }

    @Operation(
            summary = "Get user details",
            description = "Fetches user information along with their rated movies and individual movie ratings."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserDetails(
            @Parameter(description = "ID of the user", example = "1")
            @PathVariable Long userId) {
        UserDto user = userService.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));

        List<RatingDto> ratings = ratingService.findByUser(userId);
        Map<Long, RatingDto> movieRatings = ratings.stream()
                .collect(Collectors.toMap(rating -> rating.getMovie().getId(), rating -> rating));

        List<MovieDto> ratedMovies = ratings.stream()
                .map(RatingDto::getMovie)
                .toList();

        Map<String, Object> response = Map.of(
                "user", user,
                "ratedMovies", ratedMovies,
                "movieRatings", movieRatings
        );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get logged-in user details",
            description = "Retrieves details of the currently authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/account")
    public ResponseEntity<?> getAccountDetails() {
        Long userId = SecurityUtil.getLoggedInUserId();
        UserDto user = userService.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID "+ userId + " not found"));

        return ResponseEntity.ok(user);
    }

    @Operation(
            summary = "Get logged-in user reviews",
            description = "Fetches all reviews made by the currently authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User reviews retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No reviews found for the user")
    })
    @GetMapping("/account/reviews")
    public ResponseEntity<?> getAccountReviews() {
        Long userId = SecurityUtil.getLoggedInUserId();
        List<ReviewDto> reviews = reviewService.findAllForUser(userId);

        if (reviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reviews);
    }
}

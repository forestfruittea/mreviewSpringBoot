package com.example.springbootmovie.controller;

import com.example.springbootmovie.exception.ResourceNotFoundException;
import com.example.springbootmovie.model.dto.MovieDto;
import com.example.springbootmovie.model.dto.RatingDto;
import com.example.springbootmovie.model.dto.ReviewDto;
import com.example.springbootmovie.service.MovieService;
import com.example.springbootmovie.service.RatingService;
import com.example.springbootmovie.service.ReviewService;
import com.example.springbootmovie.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Movies", description = "Endpoints for managing movies and retrieving details")
@RestController
@RequestMapping("/api")
@Slf4j
public class MovieController {
    private final MovieService movieService;
    private final ReviewService reviewService;
    private final RatingService ratingService;

    public MovieController(MovieService movieService, ReviewService reviewService, RatingService ratingService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
        this.ratingService = ratingService;
    }

    @Operation(
            summary = "Retrieve a list of movies",
            description = "Fetches all movies along with their average ratings."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of movies")
    })
    @GetMapping("/movies")
    public ResponseEntity<List<MovieDto>> listMovies() {
        List<MovieDto> movies = movieService.findAll();

        movies.forEach(movie -> {
            Double averageRating = ratingService.calculateAverageRating(movie.getId());
            movie.setAverageRating(averageRating);
        });

        log.info("Fetched list of movies with average ratings");
        return ResponseEntity.ok(movies);
    }

    @Operation(
            summary = "Get details of a specific movie",
            description = "Fetches a movie's details including reviews, average rating, and the user's rating (if logged in)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved movie details"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    @GetMapping("/movie/{id}")
    public ResponseEntity<?> movieDetails(
            @Parameter(description = "ID of the movie to retrieve", example = "5")
            @PathVariable(name = "id") Long id) {

        MovieDto movie = movieService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie with ID " + id + " not found"));

        List<ReviewDto> reviews = reviewService.findAllForMovie(id);
        Double averageRating = ratingService.calculateAverageRating(id);
        Long userId = SecurityUtil.getLoggedInUserId();
        Optional<RatingDto> userRating = ratingService.findByUserAndMovie(userId, id);

        movie.setReviews(reviews);
        movie.setAverageRating(averageRating);
        movie.setUserRating(userRating.orElse(null));

        log.info("Fetched details for movie with ID: {}", id);
        return ResponseEntity.ok(movie);
    }
}

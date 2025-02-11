package com.example.springbootmovie.controller;

import com.example.springbootmovie.exception.ResourceNotFoundException;
import com.example.springbootmovie.model.dto.GenreDto;
import com.example.springbootmovie.model.dto.MovieDto;
import com.example.springbootmovie.service.GenreService;
import com.example.springbootmovie.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Genres", description = "Endpoints for managing movie genres")
@RestController
@RequestMapping("/api")
@Slf4j
public class GenreController {
    private final GenreService genreService;
    private final MovieService movieService;

    public GenreController(GenreService genreService, MovieService movieService) {
        this.genreService = genreService;
        this.movieService = movieService;
    }

    @Operation(
            summary = "Retrieve a list of genres",
            description = "Fetches all available movie genres."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of genres")
    })
    @GetMapping("/genres")
    public ResponseEntity<List<GenreDto>> listGenres() {
        List<GenreDto> genres = genreService.findAll();
        log.info("Fetched list of genres");
        return ResponseEntity.ok(genres);
    }

    @Operation(
            summary = "Get details of a specific genre",
            description = "Fetches a genre's details along with movies that belong to this genre."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved genre details"),
            @ApiResponse(responseCode = "404", description = "Genre not found")
    })
    @GetMapping("/genre/{id}")
    public ResponseEntity<?> genreDetails(
            @Parameter(description = "ID of the genre to retrieve", example = "3")
            @PathVariable(name = "id") Long id) {

        GenreDto genre = genreService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre with ID " + id + " not found"));

        List<MovieDto> movies = movieService.findAllByGenreId(id);
        genre.setMovies(movies);

        log.info("Fetched details for genre with ID: {}", id);
        return ResponseEntity.ok(genre);
    }
}

package com.example.springbootmovie.controller;

import com.example.springbootmovie.exception.ResourceNotFoundException;
import com.example.springbootmovie.model.dto.DirectorDto;
import com.example.springbootmovie.service.DirectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Directors", description = "Endpoints for managing movie directors")
@RestController
@RequestMapping("/api")
@Slf4j
public class DirectorController {
    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @Operation(
            summary = "Retrieve a list of all directors",
            description = "Fetches all available directors in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of directors")
    })
    @GetMapping("/directors")
    public ResponseEntity<List<DirectorDto>> listDirectors() {
        List<DirectorDto> directors = directorService.findAll();
        log.info("Fetched list of all directors");
        return ResponseEntity.ok(directors);
    }

    @Operation(
            summary = "Get details of a specific director",
            description = "Fetches details of a director using their ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved director details"),
            @ApiResponse(responseCode = "404", description = "Director not found")
    })
    @GetMapping("/director/{id}")
    public ResponseEntity<?> showDirectorDetails(
            @Parameter(description = "ID of the director to retrieve", example = "1")
            @PathVariable(name = "id") Long id) {

        DirectorDto director = directorService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Director with ID " + id + " not found"));

        log.info("Fetched details for director with ID: {}", id);
        return ResponseEntity.ok(director);
    }
}

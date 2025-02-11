package com.example.springbootmovie.controller;

import com.example.springbootmovie.exception.ResourceNotFoundException;
import com.example.springbootmovie.model.dto.ActorDto;
import com.example.springbootmovie.service.ActorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Actors", description = "Endpoints for managing actors")
@RestController
@RequestMapping("/api/actors")
@RequiredArgsConstructor
@Slf4j
public class ActorController {

    private final ActorService actorService;

    @Operation(
            summary = "Retrieve all actors",
            description = "Fetches a list of all available actors."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of actors")
    })
    @GetMapping
    public ResponseEntity<List<ActorDto>> listActors() {
        List<ActorDto> actors = actorService.findAll();
        log.info("Fetched list of all actors");
        return ResponseEntity.ok(actors);
    }

    @Operation(
            summary = "Get details of a specific actor",
            description = "Fetches details of an actor using their ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved actor details"),
            @ApiResponse(responseCode = "404", description = "Actor not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ActorDto> getActorDetails(
            @Parameter(description = "ID of the actor to retrieve", example = "1")
            @PathVariable Long id) {

        ActorDto actor = actorService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Actor with ID " + id + " not found"));

        log.info("Fetched details for actor with ID: {}", id);
        return ResponseEntity.ok(actor);
    }
}

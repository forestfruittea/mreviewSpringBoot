package com.example.springbootmovie.controller.admin;

import com.example.springbootmovie.model.dto.ActorDto;
import com.example.springbootmovie.service.ActorService;
import com.example.springbootmovie.service.ImageService;
import com.example.springbootmovie.validation.ValidationErrorHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "Admin - Actor Management", description = "CRUD operations for actors by admin")
@RestController
@RequestMapping("/api/admin/tool/actors")
public class AdminActorsController {
    private final ActorService actorService;
    private final ImageService imageService;
    private final ValidationErrorHandler validationErrorHandler;

    @Autowired
    public AdminActorsController(ActorService actorService, ImageService imageService, ValidationErrorHandler validationErrorHandler) {
        this.actorService = actorService;
        this.imageService = imageService;
        this.validationErrorHandler = validationErrorHandler;
    }

    @Operation(
            summary = "Create Actor",
            description = "Create a new actor with photo upload."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actor created successfully"),
            @ApiResponse(responseCode = "400", description = "Error uploading photo")
    })
    @PostMapping("/create")
    public ResponseEntity<String> createActor(
            @Valid @RequestBody ActorDto actorDto,
            BindingResult bindingResult,
            @RequestParam("photo") @Parameter(description = "Actor's photo file") MultipartFile photoFile) {

        String errorMessages = validationErrorHandler.handleValidationErrors(bindingResult);
        if (errorMessages != null) {
            return ResponseEntity.badRequest().body("Validation failed: \n" + errorMessages);
        }

        try {
            String photoPath = imageService.saveFile(photoFile, "static/uploads");
            // Create ActorDto
            actorDto.setPhotoPath(photoPath);
            actorService.save(actorDto);
            return ResponseEntity.ok("Actor created successfully!");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading photo: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Delete Actor",
            description = "Delete an actor by their ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actor deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Failed to delete actor")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteActor(@RequestParam("actorId") Long actorId) {
        try {
            actorService.delete(actorId);
            List<ActorDto> actors = actorService.findAllSorted();
            return ResponseEntity.ok(actors);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete actor: " + e.getMessage());
        }
    }
}

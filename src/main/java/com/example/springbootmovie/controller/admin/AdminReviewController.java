package com.example.springbootmovie.controller.admin;

import com.example.springbootmovie.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/admin/tool/reviews")
@Tag(name = "Admin - Review Management", description = "Admin operations for managing reviews, including deletion.")
public class AdminReviewController {

    private final ReviewService reviewService;

    @Autowired
    public AdminReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(
            summary = "Delete Review",
            description = "Delete a review by its ID. If the review doesn't exist, a ResourceNotFoundException will be thrown."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Review not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected error occurred while deleting the review")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "ID of the review to be deleted") Long id) {
        try {
            reviewService.delete(id);
            log.info("Review with ID {} deleted successfully.", id);
            return ResponseEntity.status(HttpStatus.OK).body("Review deleted successfully!");
        } catch (Exception e) {
            log.error("Error occurred while deleting review with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("An unexpected error occurred while deleting the review.");
        }
    }
}

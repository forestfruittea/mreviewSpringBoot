package com.example.springbootmovie.model.dto;

import com.example.springbootmovie.model.entity.RatingEntity;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingDto {

    private Long id;
    private UserDto user;
    private MovieDto movie;
    @Positive(message = "Rating must be a positive number")
    private int rating;
    public static RatingDto of(RatingEntity rating) {
        return RatingDto.builder()
                .id(rating.getId())
                .user(UserDto.of(rating.getUser()))
                .movie(MovieDto.ofReview(rating.getMovie()))
                // Map only movies without their actors to avoid lazy initialization issues
                .rating(rating.getRating())
                .build();
    }
}

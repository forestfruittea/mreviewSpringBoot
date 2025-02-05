package com.example.springbootmovie.model.dto;

import com.example.springbootmovie.model.entity.ReviewEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private UserDto user;
    private MovieDto movie;
    public String getFormattedCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return createdAt.format(formatter);
    }
    public static ReviewDto of(ReviewEntity review) {
        return ReviewDto.builder()
                .id(review.getId())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .user(UserDto.of(review.getUser()))
                .movie(MovieDto.ofReview(review.getMovie()))
                .build();
    }
    public static ReviewDto ofUser(ReviewEntity review) {
        return ReviewDto.builder()
                .id(review.getId())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .build();
    }
}

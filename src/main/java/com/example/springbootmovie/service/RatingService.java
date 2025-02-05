package com.example.springbootmovie.service;
import java.util.List;
import java.util.Optional;
import com.example.springbootmovie.model.dto.RatingDto;

public interface RatingService {
    double calculateAverageRating(Long movieId);
    RatingDto save(RatingDto ratingDto);
    Optional<RatingDto> findByUserAndMovie(Long userId, Long movieId);
    List<RatingDto> findByUser(Long userId);
    RatingDto update(RatingDto ratingDto);
}

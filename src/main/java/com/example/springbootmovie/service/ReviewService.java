package com.example.springbootmovie.service;
import com.example.springbootmovie.model.dto.ReviewDto;
import java.util.List;

public interface ReviewService {
    void save(ReviewDto reviewDto);
    List<ReviewDto> findAllForMovie(Long movieId);
    List<ReviewDto> findAllForUser(Long userId);
    void delete(Long reviewId);
    //TODO ReviewDto update(ReviewDto reviewDto);
}

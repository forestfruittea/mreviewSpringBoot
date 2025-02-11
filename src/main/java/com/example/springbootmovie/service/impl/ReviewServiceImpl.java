package com.example.springbootmovie.service.impl;

import com.example.springbootmovie.exception.ResourceNotFoundException;
import com.example.springbootmovie.model.dto.ReviewDto;
import com.example.springbootmovie.model.dto.UserDto;
import com.example.springbootmovie.model.entity.MovieEntity;
import com.example.springbootmovie.model.entity.ReviewEntity;
import com.example.springbootmovie.model.entity.UserEntity;
import com.example.springbootmovie.mapper.impl.ReviewMapper;
import com.example.springbootmovie.mapper.impl.UserMapper;
import com.example.springbootmovie.repository.MovieRepository;
import com.example.springbootmovie.repository.ReviewRepository;
import com.example.springbootmovie.repository.UserRepository;
import com.example.springbootmovie.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    @Autowired
    public ReviewServiceImpl(UserRepository userRepository, MovieRepository movieRepository, ReviewRepository reviewRepository, ReviewMapper reviewMapper, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }
    @Override
    @Transactional
    public void save(ReviewDto reviewDto) {
        Long userId = reviewDto.getUser().getId();
        Long movieId = reviewDto.getMovie().getId();
        MovieEntity movieEntity = movieRepository.findById(reviewDto.getMovie().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie with ID " + movieId + " not found"));
        UserEntity userEntity = userRepository.findById(reviewDto.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));

        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setUser(userEntity);
        reviewEntity.setCreatedAt(LocalDateTime.now());
        reviewEntity.setMovie(movieEntity);
        reviewEntity.setContent(reviewDto.getContent());

        log.debug("Saving review for movie: {}", movieEntity.getTitle());
        reviewRepository.save(reviewEntity);
        }
    @Override
    public void delete(Long reviewId) {
        reviewRepository.deleteById(reviewId);
        log.debug("Review with ID {} has been deleted", reviewId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> findAllForMovie(Long movieId) {
        List<ReviewEntity> reviewEntities = reviewRepository.findByMovieId(movieId);
        log.debug("Fetching all reviews for movie with ID: {}", movieId);

        return reviewEntities.stream()
                .map(ReviewDto::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> findAllForUser(Long userId) {
        List<ReviewEntity> reviewEntities = reviewRepository.findByUserId(userId);
        log.debug("Fetching all reviews for user with ID: {}", userId);

        return reviewEntities.stream()
                .map(ReviewDto::of)
                .collect(Collectors.toList());
    }
}

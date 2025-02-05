package com.example.springbootmovie.service.impl;

import com.example.springbootmovie.model.dto.RatingDto;
import com.example.springbootmovie.model.entity.RatingEntity;
import com.example.springbootmovie.mapper.impl.RatingMapper;
import com.example.springbootmovie.repository.RatingRepository;
import com.example.springbootmovie.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, RatingMapper ratingMapper) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
    }

    // Find rating by user and movie
    @Transactional(readOnly = true)
    @Override
    public Optional<RatingDto> findByUserAndMovie(Long userId, Long movieId) {
        Optional<RatingEntity> ratingEntity = ratingRepository.findByUserIdAndMovieId(userId, movieId);
        log.debug("finds rate by user id and movie id");
        return ratingEntity.map(RatingDto::of);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RatingDto> findByUser(Long userId) {
        List<RatingEntity> ratingEntities = ratingRepository.findByUserId(userId);
        log.debug("finds rate by user id");

        return ratingEntities.stream()
                .map(RatingDto::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RatingDto save(RatingDto ratingDto) {
        RatingEntity ratingEntity = ratingMapper.toEntity(ratingDto);
        ratingEntity = ratingRepository.save(ratingEntity);
        log.debug("saves rate");

        return ratingMapper.toDto(ratingEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public double calculateAverageRating(Long movieId) {
        List<RatingEntity> ratings = ratingRepository.findByMovieId(movieId);
        log.debug("calculates average rating for movie");

        if (ratings.isEmpty()) {
            return 0.0;
        }
        return ratings.stream().mapToDouble(RatingEntity::getRating).average().orElse(0.0);
    }

    @Override
    @Transactional
    public RatingDto update(RatingDto ratingDto) {
        RatingEntity ratingEntity = ratingMapper.toEntity(ratingDto);
        ratingEntity = ratingRepository.save(ratingEntity);
        log.debug("updates rate");
        return RatingDto.of(ratingEntity);
    }
}

package com.example.springbootmovie.repository;

import com.example.springbootmovie.model.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    // Derived query method to find ratings by movie ID
    List<RatingEntity> findByMovieId(Long movieId);

    // Derived query method to find ratings by user and movie ID
    Optional<RatingEntity> findByUserIdAndMovieId(Long userId, Long movieId);

    // Derived query method to find ratings by user ID
    List<RatingEntity> findByUserId(Long userId);
}

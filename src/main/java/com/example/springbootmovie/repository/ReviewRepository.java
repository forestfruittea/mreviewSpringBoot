package com.example.springbootmovie.repository;

import com.example.springbootmovie.model.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findByMovieId(Long movieId);
    List<ReviewEntity> findByUserId(Long userId);
    @Query("SELECT DISTINCT r FROM ReviewEntity r " +
            "LEFT JOIN FETCH r.user " +
            "LEFT JOIN FETCH r.movie")
    List<ReviewEntity> findAllReviewsWithUserAndMovie();
}

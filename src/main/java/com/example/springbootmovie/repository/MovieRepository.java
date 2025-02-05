package com.example.springbootmovie.repository;

import com.example.springbootmovie.model.entity.MovieEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    // Find movies by title containing a specific name
    List<MovieEntity> findByTitleContainingIgnoreCase(String name);

    // Find movies by genre ID
    @Query("SELECT DISTINCT m FROM MovieEntity m " +
            "JOIN m.genres g WHERE g.id = :genreId")
    List<MovieEntity> findAllByGenreId(Long genreId);

//    // Find movies with pagination using custom query
//    @Query("SELECT DISTINCT m FROM MovieEntity m " +
//            "LEFT JOIN FETCH m.actors " +
//            "LEFT JOIN FETCH m.genres " +
//            "LEFT JOIN FETCH m.director " +
//            "ORDER BY m.title")
//    List<MovieEntity> findMoviesByPage();

    // Find a movie by ID with eager fetching
    @Query("SELECT m FROM MovieEntity m " +
            "LEFT JOIN FETCH m.director " +
            "WHERE m.id = :movieId")
    Optional<MovieEntity> findByIdWithDetails(Long movieId);
    @EntityGraph(attributePaths = {"genres"})
    List<MovieEntity> findAll();
}
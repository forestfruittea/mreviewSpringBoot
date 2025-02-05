package com.example.springbootmovie.repository;

import com.example.springbootmovie.model.entity.GenreEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    @Query("SELECT g FROM GenreEntity g ORDER BY g.name")
    List<GenreEntity> findAllSortedByName();
    @Query("SELECT DISTINCT g FROM GenreEntity g JOIN g.movies m WHERE m.id = :movieId")
    List<GenreEntity> findAllByMovieId(Long movieId);
    Optional<GenreEntity> findByName(String name);
    @EntityGraph(value = "Genre.movies", type = EntityGraph.EntityGraphType.LOAD)
    List<GenreEntity> findAll();
    @EntityGraph(value = "Genre.movies", type = EntityGraph.EntityGraphType.LOAD)
    Optional<GenreEntity> findById(Long id);
    List<GenreEntity> findByNameIn(List<String> names);


}
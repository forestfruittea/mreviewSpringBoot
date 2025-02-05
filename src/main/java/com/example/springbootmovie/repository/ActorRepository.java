package com.example.springbootmovie.repository;

import com.example.springbootmovie.model.entity.ActorEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ActorRepository extends JpaRepository<ActorEntity, Long> {
            @Query("SELECT a FROM ActorEntity a ORDER BY a.name")
            List<ActorEntity> findAllSortedByName();
            Optional<ActorEntity> findByName(String name);
            @Override
            @EntityGraph(value = "Actor.movies", type = EntityGraph.EntityGraphType.LOAD)
            List<ActorEntity> findAll();
            @EntityGraph(value = "Actor.movies", type = EntityGraph.EntityGraphType.LOAD)
            Optional<ActorEntity> findById(Long id);
            List<ActorEntity> findByNameIn(List<String> names);



}
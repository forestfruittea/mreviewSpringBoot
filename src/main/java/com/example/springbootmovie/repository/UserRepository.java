package com.example.springbootmovie.repository;

import com.example.springbootmovie.model.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    // Custom query for existsByUsername
    @Query("SELECT COUNT(u) > 0 FROM UserEntity u WHERE u.username = :username")
    boolean existsByUsername(String username);
    @Override
    @EntityGraph(value = "User.reviews", type = EntityGraph.EntityGraphType.LOAD)
    Optional<UserEntity> findById(Long id);

}

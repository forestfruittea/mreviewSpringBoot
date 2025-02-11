package com.example.springbootmovie.repository;

import com.example.springbootmovie.model.entity.RefreshToken;
import com.example.springbootmovie.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(UserEntity user);
}

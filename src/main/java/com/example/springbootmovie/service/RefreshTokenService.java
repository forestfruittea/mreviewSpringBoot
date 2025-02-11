package com.example.springbootmovie.service;

import com.example.springbootmovie.exception.ResourceNotFoundException;
import com.example.springbootmovie.model.dto.UserDto;
import com.example.springbootmovie.model.entity.RefreshToken;
import com.example.springbootmovie.model.entity.UserEntity;
import com.example.springbootmovie.repository.RefreshTokenRepository;
import com.example.springbootmovie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {

    boolean isValid(RefreshToken refreshToken);
    Optional<RefreshToken> findByToken(String token);
    RefreshToken createRefreshToken(String username);
    void deleteByUser(UserDto user);
}

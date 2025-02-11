package com.example.springbootmovie.service.impl;

import com.example.springbootmovie.exception.ResourceNotFoundException;
import com.example.springbootmovie.mapper.impl.UserMapper;
import com.example.springbootmovie.model.dto.UserDto;
import com.example.springbootmovie.model.entity.RefreshToken;
import com.example.springbootmovie.model.entity.UserEntity;
import com.example.springbootmovie.repository.RefreshTokenRepository;
import com.example.springbootmovie.repository.UserRepository;
import com.example.springbootmovie.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Value("${jwt.refresh.expiration}")
    private Duration refreshTokenExpiration;
    @Override
    public RefreshToken createRefreshToken(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenExpiration.toMillis()))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }
    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
    @Override
    public boolean isValid(RefreshToken refreshToken) {
        return refreshToken.getExpiryDate().isAfter(Instant.now());
    }
    @Override
    public void deleteByUser(UserDto user) {
        refreshTokenRepository.deleteByUser(userMapper.toEntity(user));
    }
}

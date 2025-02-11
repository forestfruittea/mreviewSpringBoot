package com.example.springbootmovie.controller.auth;

import com.example.springbootmovie.exception.ResourceNotFoundException;
import com.example.springbootmovie.model.dto.AuthRequest;
import com.example.springbootmovie.model.dto.JwtResponse;
import com.example.springbootmovie.model.dto.RefreshTokenRequest;
import com.example.springbootmovie.model.dto.UserDto;
import com.example.springbootmovie.model.entity.RefreshToken;
import com.example.springbootmovie.service.RefreshTokenService;
import com.example.springbootmovie.service.UserService;
import com.example.springbootmovie.service.impl.CustomUserDetailsServiceImpl;
import com.example.springbootmovie.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Authentication", description = "Endpoints for user authentication")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsServiceImpl userDetailsService;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @Operation(
            summary = "User Login",
            description = "Authenticates the user and returns a JWT and refresh token."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            UserDto user = userService.findByUsername(authRequest.getUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found: " + authRequest.getUsername()));
            String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());

            return ResponseEntity.ok(new JwtResponse("Bearer " + token, refreshToken.getToken()));
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @Operation(
            summary = "Refresh JWT Token",
            description = "Uses a valid refresh token to issue a new JWT."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "New access token issued"),
            @ApiResponse(responseCode = "403", description = "Invalid or expired refresh token")
    })
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        Optional<RefreshToken> refreshToken = refreshTokenService.findByToken(request.getRefreshToken());

        if (refreshToken.isPresent() && refreshTokenService.isValid(refreshToken.get())) {
            UserDto user = UserDto.of(refreshToken.get().getUser());
            String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

            return ResponseEntity.ok(JwtResponse.builder()
                    .accessToken("Bearer " + token)
                    .refreshToken(request.getRefreshToken())
                    .build());
        } else {
            return ResponseEntity.status(403).body("Invalid or expired refresh token");
        }
    }
}

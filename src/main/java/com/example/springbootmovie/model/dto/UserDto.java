package com.example.springbootmovie.model.dto;


import com.example.springbootmovie.model.entity.UserEntity;
import com.example.springbootmovie.model.role.Role;
import lombok.*;

import java.util.List;
import java.util.ResourceBundle;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String avatarPath;
    private Role role;
    private List<ReviewDto> reviews;

    //Uses in JSPs
    public String getFullAvatarPath() {
        String basePath = ResourceBundle.getBundle("application").getString("base.avatar.path");
        if (avatarPath != null && !avatarPath.isEmpty()) {
            return basePath + avatarPath;
        }
        return null;
    }
    public static UserDto of(UserEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .avatarPath(user.getAvatarPath())
                .role(user.getRole())
                // Map only movies without their actors to avoid lazy initialization issues
                .reviews(user.getReviews() != null
                        ? user.getReviews().stream()
                        .map(ReviewDto::ofUser)
                        .toList()
                        : null)
                .build();
    }
}

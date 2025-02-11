package com.example.springbootmovie.model.dto;


import com.example.springbootmovie.model.entity.UserEntity;
import com.example.springbootmovie.model.role.Role;
import com.example.springbootmovie.validation.ValidRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    private String avatarPath;
    @NotNull(message = "Role is required")
    @ValidRole
    private Role role;
    private List<ReviewDto> reviews;

    //Uses in JSPs
//    public String getFullAvatarPath() {
//        String basePath = ResourceBundle.getBundle("application").getString("base.avatar.path");
//        if (avatarPath != null && !avatarPath.isEmpty()) {
//            return basePath + avatarPath;
//        }
//        return null;
//    }
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

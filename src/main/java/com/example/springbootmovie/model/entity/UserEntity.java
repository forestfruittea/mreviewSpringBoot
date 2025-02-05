package com.example.springbootmovie.model.entity;

import com.example.springbootmovie.model.role.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
        name = "User.reviews",
        attributeNodes = @NamedAttributeNode("reviews")
)
@Builder
@Table(name = "users")

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    @ToString.Exclude
    private String password;
    @Column(name = "avatar_path")
    private String avatarPath;
    @Enumerated(EnumType.STRING)  // Ensure Role is stored as a string in the DB
    @Column(nullable = false)
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ReviewEntity> reviews;
}

package com.example.springbootmovie.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@NamedEntityGraph(
        name = "Actor.movies",
        attributeNodes = @NamedAttributeNode("movies")
)
@Table(name = "actors")
public class ActorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany(mappedBy = "actors", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<MovieEntity> movies;
    @Column(name = "bio", length = 1500)
    private String bio;
    @Column(name = "year_of_birth")
    private Long yearOfBirth;
    @Column(name = "photo_path")
    private String photoPath;
    @Column(name ="height")
    private Long height;
}

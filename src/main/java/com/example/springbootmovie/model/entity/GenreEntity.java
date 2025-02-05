package com.example.springbootmovie.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NamedEntityGraph(
        name = "Genre.movies",
        attributeNodes = @NamedAttributeNode("movies")
)
@Table(name = "genres")
public class GenreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;
    @ManyToMany(mappedBy = "genres")
    @ToString.Exclude
    private List<MovieEntity> movies;
    @Column(name = "image_path")
    private String imagePath;
    @Column(name = "description", length = 500)
    private String description;
}


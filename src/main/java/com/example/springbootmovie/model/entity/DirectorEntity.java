package com.example.springbootmovie.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@NamedEntityGraph(
        name = "Director.movies",
        attributeNodes = @NamedAttributeNode("movies")
)
@Table(name = "directors")
public class DirectorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @ToString.Exclude
    @OneToMany(mappedBy = "director", fetch = FetchType.LAZY)
    private List<MovieEntity> movies;
    @Column(name = "bio", length = 1500)
    private String bio;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "photo_path")
    private String photoPath;
}

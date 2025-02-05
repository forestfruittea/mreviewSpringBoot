package com.example.springbootmovie.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Builder
@Entity
@Table(name = "movies")
public class MovieEntity {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ToString.Include
    @Column(name = "title", nullable = false)
    @NotNull
    private String title;

    @ToString.Include
    @Column(name = "description", length = 500)
    private String description;

    @ToString.Include
    @Column(name = "country")
    private String country;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY, optional = false)
    private DirectorEntity director;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<GenreEntity> genres;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<ActorEntity> actors;

    @ToString.Include
    @Column(name = "release_date")
    private LocalDate releaseDate;

    @ToString.Include
    @Column(name = "poster_path", nullable = false)
    @NotNull
    private String posterPath;

    @ToString.Include
    @Column(name = "length")
    private Long length;

    @ToString.Include
    @Column(name = "budget")
    private Long budget;

    @ToString.Include
    @Column(name = "box_office")
    private Long boxOffice;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<ReviewEntity> reviews;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<RatingEntity> ratings;

}

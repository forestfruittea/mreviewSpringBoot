package com.example.springbootmovie.model.dto;
import com.example.springbootmovie.model.entity.MovieEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDto {
    private Long id;
    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;
    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;
    private String country;
    @Valid
    private DirectorDto director;
    @Valid
    private List<ActorDto> actors;
    @Valid
    private List<GenreDto> genres;
    private LocalDate releaseDate;
    @NotNull(message = "Poster path cannot be null.")
    @Size(min = 1, max = 255, message = "Poster path must be between 1 and 255 characters")
    private String posterPath;
    @Positive(message = "Length must be a positive number")
    private Long length;
    @Positive(message = "Budget must be a positive number")
    private Long budget;
    @Positive(message = "Box office must be a positive number")
    private Long boxOffice;
    private List<ReviewDto> reviews;
    private double averageRating;

    //Uses in JSPs
    public String getFullPosterPath() {
        String basePath = ResourceBundle.getBundle("application").getString("base.poster.path");
        if (posterPath != null && !posterPath.isEmpty()) {
            return basePath + posterPath;
        }
        return null;
    }
    public String getReleaseYear() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        return releaseDate.format(formatter);
    }
    public static MovieDto of(MovieEntity movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .country(movie.getCountry())
                .director(DirectorDto.ofMovie(movie.getDirector())) // Assuming you have a similar 'of' method for DirectorDto
                .actors(movie.getActors() != null
                        ? movie.getActors().stream()
                        .map(actor -> ActorDto.builder()
                                .id(actor.getId())
                                .name(actor.getName())
                                .photoPath(actor.getPhotoPath())
                                .build()) // Using ActorDto.of to map each Actor
                        .toList()
                        : null)
                .genres(movie.getGenres() != null
                        ? movie.getGenres().stream()
                        .map(genre -> GenreDto.builder()
                                .id(genre.getId())
                                .name(genre.getName())
                                .build()) // Assuming you have a similar 'of' method for GenreDto
                        .toList()
                        : null)
                .releaseDate(movie.getReleaseDate())
                .posterPath(movie.getPosterPath())
                .length(movie.getLength())
                .budget(movie.getBudget())
                .boxOffice(movie.getBoxOffice())
                .reviews(movie.getReviews() != null
                        ? movie.getReviews().stream()
                        .map(ReviewDto::of) // Assuming you have a similar 'of' method for ReviewDto
                        .toList()
                        : null)
//                .averageRating(movie.getAverageRating())
                .build();
    }
    public static MovieDto ofReview(MovieEntity movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .releaseDate(movie.getReleaseDate())
                .posterPath(movie.getPosterPath())
                .build();

    }
}

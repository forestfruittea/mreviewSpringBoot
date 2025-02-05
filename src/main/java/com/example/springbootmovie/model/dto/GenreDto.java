package com.example.springbootmovie.model.dto;


import com.example.springbootmovie.model.entity.GenreEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.ResourceBundle;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenreDto {
    private Long id;
    @NotNull(message = "Genre name cannot be null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;
    private List<MovieDto> movies;
    private String description;
    private String imagePath;

    public String getFullImagePath() {
        String basePath = ResourceBundle.getBundle("application").getString("base.genreImage.path");
        if (imagePath != null && !imagePath.isEmpty()) {
            return basePath + imagePath;
        }
        return null;
    }
    public static GenreDto of(GenreEntity genre) {
        return GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .description(genre.getDescription())
                .imagePath(genre.getImagePath())
                // Map only movies without their actors to avoid lazy initialization issues
                .movies(genre.getMovies() != null
                        ? genre.getMovies().stream()
                        .map(movie -> MovieDto.builder()
                                .id(movie.getId())
                                .title(movie.getTitle())
                                .releaseDate(movie.getReleaseDate())
                                .build())
                        .toList()
                        : null)
                .build();
    }
}

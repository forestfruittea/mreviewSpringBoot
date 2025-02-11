package com.example.springbootmovie.model.dto;

import com.example.springbootmovie.model.entity.DirectorEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DirectorDto {
    private Long id;
    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String name;
    private List<MovieDto> movies;
    @Size(max = 1500, message = "Bio must be at most 1500 characters")
    private String bio;
    private LocalDate dateOfBirth;
    private String photoPath;
    public static DirectorDto of(DirectorEntity director) {
        return DirectorDto.builder()
                .id(director.getId())
                .name(director.getName())
                .bio(director.getBio())
                .dateOfBirth(director.getDateOfBirth())
                .photoPath(director.getPhotoPath())
                // Map only movies without their actors to avoid lazy initialization issues
                .movies(director.getMovies() != null
                        ? director.getMovies().stream()
                        .map(movie -> MovieDto.builder()
                                .id(movie.getId())
                                .title(movie.getTitle())
                                .releaseDate(movie.getReleaseDate())
                                .posterPath(movie.getPosterPath())
                                .build())
                        .toList()
                        : null)
                .build();
    }
    public static DirectorDto ofMovie(DirectorEntity director) {
        return DirectorDto.builder()
                .id(director.getId())
                .name(director.getName())
                .build();

    }
}

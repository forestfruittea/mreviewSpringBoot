package com.example.springbootmovie.model.dto;

import com.example.springbootmovie.model.entity.ActorEntity;
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
public class ActorDto {
    private Long id;
    @NotNull(message = "Actor name cannot be null")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String name;
    private List<MovieDto> movies;
    @Size(max = 1500, message = "Bio must be at most 1500 characters")
    private String bio;
    private Long yearOfBirth;
    @NotNull(message = "Actor photo path cannot be null")
    private String photoPath;
    private Long height;

    public String getFullPhotoPath() {
        String basePath = ResourceBundle.getBundle("application").getString("base.photo.path");
        if (photoPath != null && !photoPath.isEmpty()) {
            return basePath + photoPath;
        }
        return null;
    }

    public static ActorDto of(ActorEntity actor) {
        return ActorDto.builder()
                .id(actor.getId())
                .name(actor.getName())
                .bio(actor.getBio())
                .yearOfBirth(actor.getYearOfBirth())
                .photoPath(actor.getPhotoPath())
                .height(actor.getHeight())
                // Map only movies without their actors to avoid lazy initialization issues
                .movies(actor.getMovies() != null
                        ? actor.getMovies().stream()
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
}


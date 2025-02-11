package com.example.springbootmovie.service;
import com.example.springbootmovie.model.dto.GenreDto;
import com.example.springbootmovie.model.entity.GenreEntity;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    GenreDto save(GenreDto genreDto);
    void delete(Long genreId);
    Optional<GenreDto> findById(Long genreId);
    List<GenreDto> findAllByMovieId(Long movieId);
    List<GenreDto> findAll();
    List<GenreDto> findAllSorted();
    List<GenreEntity> findOrCreateGenres(List<GenreDto> genreDtos);
    //TODO GenreDto update(GenreDto genreDto);

}

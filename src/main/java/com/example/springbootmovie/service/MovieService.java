package com.example.springbootmovie.service;

import com.example.springbootmovie.model.dto.MovieDto;

import java.util.List;
import java.util.Optional;
public interface MovieService {
    void save(MovieDto movieDto);
    void delete(Long movieId);
    List<MovieDto> findByName(String name);
    Optional<MovieDto> findById(Long id);
    List<MovieDto>findAllByGenreId(Long genreId);
    List<MovieDto> findAllSorted();
//    List<MovieDto> findMoviesByPage(int page, int size);
    long count();
    List<MovieDto> findAll();

    //TODO MovieDto update(MovieDto movieDto);

}
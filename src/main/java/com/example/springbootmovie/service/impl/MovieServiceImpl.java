package com.example.springbootmovie.service.impl;

import com.example.springbootmovie.model.dto.GenreDto;
import com.example.springbootmovie.model.dto.MovieDto;
import com.example.springbootmovie.model.entity.ActorEntity;
import com.example.springbootmovie.model.entity.DirectorEntity;
import com.example.springbootmovie.model.entity.GenreEntity;
import com.example.springbootmovie.model.entity.MovieEntity;
import com.example.springbootmovie.mapper.impl.MovieMapper;
import com.example.springbootmovie.repository.MovieRepository;
import com.example.springbootmovie.service.ActorService;
import com.example.springbootmovie.service.DirectorService;
import com.example.springbootmovie.service.GenreService;
import com.example.springbootmovie.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@Slf4j
 public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final GenreService genreService;
    private final ActorService actorService;
    private final DirectorService directorService;
    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper, GenreService genreService, ActorService actorService, DirectorService directorService) {
        this.movieRepository = movieRepository;
        this.actorService = actorService;
        this.directorService = directorService;
        this.movieMapper = movieMapper;
        this.genreService = genreService;
    }
    @Override
    @Transactional
    public void save(MovieDto movieDto) {
        DirectorEntity directorEntity = directorService.findOrCreateDirector(movieDto.getDirector());
        List<ActorEntity> actorEntities = actorService.findOrCreateActors(movieDto.getActors());
        List<GenreEntity> genreEntities = genreService.findOrCreateGenres(movieDto.getGenres());

        MovieEntity movieEntity = movieMapper.toEntity(movieDto);
        movieEntity.setDirector(directorEntity);
        movieEntity.setActors(actorEntities);
        movieEntity.setGenres(genreEntities);

        log.debug("Saving movie: {}", movieDto.getTitle());
        movieRepository.save(movieEntity);
    }
    @Override
    @Transactional
    public void delete(Long movieId) {
        log.debug("deletes movie");
        movieRepository.deleteById(movieId);
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<MovieDto> findById(Long id) {
        Optional<MovieEntity> movieEntity = movieRepository.findByIdWithDetails(id);
        log.debug("finds movie by id");
        return movieEntity.map(MovieDto::of);
    }
    @Override
    @Transactional(readOnly = true)
    public List<MovieDto> findByName(String name) {
        List<MovieEntity> movieEntities = movieRepository.findByTitleContainingIgnoreCase(name);
        log.debug("finds movie by name");
        return movieEntities.stream()
                .map(MovieDto::of)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    public List<MovieDto> findAllByGenreId(Long genreId){
        List<MovieEntity> movieEntities = movieRepository.findAllByGenreId(genreId);
        log.debug("finds all movies by genre id");
        // For each movie, fetch genres using the findAllByMovieId method
        return movieEntities.stream()
                .map(movie -> {
                    List<GenreDto> genreDtos = genreService.findAllByMovieId(movie.getId());  // Assuming movie.getId() gives the movie's ID
                    MovieDto movieDto = MovieDto.of(movie);
                    movieDto.setGenres(genreDtos);
                    return movieDto;
                })
                .collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    public List<MovieDto> findAll() {
        List<MovieEntity> movieEntities = movieRepository.findAll();
        log.debug("finds all movies");
        return movieEntities.stream()
                .map(MovieDto::of)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    public List<MovieDto> findAllSorted() {
        List<MovieEntity> movies = movieRepository.findAll();
        log.debug("finds all movies sorted by name");

        return movies.stream()
                .sorted((m1, m2) -> {
                    String title1 = m1.getTitle().toLowerCase();
                    String title2 = m2.getTitle().toLowerCase();
                    return title1.compareTo(title2);
                })
                .map(MovieDto::of)
                .collect(Collectors.toList());
            }
        }
//TODO
//            @Override
//            public MovieDto update(MovieDto movieDto) {
//                MovieEntity movieEntity = movieMapper.toEntity(movieDto);
//                movieEntity = movieRepository.update(movieEntity);
//                log.debug("updates movie");
//                return movieMapper.toDto(movieEntity);
//            }

package com.example.springbootmovie.service.impl;

import com.example.springbootmovie.model.dto.GenreDto;
import com.example.springbootmovie.model.dto.MovieDto;
import com.example.springbootmovie.model.entity.ActorEntity;
import com.example.springbootmovie.model.entity.DirectorEntity;
import com.example.springbootmovie.model.entity.GenreEntity;
import com.example.springbootmovie.model.entity.MovieEntity;
import com.example.springbootmovie.mapper.impl.ActorMapper;
import com.example.springbootmovie.mapper.impl.DirectorMapper;
import com.example.springbootmovie.mapper.impl.GenreMapper;
import com.example.springbootmovie.mapper.impl.MovieMapper;
import com.example.springbootmovie.repository.ActorRepository;
import com.example.springbootmovie.repository.DirectorRepository;
import com.example.springbootmovie.repository.GenreRepository;
import com.example.springbootmovie.repository.MovieRepository;
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
            private final GenreRepository genreRepository;
            private final ActorRepository actorRepository;
            private final DirectorRepository directorRepository;
            private final MovieMapper movieMapper;
            private final DirectorMapper directorMapper;
            private final ActorMapper actorMapper;
            private final GenreMapper genreMapper;
            private final GenreService genreService;


            @Autowired
            public MovieServiceImpl(MovieRepository movieRepository, GenreRepository genreRepository, ActorRepository actorRepository, DirectorRepository directorRepository, MovieMapper movieMapper, DirectorMapper directorMapper, ActorMapper actorMapper, GenreMapper genreMapper, GenreService genreService) {
                this.movieRepository = movieRepository;
                this.genreRepository = genreRepository;
                this.actorRepository = actorRepository;
                this.directorRepository = directorRepository;
                this.movieMapper = movieMapper;
                this.directorMapper = directorMapper;
                this.actorMapper = actorMapper;
                this.genreMapper = genreMapper;
                this.genreService = genreService;
            }
            @Override
            @Transactional
            public void save(MovieDto movieDto) {
                DirectorEntity directorEntity = directorRepository.findByName(movieDto.getDirector().getName())
                        .orElseGet(() -> directorRepository.save(directorMapper.toEntity(movieDto.getDirector())));

                List<ActorEntity> actorEntities = movieDto.getActors().stream()
                        .map(actorDto -> actorRepository.findByName(actorDto.getName())
                                .orElseGet(() -> actorRepository.save(actorMapper.toEntity(actorDto))))
                        .collect(Collectors.toList());

                List<GenreEntity> genreEntities = movieDto.getGenres().stream()
                        .map(genreDto -> genreRepository.findByName(genreDto.getName())
                                .orElseGet(() -> genreRepository.save(genreMapper.toEntity(genreDto))))
                        .collect(Collectors.toList());

                MovieEntity movieEntity = movieMapper.toEntity(movieDto);
                movieEntity.setDirector(directorEntity);
                movieEntity.setActors(actorEntities);
                movieEntity.setGenres(genreEntities);
                log.debug("saves movie");
                movieRepository.save(movieEntity);
            }

//TODO
//            @Override
//            public MovieDto update(MovieDto movieDto) {
//                MovieEntity movieEntity = movieMapper.toEntity(movieDto);
//                movieEntity = movieRepository.update(movieEntity);
//                log.debug("updates movie");
//                return movieMapper.toDto(movieEntity);
//            }

            @Override
            @Transactional(readOnly = true)
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
            public List<MovieDto> findByName(String name){
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
//            @Override
//            @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
//            public List<MovieDto> findMoviesByPage(int page, int size) {
//                List<MovieEntity> movieEntities = movieRepository.findMoviesByPage(page, size);
//                log.debug("finds movies for page {} with size {}", page, size);
//                return movieEntities.stream()
//                        .map(movieMapper::toDto)
//                        .collect(Collectors.toList());
//            }
            @Override
            public long count() {
                return movieRepository.count();
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

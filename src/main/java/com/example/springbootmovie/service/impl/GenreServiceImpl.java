package com.example.springbootmovie.service.impl;

import com.example.springbootmovie.model.dto.GenreDto;
import com.example.springbootmovie.model.entity.GenreEntity;
import com.example.springbootmovie.mapper.impl.GenreMapper;
import com.example.springbootmovie.repository.GenreRepository;
import com.example.springbootmovie.service.GenreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository, GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }
    @Override
    @Transactional
    public GenreDto save(GenreDto genreDto) {
        GenreEntity genreEntity = genreMapper.toEntity(genreDto);
        genreEntity = genreRepository.save(genreEntity);
        log.debug("saves genre");
        return GenreDto.of(genreEntity);
    }
//TODO
//    @Override
//    public GenreDto update(GenreDto genreDto) {
//        GenreEntity genreEntity = genreMapper.toEntity(genreDto);
//        genreEntity = genreRepository.update(genreEntity);
//        log.debug("updates genre");
//        return genreMapper.toDto(genreEntity);
//    }

    @Override
    @Transactional
    public void delete(Long genreId) {
        log.debug("deletes genre");
        genreRepository.deleteById(genreId);
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<GenreDto> findById(Long genreId) {
        Optional<GenreEntity> genreEntity = genreRepository.findById(genreId);
        log.debug("finds genre by id");
        return genreEntity.map(GenreDto::of);
    }
    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findAllByMovieId(Long movieId){
        List<GenreEntity> genreEntities = genreRepository.findAllByMovieId(movieId);
        log.debug("finds all genres by movie id");
        return genreEntities.stream()
                .map(genreMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findAll() {
        List<GenreEntity> genreEntities = genreRepository.findAll();
        log.debug("finds all genres");
        return genreEntities.stream()
                .map(GenreDto::of)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findAllSorted() {
        List<GenreEntity> genreEntities = genreRepository.findAllSortedByName();
        return genreEntities.stream()
                .map(genreMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional
    public List<GenreEntity> findOrCreateGenres(List<GenreDto> genreDtos) {
        return genreDtos.stream()
                .map(genreDto -> genreRepository.findByName(genreDto.getName())
                        .orElseGet(() -> genreRepository.save(genreMapper.toEntity(genreDto))))
                .collect(Collectors.toList());
    }
}

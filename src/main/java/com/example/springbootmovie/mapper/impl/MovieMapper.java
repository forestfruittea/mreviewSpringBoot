package com.example.springbootmovie.mapper.impl;

import com.example.springbootmovie.model.dto.MovieDto;
import com.example.springbootmovie.model.entity.MovieEntity;
import com.example.springbootmovie.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper implements Mapper<MovieEntity, MovieDto> {

    private final ModelMapper modelMapper;
    @Autowired
    public MovieMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MovieEntity toEntity(MovieDto movieDto) {
        return modelMapper.map(movieDto, MovieEntity.class);
    }

    @Override
    public MovieDto toDto(MovieEntity movieEntity) {
        return modelMapper.map(movieEntity, MovieDto.class);
    }
}

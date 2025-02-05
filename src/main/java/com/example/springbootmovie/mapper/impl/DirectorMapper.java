package com.example.springbootmovie.mapper.impl;

import com.example.springbootmovie.model.dto.DirectorDto;
import com.example.springbootmovie.model.entity.DirectorEntity;
import com.example.springbootmovie.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DirectorMapper implements Mapper<DirectorEntity, DirectorDto> {
    private final ModelMapper modelMapper;
    @Autowired
    public DirectorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public DirectorEntity toEntity(DirectorDto directorDto) {
        return modelMapper.map(directorDto, DirectorEntity.class);
    }

    @Override
    public DirectorDto toDto(DirectorEntity directorEntity) {
        return modelMapper.map(directorEntity, DirectorDto.class);
    }
}

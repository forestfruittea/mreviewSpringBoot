package com.example.springbootmovie.mapper.impl;

import com.example.springbootmovie.model.dto.ActorDto;
import com.example.springbootmovie.model.entity.ActorEntity;
import com.example.springbootmovie.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActorMapper implements Mapper<ActorEntity, ActorDto> {

    private final ModelMapper modelMapper;
    @Autowired
    public ActorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ActorEntity toEntity(ActorDto actorDto) {
        return modelMapper.map(actorDto, ActorEntity.class);
    }

    @Override
    public ActorDto toDto(ActorEntity actorEntity) {
        return modelMapper.map(actorEntity, ActorDto.class);
    }
}
package com.example.springbootmovie.mapper.impl;

import com.example.springbootmovie.model.dto.UserDto;
import com.example.springbootmovie.model.entity.UserEntity;
import com.example.springbootmovie.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<UserEntity, UserDto> {

    private final ModelMapper modelMapper;
    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserEntity toEntity(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }

    @Override
    public UserDto toDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }
}

package com.example.springbootmovie.service;
import com.example.springbootmovie.model.dto.ActorDto;
import com.example.springbootmovie.model.entity.ActorEntity;

import java.util.List;
import java.util.Optional;

public interface ActorService {
    ActorDto save(ActorDto actorDto);
    void delete(Long actorId);
    Optional<ActorDto> findById(Long actorId);
    List<ActorDto> findAll();
    List<ActorDto> findAllSorted();
    List<ActorEntity> findOrCreateActors(List<ActorDto> actorDtos);
    //TODO   DirectorDto update(DirectorDto directorDto);

}

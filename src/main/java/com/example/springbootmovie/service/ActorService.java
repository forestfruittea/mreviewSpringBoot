package com.example.springbootmovie.service;
import com.example.springbootmovie.model.dto.ActorDto;
import java.util.List;
import java.util.Optional;

public interface ActorService {
    ActorDto save(ActorDto actorDto);
    void delete(Long actorId);
    Optional<ActorDto> findById(Long actorId);
    List<ActorDto> findAll();
    List<ActorDto> findAllSorted();
    ActorDto findOrSave(ActorDto actorDto);

    List<ActorDto> getActorsByNames(List<String> actorNames);

    //TODO   DirectorDto update(DirectorDto directorDto);

}

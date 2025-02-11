package com.example.springbootmovie.service;
import com.example.springbootmovie.model.dto.DirectorDto;
import com.example.springbootmovie.model.entity.DirectorEntity;

import java.util.List;
import java.util.Optional;

public interface DirectorService {
    DirectorDto save(DirectorDto directorDto);
    void delete(Long directorId);
    Optional<DirectorDto> findById(Long directorId);
    List<DirectorDto> findAll();
    List<DirectorDto> findAllSorted();
    DirectorEntity findOrCreateDirector(DirectorDto directorDto);
    //TODO   DirectorDto update(DirectorDto directorDto);

}

package com.example.springbootmovie.service.impl;

import com.example.springbootmovie.model.dto.DirectorDto;
import com.example.springbootmovie.model.entity.DirectorEntity;
import com.example.springbootmovie.mapper.impl.DirectorMapper;
import com.example.springbootmovie.repository.DirectorRepository;
import com.example.springbootmovie.service.DirectorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@Slf4j
public class DirectorServiceImpl implements DirectorService{
    private final DirectorRepository directorRepository;
    private final DirectorMapper directorMapper;
    @Autowired
    public DirectorServiceImpl(DirectorRepository directorRepository, DirectorMapper directorMapper) {
        this.directorRepository = directorRepository;
        this.directorMapper = directorMapper;
    }
    @Override
    @Transactional(readOnly = true)
    public DirectorDto save(DirectorDto directorDto) {
        DirectorEntity directorEntity = directorMapper.toEntity(directorDto);
        directorEntity = directorRepository.save(directorEntity);
        log.debug("saves director");
        return directorMapper.toDto(directorEntity);
    }
//TODO
//    @Override
//    public DirectorDto update(DirectorDto directorDto) {
//        DirectorEntity directorEntity = directorMapper.toEntity(directorDto);
//        directorEntity = directorRepository.update(directorEntity);
//        log.debug("updates director");
//        return directorMapper.toDto(directorEntity);
//    }
    @Override
    @Transactional
    public void delete(Long directorId) {
        directorRepository.deleteById(directorId);
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<DirectorDto> findById(Long directorId) {
        Optional<DirectorEntity> directorEntity = directorRepository.findById(directorId);
        log.debug("finds director by id");
        return directorEntity.map(DirectorDto::of);
    }
    @Override
    @Transactional(readOnly = true)
    public List<DirectorDto> findAllSorted() {
        List<DirectorEntity> directorEntities = directorRepository.findAllSortedByName();
        return directorEntities.stream()
                .map(directorMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    public List<DirectorDto> findAll() {
        List<DirectorEntity> directorEntities = directorRepository.findAll();
        log.debug("finds all directors");
        return directorEntities.stream()
                .map(DirectorDto::of)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional
    public DirectorEntity findOrCreateDirector(DirectorDto directorDto) {
        return directorRepository.findByName(directorDto.getName())
                .orElseGet(() -> directorRepository.save(directorMapper.toEntity(directorDto)));
    }
}

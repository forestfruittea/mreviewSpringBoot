package com.example.springbootmovie.service.impl;

import com.example.springbootmovie.model.dto.ActorDto;
import com.example.springbootmovie.model.entity.ActorEntity;
import com.example.springbootmovie.mapper.impl.ActorMapper;
import com.example.springbootmovie.repository.ActorRepository;
import com.example.springbootmovie.service.ActorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@Slf4j
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    @Autowired
    public ActorServiceImpl(ActorRepository actorRepository, ActorMapper actorMapper) {
        this.actorRepository = actorRepository;
        this.actorMapper = actorMapper;
    }
    @Override
    @Transactional
    public ActorDto save(ActorDto actorDto) {
        ActorEntity actorEntity = actorMapper.toEntity(actorDto);
        actorEntity = actorRepository.save(actorEntity);
        log.debug("saves actor");
        return actorMapper.toDto(actorEntity);
    }
//TODO
//    @Override
//    public ActorDto update(ActorDto actorDto) {
//        ActorEntity actorEntity = actorMapper.toEntity(actorDto);
//        actorEntity = actorRepository.update(actorEntity);
//        log.debug("updates an actor");
//        return actorMapper.toDto(actorEntity);
//    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("deletes actor by id");
        actorRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ActorDto> findById(Long id) {
        Optional<ActorEntity> actorEntity = actorRepository.findById(id);
        log.debug("finds actor sorted by id");
        return actorEntity.map(ActorDto::of);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActorDto> findAll() {
        List<ActorEntity> actorEntities = actorRepository.findAll();
        log.debug("finds all actors");

        return actorEntities.stream()
                .map(ActorDto::of)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    public List<ActorDto> findAllSorted() {
        List<ActorEntity> actorEntities = actorRepository.findAllSortedByName();
        return actorEntities.stream()
                .map(actorMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional
    public List<ActorEntity> findOrCreateActors(List<ActorDto> actorDtos) {
        return actorDtos.stream()
                .map(actorDto -> actorRepository.findByName(actorDto.getName())
                        .orElseGet(() -> actorRepository.save(actorMapper.toEntity(actorDto))))
                .collect(Collectors.toList());
    }
}

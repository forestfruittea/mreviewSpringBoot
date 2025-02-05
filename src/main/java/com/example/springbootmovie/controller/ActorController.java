package com.example.springbootmovie.controller;

import com.example.springbootmovie.model.dto.ActorDto;
import com.example.springbootmovie.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/actors")
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;

    @GetMapping
    public ResponseEntity<List<ActorDto>> listActors() {
        List<ActorDto> actors = actorService.findAll();
        return ResponseEntity.ok(actors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorDto> getActorDetails(@PathVariable Long id) {
        Optional<ActorDto> actor = actorService.findById(id);
        return actor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
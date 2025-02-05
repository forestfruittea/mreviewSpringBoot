package com.example.springbootmovie.controller;

import com.example.springbootmovie.model.dto.*;
import com.example.springbootmovie.model.role.Role;
import com.example.springbootmovie.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin/tool")
public class AdminController {

    private final MovieService movieService;
    private final DirectorService directorService;
    private final ActorService actorService;
    private final UserService userService;
    private final GenreService genreService;

    @Autowired
    public AdminController(MovieService movieService, DirectorService directorService, ActorService actorService, UserService userService, GenreService genreService) {
        this.movieService = movieService;
        this.directorService = directorService;
        this.actorService = actorService;
        this.userService = userService;
        this.genreService = genreService;
    }
    @PostMapping("/movies/create")
    public ResponseEntity<String> createMovie(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String country,
            @RequestParam String releaseDate,
            @RequestParam String length,
            @RequestParam String budget,
            @RequestParam String boxOffice,
            @RequestParam List<String> actors,
            @RequestParam List<String> genres,
            @RequestParam String directorName,
            @RequestParam("posterFile") MultipartFile posterFile) {
        try {
            // Handle file upload
            String posterPath = Paths.get(posterFile.getOriginalFilename()).getFileName().toString();
            String uploadDir = "static/uploads/";
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }
            posterFile.transferTo(new File(uploadPath, posterPath));
            DirectorDto directorDto = new DirectorDto();
            directorDto.setName(directorName);

            List<ActorDto> actorDtos = actors.stream()
                    .map(name -> {
                        ActorDto actorDto = new ActorDto();
                        actorDto.setName(name);
                        return actorDto;
                    })
                    .toList();

            // Convert List<String> genres into List<GenreDto>
            List<GenreDto> genreDtos = genres.stream()
                    .map(name -> {
                        GenreDto genreDto = new GenreDto();
                        genreDto.setName(name);
                        return genreDto;
                    })
                    .toList();
            // Create Movie DTO
            MovieDto movieDto = MovieDto.builder()
                    .title(title)
                    .description(description)
                    .country(country)
                    .releaseDate(LocalDate.parse(releaseDate))
                    .length(Long.parseLong(length))
                    .budget(Long.parseLong(budget))
                    .boxOffice(Long.parseLong(boxOffice))
                    .posterPath(posterPath)
                    .director(directorDto)
                    .actors(actorDtos)
                    .genres(genreDtos)
                    .build();

            movieService.save(movieDto);
            return ResponseEntity.ok("Movie created successfully!");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading the poster: " + e.getMessage());
        }
    }
    @PostMapping("/directors/create")
    public ResponseEntity<String> createDirector(@RequestParam String name,
                                                 @RequestParam String bio,
                                                 @RequestParam String dateOfBirth,
                                                 @RequestParam("photo") MultipartFile photoFile) {
        try {
            String photoPath = saveFile(photoFile, "static/uploads");
            DirectorDto directorDto = DirectorDto.builder()
                    .name(name)
                    .bio(bio)
                    .dateOfBirth(LocalDate.parse(dateOfBirth))
                    .photoPath(photoPath)
                    .build();
            directorService.save(directorDto);
            return ResponseEntity.ok("Director created successfully!");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading photo: " + e.getMessage());
        }
    }
    @PostMapping("/genres/create")
    public ResponseEntity<String> createGenre(   @RequestParam String name,
                                                 @RequestParam String description,
                                                 @RequestParam("image") MultipartFile imageFile) {
        try {
            String imagePath = saveFile(imageFile, "static/uploads");
            GenreDto genreDto = GenreDto.builder()
                    .name(name)
                    .description(description)
                    .imagePath(imagePath)
                    .build();
            genreService.save(genreDto);
            return ResponseEntity.ok("Genre created successfully!");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading image: " + e.getMessage());
        }
    }
    @PostMapping("/actors/create")
    public ResponseEntity<String> createActor(
                        @RequestParam("name") String name,
                        @RequestParam("bio") String bio,
                        @RequestParam("yearOfBirth") Long yearOfBirth,
                        @RequestParam("height") Long height,
                        @RequestParam("photo") MultipartFile photoFile) {
        try {
        String photoPath = saveFile(photoFile, "static/uploads");
            // Create ActorDto
            ActorDto actorDto = ActorDto.builder()
                    .name(name)
                    .bio(bio)
                    .yearOfBirth(yearOfBirth)
                    .height(height)
                    .photoPath(photoPath)
                    .build();

            actorService.save(actorDto);

            return ResponseEntity.ok("Director created successfully!");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading photo: " + e.getMessage());
        }
    }
    @PostMapping("/users/create")
    public ResponseEntity<String> createUser(@RequestParam("username") String username,
                                             @RequestParam("password") String password,
                                             @RequestParam("role") String roleString) {
        Role role = Role.valueOf(roleString.toUpperCase());
        UserDto userDto = UserDto.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();
        boolean success = userService.registerUser(userDto);
        if (success) {
            return ResponseEntity.ok("User created successfully!");
        } else {
            return ResponseEntity.badRequest().body("Username is already taken or an error occurred.");
        }
    }
    private String saveFile(MultipartFile file, String uploadDir) throws IOException {
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        String filePath = Paths.get(file.getOriginalFilename()).getFileName().toString();
        file.transferTo(new File(uploadPath, filePath));
        return filePath;
    }


}

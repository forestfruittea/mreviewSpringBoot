package com.example.springbootmovie.controller;

import com.example.springbootmovie.model.dto.GenreDto;
import com.example.springbootmovie.model.dto.MovieDto;
import com.example.springbootmovie.service.GenreService;
import com.example.springbootmovie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class GenreController {

    private final GenreService genreService;
    private final MovieService movieService;

    @Autowired
    public GenreController(GenreService genreService, MovieService movieService) {
        this.genreService = genreService;
        this.movieService = movieService;
    }

    // List all genres
    @GetMapping("/genres")
    public String listGenres(Model model) {
        List<GenreDto> genres = genreService.findAll();
        model.addAttribute("genres", genres);
        return "genres"; // Corresponds to genres.jsp
    }



    // View Genre Details
    @GetMapping("/genre")
    public String genreDetails(@RequestParam(name = "id") Long id, Model model) {
        Optional<GenreDto> optionalGenreDto = genreService.findById(id);

        if (optionalGenreDto.isPresent()) {
            GenreDto genreDto = optionalGenreDto.get();
            List<MovieDto> movies = movieService.findAllByGenreId(id);
            model.addAttribute("genre", genreDto);
            model.addAttribute("movies", movies);
            return "genre-details"; // Corresponds to genre-details.jsp
        } else {
            model.addAttribute("error", "Genre not found");
            return "error"; // Generic error page
        }
    }
}


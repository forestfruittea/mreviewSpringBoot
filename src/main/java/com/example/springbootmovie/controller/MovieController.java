package com.example.springbootmovie.controller;


import com.example.springbootmovie.model.dto.MovieDto;
import com.example.springbootmovie.model.dto.RatingDto;
import com.example.springbootmovie.model.dto.ReviewDto;
import com.example.springbootmovie.service.MovieService;
import com.example.springbootmovie.service.RatingService;
import com.example.springbootmovie.service.ReviewService;
import com.example.springbootmovie.service.UserService;
import com.example.springbootmovie.util.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class MovieController {

    private final MovieService movieService;
    private final ReviewService reviewService;
    private final UserService userService;
    private final RatingService ratingService;

    public MovieController(MovieService movieService, ReviewService reviewService, UserService userService, RatingService ratingService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.ratingService = ratingService;
    }

    @GetMapping("/movies")
    public String listMovies(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 2;
        List<MovieDto> movies = movieService.findAll();

        for (MovieDto movie : movies) {
            Double averageRating = ratingService.calculateAverageRating(movie.getId());
            movie.setAverageRating(averageRating);
        }

        long totalMovies = movieService.count();
        int totalPages = (int) Math.ceil((double) totalMovies / pageSize);

        model.addAttribute("movies", movies);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "movies"; // Corresponds to movies.jsp
    }

    @GetMapping("/movie")
    public String movieDetails(@RequestParam(name = "id") Long id, Model model) {
        Optional<MovieDto> movieOptional = movieService.findById(id);

        if (movieOptional.isPresent()) {
            MovieDto movie = movieOptional.get();
            List<ReviewDto> reviews = reviewService.findAllForMovie(id);
            Double averageRating = ratingService.calculateAverageRating(id);
            Long userId = SecurityUtil.getLoggedInUserId();
            Optional<RatingDto> userRating = ratingService.findByUserAndMovie(userId, id);

            model.addAttribute("movie", movie);
            model.addAttribute("reviews", reviews);
            model.addAttribute("averageRating", averageRating);
            model.addAttribute("userRating", userRating.orElse(null));
            return "movie-details"; // Corresponds to movie-details.jsp
        } else {
            model.addAttribute("error", "Movie not found");
            return "error"; // Error page
        }
    }
}


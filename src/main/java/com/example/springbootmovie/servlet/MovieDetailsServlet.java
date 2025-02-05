package com.example.springbootmovie.servlet;//package com.example.movierev.servlet;
//
//import com.example.movierev.dto.MovieDto;
//import com.example.movierev.dto.RatingDto;
//import com.example.movierev.dto.ReviewDto;
//import com.example.movierev.dto.UserDto;
//import com.example.movierev.service.MovieService;
//import com.example.movierev.service.RatingService;
//import com.example.movierev.service.ReviewService;
//import com.example.movierev.service.UserService;
//import jakarta.inject.Inject;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//
//@WebServlet("/movie")
//public class MovieDetailsServlet extends HttpServlet {
//    private final MovieService movieService;
//    private final ReviewService reviewService;
//    private final UserService userService;
//    private final RatingService ratingService;
//
//
//    @Inject
//    public MovieDetailsServlet(MovieService movieService, ReviewService reviewService, UserService userService, RatingService ratingService) {
//        this.movieService = movieService;
//        this.reviewService = reviewService;
//        this.userService = userService;
//        this.ratingService = ratingService;
//    }
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = request.getParameter("action");
//        String movieIdParam = request.getParameter("id");
//        if (movieIdParam == null || (action == null)) {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid data");
//            return;
//        }
//
//        try {
//            Long movieId = Long.parseLong(movieIdParam);
//            Long userId = userService.getLoggedInUserId(request);
//
//            Optional<UserDto> userOptional = userService.findById(userId);
//            Optional<MovieDto> movieDtoOptional = movieService.findById(movieId);
//
//            if (userOptional.isPresent() && movieDtoOptional.isPresent()) {
//                UserDto userDto = userOptional.get();
//                MovieDto movieDto = movieDtoOptional.get();
//
//                // Handle Review submission
//                if ("review".equals(action)) {
//                    String content = request.getParameter("content");
//                    if (content == null || content.isBlank()) {
//                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid review data");
//                        return;
//                    }
//
//                    ReviewDto reviewDto = new ReviewDto();
//                    reviewDto.setContent(content);
//                    reviewDto.setUser(userDto);
//                    reviewDto.setMovie(movieDto);
//                    reviewService.save(reviewDto);
//                }
//
//                // Handle Rating submission
//                if ("rate".equals(action)) {
//                    String ratingParam = request.getParameter("rating");
//                    if (ratingParam != null) {
//                        Integer ratingValue = Integer.parseInt(ratingParam);
//
//                        // If user already rated, update the rating
//                        Optional<RatingDto> existingRatingOpt = ratingService.findByUserAndMovie(userId, movieId);
//                        if (existingRatingOpt.isPresent()) {
//                            RatingDto existingRating = existingRatingOpt.get();
//                            existingRating.setRating(ratingValue);  // Update existing rating
//                            ratingService.update(existingRating);
//                        } else {
//                            // If no existing rating, create a new one
//                            RatingDto newRating = new RatingDto();
//                            newRating.setUser(userDto);
//                            newRating.setMovie(movieDto);
//                            newRating.setRating(ratingValue);
//                            ratingService.save(newRating);
//                        }
//                    }
//                }
//
//                // After processing, redirect or forward to movie details page
//                doGet(request, response);
//            } else {
//                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User or Movie not found");
//            }
//        } catch (NumberFormatException e) {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid movie ID format");
//        }
//    }
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String movieId = request.getParameter("id");
//
//        if (movieId != null) {
//            try {
//                Long id = Long.parseLong(movieId);
//
//                Optional<MovieDto> optionalMovie = movieService.findById(id);
//                List<ReviewDto> reviewDtoList = reviewService.findAllForMovie(id);
//                Double averageRating = ratingService.calculateAverageRating(id);
//                Long userId = userService.getLoggedInUserId(request);
//                Optional<RatingDto> userRatingOpt = ratingService.findByUserAndMovie(userId, id);
//                if (optionalMovie.isPresent()) {
//
//                    request.setAttribute("movie", optionalMovie.get());
//                    request.setAttribute("reviews",reviewDtoList);
//                    request.setAttribute("averageRating", averageRating);
//                    request.setAttribute("userRating", userRatingOpt.orElse(null));
//                    request.getRequestDispatcher("/WEB-INF/movie-details.jsp").forward(request, response);
//
//                } else {
//                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Movie not found");
//                }
//            } catch (NumberFormatException e) {
//                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid movie ID format");
//            }
//        } else {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Movie ID is required");
//        }
//    }
//
//}

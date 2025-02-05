package com.example.springbootmovie.controller;

import com.example.springbootmovie.model.dto.MovieDto;
import com.example.springbootmovie.model.dto.RatingDto;
import com.example.springbootmovie.model.dto.ReviewDto;
import com.example.springbootmovie.model.dto.UserDto;
import com.example.springbootmovie.service.RatingService;
import com.example.springbootmovie.service.ReviewService;
import com.example.springbootmovie.service.UserService;
import com.example.springbootmovie.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;
    private final RatingService ratingService;
    private final ReviewService reviewService;

    @Autowired
    public UserController(UserService userService, RatingService ratingService, ReviewService reviewService) {
        this.userService = userService;
        this.ratingService = ratingService;
        this.reviewService = reviewService;
    }

    @GetMapping("/userprofile")
    public String getUserDetails(@RequestParam("id") Long userId, Model model) {
        // Fetch user details (avatar, username)
        Optional<UserDto> userOpt = userService.findById(userId);
        if (userOpt.isEmpty()) {
            return "error/404";
        }
        UserDto user = userOpt.get();

        List<RatingDto> ratings = ratingService.findByUser(userId);

        Map<Long, RatingDto> movieRatings = ratings.stream()
                .collect(Collectors.toMap(rating -> rating.getMovie().getId(), rating -> rating));

        List<MovieDto> ratedMovies = ratings.stream()
                .map(RatingDto::getMovie)
                .collect(Collectors.toList());

        model.addAttribute("user", user);
        model.addAttribute("ratedMovies", ratedMovies);
        model.addAttribute("movieRatings", movieRatings);  // Pass the movie ratings map to the view

        return "user-details"; // JSP or Thymeleaf template for the user profile
    }
    @GetMapping("/account")
    public String getAccountDetails(Model model) {
        Long userId = SecurityUtil.getLoggedInUserId();
        Optional<UserDto> userOptional = userService.findById(userId);

        if (userOptional.isPresent()) {
            UserDto user = userOptional.get();
            model.addAttribute("user", user);
            return "account";  // Return view for account details (e.g., account.jsp or Thymeleaf template)
        } else {
            return "error/404";  // Custom error view
        }
    }

    // Display all reviews made by the logged-in user
    @GetMapping("/account/reviews")
    public String getAccountReviews(Model model) {
        Long userId = SecurityUtil.getLoggedInUserId();
        List<ReviewDto> reviews = reviewService.findAllForUser(userId);

        model.addAttribute("reviews", reviews);
        return "account-reviews";  // Return view for user reviews (e.g., account-reviews.jsp or Thymeleaf template)
    }
}



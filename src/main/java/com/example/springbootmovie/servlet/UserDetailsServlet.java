package com.example.springbootmovie.servlet;//package com.example.movierev.servlet;
//
//import com.example.movierev.dto.MovieDto;
//import com.example.movierev.dto.RatingDto;
//import com.example.movierev.dto.UserDto;
//import com.example.movierev.service.RatingService;
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
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@WebServlet("/userprofile")
//public class UserDetailsServlet extends HttpServlet {
//    private final UserService userService;
//    private final RatingService ratingService;
//
//    @Inject
//    public UserDetailsServlet(UserService userService, RatingService ratingService) {
//        this.userService = userService;
//        this.ratingService = ratingService;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String userIdParam = req.getParameter("id");
//        Long userId = Long.valueOf(userIdParam);
//
//        // Fetch user details (avatar, username)
//        Optional<UserDto> userOpt = userService.findById(userId);
//        if (userOpt.isEmpty()) {
//            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
//            return;
//        }
//        UserDto user = userOpt.get();
//
//        // Fetch the ratings the user has made (i.e., movies they have rated)
//        List<RatingDto> ratings = ratingService.findByUser(userId);
//
//        Map<Long, RatingDto> movieRatings = ratings.stream()
//                .collect(Collectors.toMap(rating -> rating.getMovie().getId(), rating -> rating));
//
//        List<MovieDto> ratedMovies = ratings.stream()
//                .map(RatingDto::getMovie)
//                .collect(Collectors.toList());
//
//
//        req.setAttribute("user", user);
//        req.setAttribute("ratedMovies", ratedMovies);
//        req.setAttribute("movieRatings", movieRatings);  // Pass the movie ratings map to the JSP
//
//
//        // Forward the request to the JSP page
//        req.getRequestDispatcher("/WEB-INF/user-details.jsp").forward(req, resp);
//    }
//}
//

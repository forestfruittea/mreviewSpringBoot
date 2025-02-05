package com.example.springbootmovie.servlet;//package com.example.movierev.servlet;
//
//import com.example.movierev.dto.ReviewDto;
//import com.example.movierev.service.ReviewService;
//import com.example.movierev.service.UserService;
//import jakarta.inject.Inject;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Controller;
//
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet("/account/reviews")
//@Controller
//public class AccountReviewsServlet extends HttpServlet {
//    private final ReviewService reviewService;
//    private final UserService userService;
//    @Inject
//    public AccountReviewsServlet(ReviewService reviewService, UserService userService) {
//        this.reviewService = reviewService;
//        this.userService = userService;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Long userId = userService.getLoggedInUserId(req); // Placeholder logic for now
//        List<ReviewDto> reviews = reviewService.findAllForUser(userId);
//
//        req.setAttribute("reviews", reviews);
//        req.getRequestDispatcher("/WEB-INF/account-reviews.jsp").forward(req, resp);
//    }
//}

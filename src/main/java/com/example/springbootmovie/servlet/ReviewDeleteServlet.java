package com.example.springbootmovie.servlet;//package com.example.movierev.servlet;
//
//import com.example.movierev.dto.ReviewDto;
//import com.example.movierev.service.ReviewService;
//import jakarta.inject.Inject;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet("/admin/tool/reviews/delete")
//public class ReviewDeleteServlet extends HttpServlet {
//
//    @Inject
//    private ReviewService reviewService;
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // Fetch all reviews, sorted by username and movie title
//        List<ReviewDto> reviews = reviewService.findAllSortedByUsernameAndMovieTitle();
//
//        req.setAttribute("reviews", reviews);
//        req.getRequestDispatcher("/WEB-INF/review-delete.jsp").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String reviewIdParam = req.getParameter("reviewId");
//        if (reviewIdParam != null) {
//            try {
//                Long reviewId = Long.parseLong(reviewIdParam);
//                reviewService.delete(reviewId);
//
//                req.setAttribute("message", "Review deleted successfully!");
//            } catch (NumberFormatException e) {
//                req.setAttribute("message", "Invalid review ID.");
//            }
//        }
//
//        // Refresh the list of reviews
//        List<ReviewDto> reviews = reviewService.findAllSortedByUsernameAndMovieTitle();
//        req.setAttribute("reviews", reviews);
//
//        req.getRequestDispatcher("/WEB-INF/review-delete.jsp").forward(req, resp);
//    }
//}
//

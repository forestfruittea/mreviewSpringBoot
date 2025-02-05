package com.example.springbootmovie.servlet;//package com.example.movierev.servlet;
//
//import com.example.movierev.dto.MovieDto;
//import com.example.movierev.service.MovieService;
//import jakarta.inject.Inject;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.util.List;
//
//
//import jakarta.servlet.ServletException;
//
//@WebServlet("/admin/tool/movies/delete")
//public class MovieDeleteServlet extends HttpServlet {
//
//    @Inject
//    private MovieService movieService;
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // Fetch all movies
//        List<MovieDto> movies = movieService.findAllSorted();
//
//        req.setAttribute("movies", movies);
//
//        req.getRequestDispatcher("/WEB-INF/movie-delete.jsp").forward(req, resp);
//    }
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//            String movieIdParam = req.getParameter("movieId");
//            if (movieIdParam != null) {
//                try {
//                    Long movieId = Long.parseLong(movieIdParam);
//                    movieService.delete(movieId);
//
//                    // Set a success message
//                    req.setAttribute("message", "Movie deleted successfully!");
//                } catch (NumberFormatException e) {
//                    // Handle invalid movie ID format
//                    req.setAttribute("message", "Invalid movie ID.");
//                }
//            }
//        List<MovieDto> movies = movieService.findAllSorted();
//
//        req.setAttribute("movies", movies);
//
//        req.getRequestDispatcher("/WEB-INF/movie-delete.jsp").forward(req, resp);
//    }
//}
////
//

package com.example.springbootmovie.servlet;//package com.example.movierev.servlet;
//
//import com.example.movierev.dto.GenreDto;
//import com.example.movierev.dto.MovieDto;
//import com.example.movierev.service.GenreService;
//import com.example.movierev.service.MovieService;
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
//@WebServlet("/genre")
//public class GenreDetailsServlet extends HttpServlet {
//
//
//    private final GenreService genreService;
//    private final MovieService movieService;
//    @Inject
//    public GenreDetailsServlet(GenreService genreService, MovieService movieService) {
//        this.genreService = genreService;
//        this.movieService = movieService;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String genreId = request.getParameter("id");
//
//        if (genreId != null) {
//            try {
//                Long id = Long.parseLong(genreId);
//
//                Optional<GenreDto> optionalGenreDto = genreService.findById(id);
//
//                if (optionalGenreDto.isPresent()) {
//                    GenreDto genreDto = optionalGenreDto.get();
//                    List<MovieDto> movies = movieService.findAllByGenreId(id);
//                    request.setAttribute("genre", genreDto);
//                    request.setAttribute("movies", movies);
//                    request.getRequestDispatcher("/WEB-INF/genre-details.jsp").forward(request, response);
//
//                } else {
//                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Genre not found");
//                }
//            } catch (NumberFormatException e) {
//                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid genre ID format");
//            }
//        } else {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Genre ID is required");
//        }
//    }
//}

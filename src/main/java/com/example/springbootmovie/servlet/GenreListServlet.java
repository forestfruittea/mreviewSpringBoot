package com.example.springbootmovie.servlet;//package com.example.movierev.servlet;
//
//import com.example.movierev.dto.GenreDto;
//import com.example.movierev.service.GenreService;
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
//@WebServlet("/genres")
//public class GenreListServlet extends HttpServlet {
//    @Inject
//    private GenreService genreService;
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        List<GenreDto> genres = genreService.findAll();  // Get actors list
//        req.setAttribute("genres", genres);
//        req.getRequestDispatcher("/WEB-INF/genres.jsp").forward(req, resp);
//    }
//}

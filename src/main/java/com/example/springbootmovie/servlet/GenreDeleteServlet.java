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
//@WebServlet("/admin/tool/genres/delete")
//public class GenreDeleteServlet extends HttpServlet {
//
//    @Inject
//    private GenreService genreService;
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        List<GenreDto> genres = genreService.findAllSorted();
//        req.setAttribute("genres", genres);
//        req.getRequestDispatcher("/WEB-INF/genre-delete.jsp").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String genreIdParam = req.getParameter("genreId");
//        if (genreIdParam != null) {
//            try {
//                Long genreId = Long.parseLong(genreIdParam);
//                genreService.delete(genreId);
//
//                req.setAttribute("message", "Genre deleted successfully!");
//            } catch (NumberFormatException e) {
//                req.setAttribute("message", "Invalid genre ID.");
//            }
//        }
//
//        List<GenreDto> genres = genreService.findAllSorted();
//        req.setAttribute("genres", genres);
//        req.getRequestDispatcher("/WEB-INF/genre-delete.jsp").forward(req, resp);
//    }
//}

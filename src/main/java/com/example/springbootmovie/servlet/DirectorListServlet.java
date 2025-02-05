package com.example.springbootmovie.servlet;//package com.example.movierev.servlet;
//
//import com.example.movierev.dto.DirectorDto;
//import com.example.movierev.service.DirectorService;
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
//@WebServlet("/directors")
//public class DirectorListServlet extends HttpServlet {
//    @Inject
//    private DirectorService directorService;
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        List<DirectorDto> directors = directorService.findAll();  // Get actors list
//        req.setAttribute("directors", directors);
//        req.getRequestDispatcher("/WEB-INF/directors.jsp").forward(req, resp);
//    }
//}
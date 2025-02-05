package com.example.springbootmovie.servlet;//package com.example.movierev.servlet;
//
//import com.example.movierev.dto.ActorDto;
//import com.example.movierev.service.ActorService;
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
//@WebServlet("/actors")
//public class ActorListServlet extends HttpServlet {
//    @Inject
//    private ActorService actorService;
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        List<ActorDto> actors = actorService.findAll();  // Get actors list
//        req.setAttribute("actors", actors);
//        req.getRequestDispatcher("/WEB-INF/actors.jsp").forward(req, resp);
//    }
//}

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
//@WebServlet("/admin/tool/actors/delete")
//public class ActorDeleteServlet extends HttpServlet {
//
//    @Inject
//    private ActorService actorService;
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // Fetch all actors
//        List<ActorDto> actors = actorService.findAllSorted();
//
//        req.setAttribute("actors", actors);
//
//        req.getRequestDispatcher("/WEB-INF/actor-delete.jsp").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String actorIdParam = req.getParameter("actorId");
//        if (actorIdParam != null) {
//            try {
//                Long actorId = Long.parseLong(actorIdParam);
//                actorService.delete(actorId);
//
//                // Set a success message
//                req.setAttribute("message", "Actor deleted successfully!");
//            } catch (NumberFormatException e) {
//                // Handle invalid actor ID format
//                req.setAttribute("message", "Invalid actor ID.");
//            }
//        }
//
//        List<ActorDto> actors = actorService.findAllSorted();
//
//        req.setAttribute("actors", actors);
//
//        req.getRequestDispatcher("/WEB-INF/actor-delete.jsp").forward(req, resp);
//    }
//}
//

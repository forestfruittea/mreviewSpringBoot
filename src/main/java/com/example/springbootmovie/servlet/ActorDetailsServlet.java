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
//import java.util.Optional;
//
//@WebServlet("/actor")
//public class ActorDetailsServlet extends HttpServlet {
//
//    @Inject
//    private ActorService actorService; // Assume this service provides actor data
//
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String actorId = request.getParameter("id");
//
//        if (actorId != null) {
//            try {
//                Long id = Long.parseLong(actorId);
//
//                Optional<ActorDto> optionalActor = actorService.findById(id);
//
//                if (optionalActor.isPresent()) {
//                    ActorDto actor = optionalActor.get();
//
//                    request.setAttribute("actor", actor);
//                    request.getRequestDispatcher("/WEB-INF/actor-details.jsp").forward(request, response);
//
//                } else {
//                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Actor not found");
//                }
//            } catch (NumberFormatException e) {
//                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid actor ID format");
//            }
//        } else {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Actor ID is required");
//        }
//    }
//}
//

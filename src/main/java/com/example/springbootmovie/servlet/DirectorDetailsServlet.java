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
//import java.util.Optional;
//@WebServlet("/director")
//public class DirectorDetailsServlet extends HttpServlet {
//
//    @Inject
//    private DirectorService directorService; // Assume this service provides actor data
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String directorId = request.getParameter("id");
//
//        if (directorId != null) {
//            try {
//                Long id = Long.parseLong(directorId);
//
//                Optional<DirectorDto> optionalDirectorDto = directorService.findById(id);
//
//                if (optionalDirectorDto.isPresent()) {
//                    DirectorDto directorDto = optionalDirectorDto.get();
//
//                    request.setAttribute("director", directorDto);
//                    request.getRequestDispatcher("/WEB-INF/director-details.jsp").forward(request, response);
//
//                } else {
//                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Director not found");
//                }
//            } catch (NumberFormatException e) {
//                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid director ID format");
//            }
//        } else {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Director ID is required");
//        }
//    }
//}
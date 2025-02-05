package com.example.springbootmovie.servlet;//package com.example.movierev.servlet;
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
//@WebServlet("/admin/tool/directors/delete")
//public class DirectorDeleteServlet extends HttpServlet {
//
//    @Inject
//    private DirectorService directorService;
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // Fetch all directors
//        List<DirectorDto> directors = directorService.findAllSorted();
//
//        req.setAttribute("directors", directors);
//
//        req.getRequestDispatcher("/WEB-INF/director-delete.jsp").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String directorIdParam = req.getParameter("directorId");
//        if (directorIdParam != null) {
//            try {
//                Long directorId = Long.parseLong(directorIdParam);
//                directorService.delete(directorId);
//
//                // Set a success message
//                req.setAttribute("message", "Director deleted successfully!");
//            } catch (NumberFormatException e) {
//                // Handle invalid director ID format
//                req.setAttribute("message", "Invalid director ID.");
//            }
//        }
//
//        List<DirectorDto> directors = directorService.findAllSorted();
//
//        req.setAttribute("directors", directors);
//
//        req.getRequestDispatcher("/WEB-INF/director-delete.jsp").forward(req, resp);
//    }
//}
//

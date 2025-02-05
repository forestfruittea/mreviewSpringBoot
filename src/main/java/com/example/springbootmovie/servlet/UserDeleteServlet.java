package com.example.springbootmovie.servlet;//package com.example.movierev.servlet;
//import com.example.movierev.dto.UserDto;
//import com.example.movierev.service.UserService;
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
//@WebServlet("/admin/tool/users/delete")
//public class UserDeleteServlet extends HttpServlet {
//
//    @Inject
//    private UserService userService;
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // Fetch all users
//        List<UserDto> users = userService.findAllSorted();
//
//        req.setAttribute("users", users);
//
//        req.getRequestDispatcher("/WEB-INF/user-delete.jsp").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String userIdParam = req.getParameter("userId");
//        if (userIdParam != null) {
//            try {
//                Long userId = Long.parseLong(userIdParam);
//                userService.delete(userId);
//
//                // Set a success message
//                req.setAttribute("message", "User deleted successfully!");
//            } catch (NumberFormatException e) {
//                // Handle invalid user ID format
//                req.setAttribute("message", "Invalid user ID.");
//            }
//        }
//
//        List<UserDto> users = userService.findAllSorted();
//
//        req.setAttribute("users", users);
//
//        req.getRequestDispatcher("/WEB-INF/user-delete.jsp").forward(req, resp);
//    }
//}
//

package com.example.springbootmovie.servlet;//package com.example.movierev.servlet;
//import com.example.movierev.dto.UserDto;
//import com.example.movierev.service.UserService;
//import jakarta.inject.Inject;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//import java.io.IOException;
//import java.util.Optional;
//
//@WebServlet("/login")
//public class LoginServlet extends HttpServlet {
//
//    private final UserService userService;
//    @Inject
//    public LoginServlet(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Display login form
//        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//
//        Long userId = userService.authenticate(username, password);
//
//        if (userId != null) {
//            // Successful login: Retrieve user details
//            Optional<UserDto> userDtoOpt = userService.findById(userId);
//
//            if (userDtoOpt.isPresent()) {
//                UserDto userDto = userDtoOpt.get();
//
//                // Set session attributes
//                HttpSession session = request.getSession();
//                session.setAttribute("userId", userDto.getId());
//                session.setAttribute("username", userDto.getUsername());
//                session.setAttribute("role", userDto.getRole());
//
//                // Redirect based on role
//
//
//                response.sendRedirect(request.getContextPath()+"/movies");
//            }
//            else {
//                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "User not found.");
//            }
//        } else {
//            // Authentication failed
//            request.setAttribute("error", "Invalid username or password.");
//            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
//        }
//    }
//}
//

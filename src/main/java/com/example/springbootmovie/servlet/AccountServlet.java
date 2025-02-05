package com.example.springbootmovie.servlet;//package com.example.movierev.servlet;
//
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
//import java.util.Optional;
//
//@WebServlet("/account")
//public class AccountServlet extends HttpServlet {
//
//    private final UserService userService;
//    @Inject
//    public AccountServlet(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//            Long userId = userService.getLoggedInUserId(req); // Placeholder logic for now
//            Optional<UserDto> userOptional = userService.findById(userId);
//
//
//            if (userOptional.isPresent()) {
//                UserDto user = userOptional.get();
//                req.setAttribute("user", user);
//                req.getRequestDispatcher("/WEB-INF/account.jsp").forward(req, resp);
//            } else {
//                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
//            }
//        }
//    }

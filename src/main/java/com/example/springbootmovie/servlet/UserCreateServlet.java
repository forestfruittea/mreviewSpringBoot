package com.example.springbootmovie.servlet;//package com.example.movierev.servlet;
//
//import com.example.movierev.filter.Role;
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
//
//@WebServlet("/admin/tool/users/create")
//public class UserCreateServlet extends HttpServlet {
//
//    @Inject
//    private UserService userService;
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // Forward to user-create.jsp to render the user creation form
//        req.getRequestDispatcher("/WEB-INF/user-create.jsp").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // Get form data
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//        String roleString = req.getParameter("role");
//
//        // Convert the role string to Role enum
//        Role role = Role.valueOf(roleString.toUpperCase());  // This will match the role from the dropdown
//
//        // Create a UserDto object
//        UserDto userDto = UserDto.builder()
//                .username(username)
//                .password(password)
//                .role(role)  // Store the role as a string in the UserDto
//                .build();
//
//        // Register the user using the service
//        boolean success = userService.registerUser(userDto);
//
//        if (success) {
//            // Redirect to the user creation page or some success page
//            resp.sendRedirect(req.getContextPath()+"/admin/tool/users/create");
//        } else {
//            // Set error message and forward to the form again in case of failure
//            req.setAttribute("error", "Username is already taken or there was an error during registration.");
//            req.getRequestDispatcher("/WEB-INF/user-create.jsp").forward(req, resp);
//        }
//    }
//}

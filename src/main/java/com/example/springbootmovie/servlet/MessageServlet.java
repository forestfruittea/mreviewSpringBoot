package com.example.springbootmovie.servlet;//package com.example.movierev.servlet;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.util.Locale;
//import java.util.ResourceBundle;
//
//@WebServlet
//public class MessageServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Get user's language from the session or default to "en_US"
//        String lang = (String) request.getSession().getAttribute("language");
//        if (lang == null) {
//            lang = "en_US"; // Default language
//        }
//
//        // Set the appropriate resource bundle
//        ResourceBundle bundle = ResourceBundle.getBundle("messages_en_US", new Locale(lang.split("_")[0], lang.split("_")[1]));
//
//        // Now pass the resource bundle to the JSP or whatever page you want
//        request.setAttribute("bundle", bundle);
//
//        // Forward to your JSP page
//        request.getRequestDispatcher("/WEB-INF/header.jsp").forward(request, response);
//    }
//}

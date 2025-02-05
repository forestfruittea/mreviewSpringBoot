package com.example.springbootmovie.servlet;//package com.example.movierev.servlet;
//
//import com.example.movierev.dto.GenreDto;
//import com.example.movierev.service.GenreService;
//import jakarta.inject.Inject;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.MultipartConfig;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.Part;
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.Validation;
//import jakarta.validation.Validator;
//import jakarta.validation.ValidatorFactory;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.Set;
//
//@WebServlet("/admin/tool/genres/create")
//@MultipartConfig(
//        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
//        maxFileSize = 1024 * 1024 * 10, // 10MB
//        maxRequestSize = 1024 * 1024 * 50 // 50MB
//)
//public class GenreCreateServlet extends HttpServlet {
//
//    @Inject
//    private GenreService genreService;
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("/WEB-INF/genre-create.jsp").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        try {
//            String name = req.getParameter("name");
//            String description = req.getParameter("description");
//
//            Part photoPart = req.getPart("imagePath");
//            String photoPath = Paths.get(photoPart.getSubmittedFileName()).getFileName().toString();
//            String uploadPath = getServletContext().getRealPath("/uploads/genreImages");
//
//            File uploadDir = new File(uploadPath);
//            if (!uploadDir.exists()) {
//                uploadDir.mkdirs();
//            }
//
//            // Save the file
//            String filePath = uploadPath + File.separator + photoPath;
//            photoPart.write(filePath);
//
//            GenreDto genreDto = new GenreDto();
//            genreDto.setName(name);
//            genreDto.setDescription(description);
//            genreDto.setImagePath(photoPath);
//
//            // Validate the actor data
//            Validator validator;
//            try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
//                validator = factory.getValidator();
//            }
//            Set<ConstraintViolation<GenreDto>> violations = validator.validate(genreDto);
//
//            if (!violations.isEmpty()) {
//                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                StringBuilder errorMessage = new StringBuilder();
//                for (ConstraintViolation<GenreDto> violation : violations) {
//                    errorMessage.append(violation.getPropertyPath())
//                            .append(": ")
//                            .append(violation.getMessage())
//                            .append("\n");
//                }
//                resp.getWriter().write("{\"error\":\"" + errorMessage.toString().trim() + "\"}");
//                return;
//            }
//
//            // Save the actor
//            genreService.save(genreDto);
//
//            resp.setStatus(HttpServletResponse.SC_CREATED);
//            resp.getWriter().write("{\"message\":\"Genre created successfully.\"}");
//        } catch (Exception e) {
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
//        }
//    }
//}

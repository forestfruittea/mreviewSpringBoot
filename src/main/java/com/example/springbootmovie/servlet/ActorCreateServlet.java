package com.example.springbootmovie.servlet;//package com.example.movierev.servlet;
//
//import com.example.movierev.dto.ActorDto;
//import com.example.movierev.service.ActorService;
//import com.fasterxml.jackson.core.JsonProcessingException;
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
//@WebServlet("/admin/tool/actors/create")
//@MultipartConfig(
//        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
//        maxFileSize = 1024 * 1024 * 10, // 10MB
//        maxRequestSize = 1024 * 1024 * 50 // 50MB
//)
//public class ActorCreateServlet extends HttpServlet {
//    private final ActorService actorService;
//
//    @Inject
//    public ActorCreateServlet(ActorService actorService) {
//        this.actorService = actorService;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("/WEB-INF/actor-create.jsp").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        try {
//            String name = req.getParameter("name");
//            String bio = req.getParameter("bio");
//            Long yearOfBith = Long.parseLong(req.getParameter("yearOfBirth"));
//            Long height = Long.parseLong(req.getParameter("height"));
//
//            Part photoPart = req.getPart("photoPath");
//            String photoPath = Paths.get(photoPart.getSubmittedFileName()).getFileName().toString();
//            String uploadPath = getServletContext().getRealPath("/uploads/persons");
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
//            ActorDto actorDto = new ActorDto();
//            actorDto.setName(name);
//            actorDto.setBio(bio);
//            actorDto.setYearOfBirth(yearOfBith);
//            actorDto.setHeight(height);
//            actorDto.setPhotoPath(photoPath);
//
//            // Validate the actor data
//            Validator validator;
//            try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
//                validator = factory.getValidator();
//            }
//            Set<ConstraintViolation<ActorDto>> violations = validator.validate(actorDto);
//
//            if (!violations.isEmpty()) {
//                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                StringBuilder errorMessage = new StringBuilder();
//                for (ConstraintViolation<ActorDto> violation : violations) {
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
//            actorService.save(actorDto);
//
//            resp.setStatus(HttpServletResponse.SC_CREATED);
//            resp.getWriter().write("{\"message\":\"Actor created successfully.\"}");
//        } catch (JsonProcessingException e) {
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            resp.getWriter().write("{\"error\":\"Invalid JSON format.\"}");
//        } catch (Exception e) {
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
//        }
//    }
//}

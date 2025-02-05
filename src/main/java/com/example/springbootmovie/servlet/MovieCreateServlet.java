package com.example.springbootmovie.servlet;//package com.example.movierev.servlet;
//
//import com.example.movierev.dto.ActorDto;
//import com.example.movierev.dto.DirectorDto;
//import com.example.movierev.dto.GenreDto;
//import com.example.movierev.dto.MovieDto;
//import com.example.movierev.service.*;
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
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@WebServlet("/admin/tool/movies/create")
//@MultipartConfig(
//        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
//        maxFileSize = 1024 * 1024 * 10, // 10MB
//        maxRequestSize = 1024 * 1024 * 50 // 50MB
//)
//public class MovieCreateServlet extends HttpServlet {
//    private final MovieService movieService;
//    private final DirectorService directorService;
//    private final ActorService actorService;
//    private final GenreService genreService;
//    @Inject
//    public MovieCreateServlet(MovieService movieService, DirectorService directorService, ActorService actorService, GenreService genreService) {
//        this.movieService = movieService;
//        this.directorService = directorService;
//        this.actorService = actorService;
//        this.genreService = genreService;
//    }
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        List<DirectorDto> directorDtos = directorService.findAll();
//        List<GenreDto> genreDtos = genreService.findAll();
//        List<ActorDto> actorDtos = actorService.findAll();
//
//        req.setAttribute("actors", actorDtos);
//        req.setAttribute("genres", genreDtos);
//        req.setAttribute("directors", directorDtos);
//
//        req.getRequestDispatcher("/WEB-INF/movie-create.jsp").forward(req, resp);
//
//    }
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        try {
//            // Parse form parameters
//            String title = req.getParameter("title");
//            String description = req.getParameter("description");
//            String country = req.getParameter("country");
//            String directorName = req.getParameter("directorName");
//            String actors = req.getParameter("actors");
//            String genres = req.getParameter("genres");
//            String releaseDate = req.getParameter("releaseDate");
//            String lengthStr = req.getParameter("length");
//            String budgetStr = req.getParameter("budget");
//            String boxOfficeStr = req.getParameter("boxOffice");
//
//
//            Part posterPart = req.getPart("posterFile");
//            String posterPath = Paths.get(posterPart.getSubmittedFileName()).getFileName().toString();
//            String uploadPath = getServletContext().getRealPath("/uploads/posters");
//
//            // Ensure the upload directory exists
//            File uploadDir = new File(uploadPath);
//            if (!uploadDir.exists()) {
//                uploadDir.mkdirs();
//            }
//
//            // Save the file
//            String filePath = uploadPath + File.separator + posterPath;
//            posterPart.write(filePath);
//
//
//            Long length = Long.parseLong(lengthStr);
//            Long budget = Long.parseLong(budgetStr);
//            Long boxOffice = Long.parseLong(boxOfficeStr);
//
//            MovieDto movieDto = new MovieDto();
//            movieDto.setTitle(title);
//            movieDto.setDescription(description);
//            movieDto.setCountry(country);
//
//            DirectorDto directorDto = new DirectorDto();
//            directorDto.setName(directorName);
//            movieDto.setDirector(directorDto);
//
//            List<ActorDto> actorDtos = Arrays.stream(actors.split(","))
//                    .map(String::trim)
//                    .map(actorName -> {
//                        ActorDto actorDto = new ActorDto();
//                        actorDto.setName(actorName);
//                        return actorDto;
//                    })
//                    .collect(Collectors.toList());
//            movieDto.setActors(actorDtos);
//
//            List<GenreDto> genreDtos = Arrays.stream(genres.split(","))
//                    .map(String::trim)
//                    .map(genreName -> {
//                        GenreDto genreDto = new GenreDto();
//                        genreDto.setName(genreName);
//                        return genreDto;
//                    })
//                    .collect(Collectors.toList());
//            movieDto.setGenres(genreDtos);
//
//            movieDto.setReleaseDate(LocalDate.parse(releaseDate));
//            movieDto.setPosterPath(posterPath);
//            movieDto.setLength(length);
//            movieDto.setBudget(budget);
//            movieDto.setBoxOffice(boxOffice);
//
//            Validator validator;
//            try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
//                validator = factory.getValidator();
//            }
//            Set<ConstraintViolation<MovieDto>> violations = validator.validate(movieDto);
//
//            if (!violations.isEmpty()) {
//
//                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//
//                StringBuilder errorMessage = new StringBuilder();
//                for (ConstraintViolation<MovieDto> violation : violations) {
//                    errorMessage.append(violation.getPropertyPath())
//                            .append(": ")
//                            .append(violation.getMessage())
//                            .append("\n");
//                }
//
//                resp.setContentType("application/json");
//                resp.getWriter().write("{\"error\":\"" + errorMessage.toString().trim() + "\"}");
//                return;
//            }
//
//            movieService.save(movieDto);
//
//            // Redirect on success
//            resp.sendRedirect(req.getContextPath() + "/admin/tool/movies/create");
//
//        } catch (Exception e) {
//            req.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
//            req.getRequestDispatcher("/WEB-INF/movie-create.jsp").forward(req, resp);
//        }
//    }
//
//
//}

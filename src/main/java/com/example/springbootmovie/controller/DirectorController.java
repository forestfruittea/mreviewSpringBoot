package com.example.springbootmovie.controller;

import com.example.springbootmovie.model.dto.DirectorDto;
import com.example.springbootmovie.service.DirectorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class DirectorController {

    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }



    // 5. Display Director Details
    @GetMapping("/director")
    public String showDirectorDetails(@RequestParam(name = "id") Long id, Model model) {
        Optional<DirectorDto> directorDto = directorService.findById(id);

        if (directorDto.isPresent()) {
            model.addAttribute("director", directorDto.get());
            return "director-details"; // Corresponds to /WEB-INF/director-details.jsp
        } else {
            model.addAttribute("error", "Director not found.");
            return "error"; // Generic error page
        }
    }

    // 6. List All Directors
    @GetMapping("/directors")
    public String listDirectors(Model model) {
        List<DirectorDto> directors = directorService.findAll();
        model.addAttribute("directors", directors);
        return "directors"; // Corresponds to /WEB-INF/directors.jsp
    }
}

package com.example.springbootmovie.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LocaleController {
    @PostMapping("/locate")
    public String changeLocale(@RequestParam("lang") String language,
                               @RequestHeader(value = "Referer", required = false) String referer,
                               HttpSession session,
                               RedirectAttributes redirectAttributes,
                               @AuthenticationPrincipal UserDetails userDetails) {
        session.setAttribute("lang", language);

        String redirectUrl = (referer != null) ? referer : "/login";
        redirectAttributes.addAttribute("lang", language);

        return "redirect:" + redirectUrl;
    }
}

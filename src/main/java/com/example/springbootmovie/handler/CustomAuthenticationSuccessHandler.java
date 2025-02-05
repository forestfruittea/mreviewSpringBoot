package com.example.springbootmovie.handler;

import com.example.springbootmovie.model.dto.UserDto;
import com.example.springbootmovie.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("Authentication successful for user: {}", authentication.getName());

        String username = ((UserDetails) authentication.getPrincipal()).getUsername();

        Optional<UserDto> userOptional = userService.findByUsername(username);

        if (userOptional.isPresent()) {
            UserDto user = userOptional.get();

            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
        } else {
            // Handle case where user is not found (redirect, show error, etc.)
            request.setAttribute("error", "User not found");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }


        String redirectUrl = determineTargetUrl(authentication);
        log.debug("Redirecting to {}", redirectUrl);

        response.sendRedirect(redirectUrl);
    }

    private String determineTargetUrl(Authentication authentication) {
        GrantedAuthority authority = authentication.getAuthorities().iterator().next();

        if (authority.getAuthority().equals("ROLE_ADMIN")) {
            return "/admin/tool";   //for docker: /admin
        } else if (authority.getAuthority().equals("ROLE_MODERATOR")) {
            return "/admin/tool";
        } else {
            return "/movies";
        }
    }
}

package com.example.springbootmovie.service.impl;

import com.example.springbootmovie.model.entity.UserEntity;
import com.example.springbootmovie.repository.UserRepository;
import com.example.springbootmovie.service.CustomUserDetailsService;
import com.example.springbootmovie.wrapper.UserDetailsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService, CustomUserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + "not found"));
        return new UserDetailsWrapper(user);
    }
}

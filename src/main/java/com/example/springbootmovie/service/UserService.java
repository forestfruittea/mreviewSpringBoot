package com.example.springbootmovie.service;
import com.example.springbootmovie.model.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
public interface UserService {
    boolean registerUser(UserDto userDto);
    void delete(Long userId);
    Optional<UserDto> findById(Long id);
    Optional<UserDto> findByUsername(String username);
    List<UserDto> findAllSorted();
    //TODO UserDto update(UserDto userDto);
    //TODO List<UserDto> findAll();


}

package com.example.springbootmovie.service.impl;

import com.example.springbootmovie.model.dto.UserDto;
import com.example.springbootmovie.model.entity.UserEntity;
import com.example.springbootmovie.model.role.Role;
import com.example.springbootmovie.mapper.impl.UserMapper;
import com.example.springbootmovie.repository.UserRepository;
import com.example.springbootmovie.service.UserService;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public boolean registerUser(UserDto userDto) {
        setAvatarForRole(userDto.getRole(), userDto);
        UserEntity newUser = userMapper.toEntity(userDto);

        userRepository.save(newUser);
        return true;
    }
    public void setAvatarForRole(Role role, UserDto userDto) {
        String avatarPath;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("application");

        avatarPath = switch (role) {
            case ROLE_CUSTOMER -> resourceBundle.getString("base.avatar");
            case ROLE_MODERATOR -> resourceBundle.getString("moderator.avatar");
            case ROLE_ADMIN -> resourceBundle.getString("admin.avatar");
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        };

        userDto.setAvatarPath(avatarPath);
    }
    @Override
    @Transactional(readOnly = true)
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> findById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity.map(UserDto::of);
    }
//TODO
//    @Override
//    public List<UserDto> findAll() {
//        return null;
//    }
    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAllSorted() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .sorted((m1, m2) -> {
                    String title1 = m1.getUsername().toLowerCase();
                    String title2 = m2.getUsername().toLowerCase();
                    return title1.compareTo(title2);
                })
                .map(UserDto::of)
                .collect(Collectors.toList());

    }
    @Transactional(readOnly = true)
    @Override
    public Optional<UserDto> findByUsername(String username){
        return userRepository.findByUsername(username).map(UserDto::of);
    }
}

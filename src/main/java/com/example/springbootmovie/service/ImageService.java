package com.example.springbootmovie.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    String saveFile(MultipartFile file, String uploadDir) throws IOException;
}

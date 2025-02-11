package com.example.springbootmovie.service.impl;

import com.example.springbootmovie.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public String saveFile(MultipartFile file, String uploadDir) throws IOException {
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        String filePath = Paths.get(file.getOriginalFilename()).getFileName().toString();
        file.transferTo(new File(uploadPath, filePath));
        return filePath;
    }
}

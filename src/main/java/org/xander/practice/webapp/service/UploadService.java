package org.xander.practice.webapp.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadService {

    private final String uploadPath;

    public UploadService(@Value("${upload.path}") String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @SneakyThrows
    public Path saveIconToFile(MultipartFile file) {
        Path uploadDir = Paths.get(uploadPath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        String iconFilename = UUID.randomUUID().toString() + '_' + file.getOriginalFilename();
        Path iconFile = Paths.get(uploadPath, iconFilename);
        file.transferTo(iconFile);
        return iconFile;
    }

}

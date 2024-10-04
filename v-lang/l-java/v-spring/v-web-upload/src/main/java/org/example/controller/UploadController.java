package org.example.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@RestController
public class UploadController {

    @PostMapping("/upload")
    public void upload(@RequestParam("file") MultipartFile file) throws IOException {
        String substring = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
        String uuid = UUID.randomUUID().toString().replace("-", "");
        file.transferTo(new File("/Volumes/macat/Project/self/tmp/"+uuid+substring));
    }
}

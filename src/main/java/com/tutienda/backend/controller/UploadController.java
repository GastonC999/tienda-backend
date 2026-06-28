package com.tutienda.backend.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadController {

    private final Cloudinary cloudinary;

    @PostMapping
    public ResponseEntity<Map> upload(@RequestParam("file") MultipartFile file) throws Exception {
        Map result = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "folder", "moccana/products",
                        "transformation", "f_auto,q_auto,w_800"
                )
        );
        return ResponseEntity.ok(Map.of("url", result.get("secure_url")));
    }
}
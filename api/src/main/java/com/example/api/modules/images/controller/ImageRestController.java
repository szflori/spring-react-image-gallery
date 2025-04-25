package com.example.api.modules.images.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.api.modules.images.model.Image;
import com.example.api.modules.images.service.ImageService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/images", produces = "application/json")
public class ImageRestController {

    private final ImageService imageService;

    @Autowired
    public ImageRestController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createAction(@RequestBody MultipartFile body) throws IOException {
        imageService.create(body);
        return ResponseEntity.ok("Successfully created!");
    }

    @GetMapping
    public ResponseEntity<Collection<Image>> getFilterAction(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity
                .ok(imageService.getAll(userDetails));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> getOneAction(@PathVariable Long id) {
        return ResponseEntity.ok(imageService.getOne(id));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Collection<Image>> getAllAction() {
        return ResponseEntity
                .ok(imageService.getAll());
    }
}

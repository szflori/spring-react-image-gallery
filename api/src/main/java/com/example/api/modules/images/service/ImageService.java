package com.example.api.modules.images.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.api.enums.EAuthorityKeys;
import com.example.api.modules.images.model.Image;
import com.example.api.modules.images.persistence.ImageEntity;
import com.example.api.modules.images.persistence.ImageRepository;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    @Autowired
    public ImageService(ImageRepository imageRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    public void create(MultipartFile file) throws IOException {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setName(file.getOriginalFilename());
        imageEntity.setType(file.getContentType());
        imageEntity.setData(file.getBytes());
        imageRepository.save(imageEntity);
    }

    public Image getOne(Long id) {
        return imageMapper.toModel(imageRepository.findById(id).orElseThrow());
    }

    public Collection<Image> getAll() {
        return imageRepository.findAll().stream().map(e -> imageMapper.toModel(e)).toList();
    }

    public Collection<Image> getAll(UserDetails userDetails) {
        List<String> types = userDetails.getAuthorities().stream()
                .filter(item -> item.toString().startsWith(EAuthorityKeys.PERMISSION.getName()))
                .map(item -> item.toString().substring(EAuthorityKeys.PERMISSION.getName().length()))
                .collect(Collectors.toList());
        return imageRepository.findAll().stream().filter(i -> types.contains(i.getType()))
                .map(e -> imageMapper.toModel(e)).toList();
    }
}

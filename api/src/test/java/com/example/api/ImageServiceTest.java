package com.example.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.example.api.modules.images.model.Image;
import com.example.api.modules.images.persistence.ImageEntity;
import com.example.api.modules.images.persistence.ImageRepository;
import com.example.api.modules.images.service.ImageMapper;
import com.example.api.modules.images.service.ImageService;

public class ImageServiceTest {
    @Mock
    private ImageRepository imageRepository;

    @Mock
    private MultipartFile file;

    @Mock
    private ImageMapper imageMapper;

    @InjectMocks
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldSaveImage_WhenValidFileProvided() throws IOException {
        // Arrange
        String fileName = "testImage.jpg";
        String fileType = "image/jpeg";
        byte[] fileData = { 1, 2, 3, 4 };

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setName(fileName);
        imageEntity.setType(fileType);
        imageEntity.setData(fileData);

        when(file.getOriginalFilename()).thenReturn(fileName);
        when(file.getContentType()).thenReturn(fileType);
        when(file.getBytes()).thenReturn(fileData);

        // Act
        imageService.create(file);

        // Assert
        verify(imageRepository, times(1)).save(any(ImageEntity.class));
    }

    @Test
    void getOne_ShouldReturnImage_WhenImageExists() {
        // Arrange
        Long imageId = 1L;
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setId(imageId);

        Image image = new Image();

        when(imageRepository.findById(imageId)).thenReturn(Optional.of(imageEntity));
        when(imageMapper.toModel(imageEntity)).thenReturn(image);

        // Act
        Image result = imageService.getOne(imageId);

        // Assert
        assertNotNull(result);
        verify(imageRepository, times(1)).findById(imageId);
    }

    @Test
    void getOne_ShouldThrowException_WhenImageDoesNotExist() {
        // Arrange
        Long imageId = 1L;
        when(imageRepository.findById(imageId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> imageService.getOne(imageId));
        verify(imageRepository, times(1)).findById(imageId);
    }
}

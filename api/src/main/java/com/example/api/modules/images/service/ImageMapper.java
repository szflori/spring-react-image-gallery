package com.example.api.modules.images.service;

import org.springframework.stereotype.Service;

import com.example.api.modules.images.model.Image;
import com.example.api.modules.images.persistence.ImageEntity;

@Service
public class ImageMapper {

    public Image toModel(ImageEntity imageEntity) {
        if (imageEntity == null) {
            return null;
        }

        Image image = new Image();
        image.setId(imageEntity.getId());
        image.setName(imageEntity.getName());
        image.setType(imageEntity.getType());
        image.setData(imageEntity.getData());
        return image;
    }

}

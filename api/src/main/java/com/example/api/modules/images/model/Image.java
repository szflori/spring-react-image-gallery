package com.example.api.modules.images.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Image {
    private Long id;

    private String name;

    private String type;

    private byte[] data;
}

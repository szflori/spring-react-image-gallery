package com.example.api.modules.images.persistence;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "images")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Lob
    private byte[] data;

    private LocalDateTime uploadDate = LocalDateTime.now();

    public ImageEntity() {
    }

    public ImageEntity(String name, String type, byte[] data, LocalDateTime uploadDate) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.uploadDate = uploadDate;
    }
}

package com.example.api.modules.images.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    List<ImageEntity> findByTypeIn(List<String> types);
}

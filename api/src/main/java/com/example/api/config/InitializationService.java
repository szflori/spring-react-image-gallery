package com.example.api.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.api.enums.EPermission;
import com.example.api.enums.ERole;
import com.example.api.modules.images.persistence.ImageEntity;
import com.example.api.modules.images.persistence.ImageRepository;
import com.example.api.modules.users.persistence.UserEntity;
import com.example.api.modules.users.persistence.UserRepository;
import com.example.api.utils.ImageMetadata;

@Service
public class InitializationService implements CommandLineRunner {

  private final ImageRepository imageRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public InitializationService(ImageRepository imageRepository, UserRepository userRepository,
      PasswordEncoder passwordEncoder) {
    this.imageRepository = imageRepository;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void run(String... args) throws Exception {
    initializeDefaultUser();
    initializeDefaultImage();
  }

  public void initializeDefaultImage() {
    if (imageRepository.count() == 0) {
      List<ImageMetadata> images = Arrays.asList(
          new ImageMetadata("bhautik-patel.jpg", EPermission.JPEG.getName(),
              "src/main/resources/static/bhautik-patel.jpg"),
          new ImageMetadata("christian-boragine.jpg", EPermission.JPEG.getName(),
              "src/main/resources/static/christian-boragine.jpg"),
          new ImageMetadata("d5-render.jpg", EPermission.JPEG.getName(),
              "src/main/resources/static/d5-render.jpg"),
          new ImageMetadata("milad-fakurian.jpg", EPermission.JPEG.getName(),
              "src/main/resources/static/milad-fakurian.jpg"),
          new ImageMetadata("prydumano-design.jpg", EPermission.JPEG.getName(),
              "src/main/resources/static/prydumano-design.jpg"),
          new ImageMetadata("steve-johnson.jpg", EPermission.JPEG.getName(),
              "src/main/resources/static/steve-johnson.jpg"),
          new ImageMetadata("tilak-baloni.jpg", EPermission.JPEG.getName(),
              "src/main/resources/static/tilak-baloni.jpg"),
          new ImageMetadata("yordan-stoyanov.jpg", EPermission.JPEG.getName(),
              "src/main/resources/static/yordan-stoyanov.jpg"),
          new ImageMetadata("gift.png", EPermission.PNG.getName(),
              "src/main/resources/static/gift.png"),
          new ImageMetadata("hand.png", EPermission.PNG.getName(),
              "src/main/resources/static/hand.png"),
          new ImageMetadata("rock.png", EPermission.PNG.getName(),
              "src/main/resources/static/rock.png"));

      images.forEach(image -> {
        try {
          Path imagePath = Paths.get(image.getPath());
          byte[] imageData = Files.readAllBytes(imagePath);

          ImageEntity imageEntity = new ImageEntity(
              image.getName(),
              image.getType(),
              imageData,
              LocalDateTime.now());

          imageRepository.save(imageEntity);
          System.out.println("Initialized: " + image.getName());
        } catch (Exception e) {
          System.err.println("Failed to initialize image: " + image.getName()
              + ", error: " + e.getMessage());
        }
      });
    } else {
      System.out.println("Images table is not empty. No initialization required.");
    }
  }

  public void initializeDefaultUser() {
    if (userRepository.count() == 0) {
      UserEntity adminUser = new UserEntity(
          "admin",
          passwordEncoder.encode("asdASD123"),
          "admin@email.com",
          Set.of(ERole.ADMIN),
          Set.of(EPermission.GIF, EPermission.JPEG, EPermission.PNG));

      UserEntity user1 = new UserEntity(
          "user1",
          passwordEncoder.encode("asdASD123"),
          "user1@email.com",
          Set.of(ERole.USER), Set.of(EPermission.JPEG));

      UserEntity user2 = new UserEntity(
          "user2",
          passwordEncoder.encode("asdASD123"),
          "user2@email.com",
          Set.of(ERole.USER), Set.of(EPermission.PNG));

      userRepository.save(adminUser);
      userRepository.save(user1);
      userRepository.save(user2);

      System.out.println("Default users initialized.");
    } else {
      System.out.println("Users already exist. No initialization required.");

    }
  }
}

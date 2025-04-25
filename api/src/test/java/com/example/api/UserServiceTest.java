package com.example.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.api.enums.EPermission;
import com.example.api.enums.ERole;
import com.example.api.modules.users.model.User;
import com.example.api.modules.users.model.requests.UserUpdateRequestDto;
import com.example.api.modules.users.persistence.UserEntity;
import com.example.api.modules.users.persistence.UserRepository;
import com.example.api.modules.users.service.UserMapper;
import com.example.api.modules.users.service.UserService;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ShouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(TestData.userEntity()));
        when(userMapper.convertToModel(TestData.userEntity())).thenReturn(TestData.user());

        assertEquals(1, userService.getAll().size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getOne_ShouldReturnUser_WhenUserExists() {
        // Arrange
        Long userId = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userMapper.convertToModel(userEntity)).thenReturn(user);

        // Act
        User result = userService.getOne(userId);

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getOne_ShouldThrowException_WhenUserDoesNotExist() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.getOne(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void update_ShouldUpdateUser_WhenValidDataIsProvided() {
        // Arrange
        Long userId = 1L;
        UserUpdateRequestDto updateRequest = new UserUpdateRequestDto();
        updateRequest.setUsername("newUsername");
        updateRequest.setEmail("newEmail@example.com");
        updateRequest.setRoles(Set.of("USER"));
        updateRequest.setPermissions(Set.of("JPEG"));

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setUsername("oldUsername");
        userEntity.setEmail("oldEmail@example.com");

        UserEntity updatedUserEntity = new UserEntity();
        updatedUserEntity.setId(userId);
        updatedUserEntity.setUsername("newUsername");
        updatedUserEntity.setEmail("newEmail@example.com");
        updatedUserEntity.setRoles(Set.of(ERole.USER));
        updatedUserEntity.setPermissions(Set.of(EPermission.JPEG));

        User updatedUser = new User();
        updatedUser.setUsername(updatedUserEntity.getUsername());

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUserEntity);
        when(userMapper.convertToModel(updatedUserEntity)).thenReturn(updatedUser);

        // Act
        User result = userService.update(userId, updateRequest);

        // Assert
        assertNotNull(result);
        assertEquals(updatedUser.getUsername(), result.getUsername());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(userEntity);
    }
}

package com.example.api.modules.users.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.enums.EPermission;
import com.example.api.enums.ERole;
import com.example.api.modules.users.model.User;
import com.example.api.modules.users.model.requests.UserUpdateRequestDto;
import com.example.api.modules.users.persistence.UserEntity;
import com.example.api.modules.users.persistence.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Collection<UserEntity> getAll() {
        return userRepository.findAll();
    }

    public User getOne(Long id) {
        return userMapper.convertToModel(userRepository.findById(id).orElseThrow());
    }

    public User update(Long id, UserUpdateRequestDto body) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow();

        if (body.getUsername() != null) {
            userEntity.setUsername(body.getUsername());
        }

        if (body.getEmail() != null) {
            userEntity.setEmail(body.getEmail());
        }

        if (body.getRoles() != null) {
            userEntity.setRoles(body.getRoles().stream().map(ERole::valueOf).collect(Collectors.toSet()));
        }

        if (body.getPermissions() != null) {
            userEntity.setPermissions(
                    body.getPermissions().stream().map(EPermission::valueOf).collect(Collectors.toSet()));
        }

        UserEntity updatedUserEntity = userRepository.save(userEntity);
        return userMapper.convertToModel(updatedUserEntity);
    }
}

package com.example.api.modules.users.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.api.modules.users.model.User;
import com.example.api.modules.users.persistence.UserEntity;

@Service
public class UserMapper {

    public User convertToModel(UserEntity userEntity) {
        User user = new User();
        user.setUsername(userEntity.getUsername());
        user.setEmail(userEntity.getEmail());
        user.setRoles(userEntity.getRoles().stream()
                .map(Enum::name)
                .collect(Collectors.toList()));
        user.setPermissions(userEntity.getPermissions().stream()
                .map(Enum::name)
                .collect(Collectors.toList()));

        return user;
    }
}

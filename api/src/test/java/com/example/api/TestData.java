package com.example.api;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.api.enums.EPermission;
import com.example.api.enums.ERole;
import com.example.api.modules.users.model.User;
import com.example.api.modules.users.persistence.UserEntity;

public class TestData {

    static Set<ERole> roles() {
        Set<ERole> roles = new HashSet<>();
        roles.add(ERole.USER);
        return roles;
    }

    static Set<EPermission> permissions() {
        Set<EPermission> permissions = new HashSet<>();
        permissions.add(EPermission.JPEG);
        return permissions;
    }

    public static UserEntity userEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("test");
        userEntity.setEmail("test@email.com");
        userEntity.setPassword("test");
        userEntity.setRoles(roles());
        userEntity.setPermissions(permissions());
        return userEntity;
    }

    public static User user() {
        User user = new User();
        user.setUsername("test");
        user.setEmail("test@email.com");
        user.setRoles(roles().stream().map(Enum::name).collect(Collectors.toList()));
        user.setPermissions(permissions().stream().map(Enum::name).collect(Collectors.toList()));
        return user;
    }
}

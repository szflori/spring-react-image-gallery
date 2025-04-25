package com.example.api.modules.auth.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.api.enums.EPermission;
import com.example.api.enums.ERole;
import com.example.api.modules.auth.model.AuthUser;
import com.example.api.modules.auth.model.requests.AuthLoginRequest;
import com.example.api.modules.auth.model.requests.AuthSignupRequest;
import com.example.api.modules.users.model.User;
import com.example.api.modules.users.persistence.UserEntity;
import com.example.api.modules.users.persistence.UserRepository;
import com.example.api.modules.users.service.UserMapper;

@Service
public class AuthUserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public AuthUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public Boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    public Boolean isUsernameExist(String username) {
        return userRepository.existsByUsername(username);
    }

    public AuthUser loadUserByEmailOrUsername(String emailOrUsername) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(emailOrUsername)
                .or(() -> userRepository.findByUsername(emailOrUsername))
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with email or username: " + emailOrUsername));
        return new AuthUser(user);
    }

    public void registerUser(AuthSignupRequest body) {
        if (isEmailExist(body.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        if (isUsernameExist(body.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        Set<ERole> roles = new HashSet<>();
        roles.add(ERole.USER);

        Set<EPermission> permissions = new HashSet<>();
        permissions.add(EPermission.JPEG);

        UserEntity entity = new UserEntity(body.getUsername(), passwordEncoder.encode(body.getPassword()),
                body.getEmail(), roles, permissions);
        userRepository.save(entity);
    }

    public AuthUser login(AuthLoginRequest body) {
        AuthUser user = loadUserByEmailOrUsername(body.getEmail());

        if (!passwordEncoder.matches(body.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }

    public User getMe(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow();
        return userMapper.convertToModel(userEntity);
    }
}

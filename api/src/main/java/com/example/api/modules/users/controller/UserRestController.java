package com.example.api.modules.users.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.modules.users.model.User;
import com.example.api.modules.users.model.requests.UserUpdateRequestDto;
import com.example.api.modules.users.persistence.UserEntity;
import com.example.api.modules.users.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/users", produces = "application/json")
@PreAuthorize("hasRole('ADMIN')")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Collection<UserEntity>> getAllAction() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getOneAction(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getOne(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateAction(@PathVariable Long id, @RequestBody UserUpdateRequestDto body) {
        return ResponseEntity.ok(userService.update(id, body));
    }
}

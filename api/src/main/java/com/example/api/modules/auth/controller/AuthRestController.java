package com.example.api.modules.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.config.JwtService;
import com.example.api.modules.auth.model.AuthUser;
import com.example.api.modules.auth.model.requests.AuthLoginRequest;
import com.example.api.modules.auth.model.requests.AuthSignupRequest;
import com.example.api.modules.auth.model.responses.AuthResponse;
import com.example.api.modules.auth.service.AuthUserDetailsService;
import com.example.api.modules.users.model.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/auth", produces = "application/json")
public class AuthRestController {

    private final AuthUserDetailsService authUserDetailsService;
    private final JwtService jwtService;

    @Autowired
    public AuthRestController(AuthUserDetailsService authUserDetailsService, JwtService jwtService) {
        this.authUserDetailsService = authUserDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginAction(@Valid @RequestBody AuthLoginRequest body) {
        AuthUser authUser = authUserDetailsService.login(body);

        AuthResponse response = new AuthResponse();
        response.setAccessToken(jwtService.generateAccessToken(authUser));

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signupAction(@Valid @RequestBody AuthSignupRequest body) {
        authUserDetailsService.registerUser(body);
        return ResponseEntity.ok().body("User registered successfully!");
    }

    @GetMapping("/me")
    public ResponseEntity<User> getMeAction(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(authUserDetailsService.getMe(userDetails.getUsername()));
    }

}
package com.example.api.modules.auth.model.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthSignupRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank
    private String password;
}

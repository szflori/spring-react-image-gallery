package com.example.api.modules.auth.model.requests;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthLoginRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}

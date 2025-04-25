package com.example.api.modules.users.model.requests;

import java.util.Collection;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {

    private String username;

    @Email
    private String email;
    private Collection<String> roles;
    private Collection<String> permissions;
}

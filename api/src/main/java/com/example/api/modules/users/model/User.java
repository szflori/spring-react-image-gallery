package com.example.api.modules.users.model;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String username;
    private String email;
    private Collection<String> roles;
    private Collection<String> permissions;
}

package com.example.api.enums;

public enum ERole {
    USER("USER"),
    ADMIN("ADMIN");

    private final String name;

    ERole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

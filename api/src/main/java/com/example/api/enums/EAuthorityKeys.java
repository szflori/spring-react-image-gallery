package com.example.api.enums;

public enum EAuthorityKeys {
    ROLE("ROLE_"),
    PERMISSION("PERMISSION_");

    private final String name;

    EAuthorityKeys(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

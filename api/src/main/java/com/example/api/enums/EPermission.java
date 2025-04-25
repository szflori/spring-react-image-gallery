package com.example.api.enums;

public enum EPermission {
    PNG("PNG"),
    JPEG("JPEG"),
    GIF("GIF");

    private final String name;

    EPermission(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

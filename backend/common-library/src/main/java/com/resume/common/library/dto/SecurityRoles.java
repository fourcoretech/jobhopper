package com.resume.common.library.dto;

public enum SecurityRoles {
    USER("USER"),
    ADMIN("ADMIN");
    private final String role;

    SecurityRoles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}

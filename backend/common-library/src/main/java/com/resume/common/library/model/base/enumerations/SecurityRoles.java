package com.resume.common.library.model.base.enumerations;

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

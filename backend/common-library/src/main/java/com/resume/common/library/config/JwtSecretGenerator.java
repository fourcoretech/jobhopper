package com.resume.common.library.config;

import java.security.SecureRandom;
import java.util.Base64;

public class JwtSecretGenerator {
    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] secretBytes = new byte[32];
        secureRandom.nextBytes(secretBytes);
        String jwtSecret = Base64.getEncoder().encodeToString(secretBytes);
        System.out.println("Your JWT Secret: " + jwtSecret);
    }
}

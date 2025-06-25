package com.auth.controller;

import com.auth.model.dto.AuthenticationRequest;
import com.auth.model.dto.UserDTO;
import com.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {
    @Autowired
    private final AuthService authService;

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO user) {
        log.info("Creating new user...");
        return ResponseEntity.ok(authService.createNewUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthenticationRequest authRequest) {
        log.info("Authentication started...");
        return ResponseEntity.ok(authService.authenticateUser(authRequest.getUsername(), authRequest.getPassword()));
    }
}

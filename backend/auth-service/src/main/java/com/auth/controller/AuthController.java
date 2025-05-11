package com.auth.controller;

import com.auth.dto.AuthenticationRequest;
import com.auth.dto.AuthenticationResponse;
import com.auth.dto.UserDTO;
import com.auth.service.AuthService;
import com.resume.common.library.dto.BaseResponse;
import com.resume.common.library.dto.SecurityRoles;
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

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authRequest) {
        log.info("Authentication started...");
        String token = authService.generateAuthToken(authRequest.getUsername(), authRequest.getPassword());
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @PostMapping(value = "/user/register")
    public ResponseEntity<BaseResponse> registerUser(@RequestBody UserDTO user) {
        log.info("Creating new user...");
        return ResponseEntity.ok(authService.createNewUser(user, SecurityRoles.USER.getRole()));
    }

    @PostMapping(value = "/admin/register")
    public ResponseEntity<BaseResponse> registerAdmin(@RequestBody UserDTO user) {
        log.info("Creating new admin...");
        return ResponseEntity.ok(authService.createNewUser(user, SecurityRoles.ADMIN.getRole()));
    }
}

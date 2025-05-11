package com.auth.service;

import com.auth.dto.UserDTO;
import com.auth.dto.UserProfile;
import com.auth.entity.User;
import com.auth.exception.AuthException;
import com.auth.repository.UserRepository;
import com.resume.common.library.config.JwtUtil;
import com.resume.common.library.dto.BaseResponse;
import com.resume.common.library.dto.SecurityRoles;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public BaseResponse createNewUser(UserDTO userDTO, String role) {
        log.info("New user {} created.", userDTO.getUsername());
        User existingUser = userRepository.findByUsername(userDTO.getUsername());
        if (existingUser != null) {
            return new BaseResponse("User already exists.", new UserProfile(existingUser), false);
        }
        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPhone(userDTO.getPhone());
        newUser.setEmail(userDTO.getEmail());
        newUser.setUsername(userDTO.getUsername().toLowerCase().trim());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setRole(setRole(role));
        newUser.setCreateTimeStamp(LocalDateTime.now().toString());
        userRepository.save(newUser);
        return new BaseResponse("User created.", new UserProfile(newUser), true);
    }

    private String setRole(String role) {
        if ("ADMIN".equalsIgnoreCase(role)) {
            return SecurityRoles.ADMIN.getRole();
        }
        return SecurityRoles.USER.getRole();
    }

    public String generateAuthToken(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            log.info("Authentication successful for user: {}", username);
        } catch (Exception e) {
            throw new AuthException(String.format("Invalid username or password. %s", e.getMessage()), HttpStatusCode.valueOf(401));
        }
        UserProfile userProfile = (UserProfile) userDetailsService.loadUserByUsername(username);
        return jwtUtil.generateToken(userProfile.getUsername(), userProfile.getAuthorities().stream().findFirst().toString());
    }
}

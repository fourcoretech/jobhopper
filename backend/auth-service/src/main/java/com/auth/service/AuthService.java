package com.auth.service;

import com.auth.entity.User;
import com.auth.exception.AuthException;
import com.auth.model.dto.UserDTO;
import com.auth.model.dto.UserProfile;
import com.auth.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resume.common.library.config.JwtUtil;
import com.resume.common.library.model.base.enumerations.SecurityRoles;
import com.resume.common.library.model.notify.dto.NotificationEvent;
import com.resume.common.library.model.notify.enumerations.EventTypes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final ProducerService producerService;
    private final ObjectMapper objectMapper;

    @Transactional
    public String createNewUser(UserDTO userDTO) {
        try {
            String username = userDTO.getUsername().toLowerCase().trim();
            if (userRepository.findByUsername(username) != null) {
                return String.format("User %s already exists.", userDTO.getUsername());
            }

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPhone(userDTO.getPhone());
            newUser.setEmail(userDTO.getEmail());
            newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            newUser.setRole(setRole(userDTO.getRole()));
            newUser.setCreateTimeStamp(LocalDateTime.now().toString());
            userRepository.save(newUser);
            log.info("New user {} created.", newUser.getUsername());

            producerService.sendNotificationEvent(NotificationEvent.builder()
                    .eventType(EventTypes.NEW_USER)
                    .recipientEmail(newUser.getEmail())
                    .summary(String.format(
                            "User %s created successfully.%n%nProfile Summary:%n%s",
                            newUser.getUsername(),
                            objectMapper.writeValueAsString(new UserProfile(newUser))
                    ))
                    .build());
            return String.format("User %s created successfully.", newUser.getUsername());
        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage(), e);
            throw new AuthException("Failed to create user. " + e.getMessage(), HttpStatusCode.valueOf(500));
        }
    }

    public String authenticateUser(String username, String password) {
        UserProfile userProfile = (UserProfile) userDetailsService.loadUserByUsername(username);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userProfile.getUsername(), password));
            log.info("Authentication successful for user: {}", username);
        } catch (Exception e) {
            producerService.sendNotificationEvent(NotificationEvent.builder()
                    .eventType(EventTypes.FAILED_LOGIN)
                    .recipientEmail(userProfile.getEmail())
                    .summary("There was a failed login attempt for username " + userProfile.getUsername() + ". Please check your credentials.")
                    .build());
            log.warn("Authentication failed for user {}: {}", username, e.getMessage());
            throw new AuthException("Invalid username or password.", HttpStatusCode.valueOf(401));
        }
        return generateToken(userProfile);
    }

    private String generateToken(UserProfile userProfile) {
        try {
            String role = userProfile.getAuthorities().stream()
                    .findFirst()
                    .map(Object::toString)
                    .orElse(SecurityRoles.USER.getRole());
            String token = jwtUtil.generateToken(userProfile.getUsername(), role);
            producerService.sendNotificationEvent(NotificationEvent.builder()
                    .eventType(EventTypes.USER_LOGIN)
                    .recipientEmail(userProfile.getEmail())
                    .summary("User " + userProfile.getUsername() + " logged in successfully.")
                    .build());
            return token;
        } catch (Exception e) {
            log.error("Error generating token for user {}: {}", userProfile.getUsername(), e.getMessage(), e);
            throw new AuthException("Unable to verify user. Please try again.", HttpStatusCode.valueOf(500));
        }
    }

    private String setRole(String role) {
        if (SecurityRoles.ADMIN.getRole().equalsIgnoreCase(role)) {
            return SecurityRoles.ADMIN.getRole();
        }
        return SecurityRoles.USER.getRole();
    }
}

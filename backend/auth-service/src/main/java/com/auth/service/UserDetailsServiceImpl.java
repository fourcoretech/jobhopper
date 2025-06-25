package com.auth.service;

import com.auth.entity.User;
import com.auth.exception.AuthException;
import com.auth.model.dto.UserProfile;
import com.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            log.info("Querying DB for {}... ", username);
            User user = userRepository.findByUsername(username);
            if (username.isBlank() || user == null) {
                log.error("Failed to load user. {} was not found in the database", username);
                throw new AuthException(String.format("User not found with username: %s", username), HttpStatusCode.valueOf(404));
            }
            log.info("User found. Total users in table: {}", userRepository.findAll().size());
            return new UserProfile(user);
        } catch (Exception e) {
            log.error("Error loading user by username: {}", e.getMessage());
            throw new AuthException(String.format("User not found with username: %s", username), HttpStatusCode.valueOf(404));
        }
    }
}

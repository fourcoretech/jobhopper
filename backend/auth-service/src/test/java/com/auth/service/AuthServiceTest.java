package com.auth.service;

import com.auth.dto.AuthenticationRequest;
import com.auth.dto.UserDTO;
import com.auth.dto.UserProfile;
import com.auth.entity.User;
import com.auth.repository.UserRepository;
import com.resume.common.library.config.JwtUtil;
import com.resume.common.library.dto.BaseResponse;
import com.resume.common.library.dto.SecurityRoles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateAuthToken_Success() {
        String username = "user@example.com";
        String password = "password123";
        Authentication authentication = mock(Authentication.class);
        UserProfile userProfile = UserProfile.builder()
                .username(username)
                .password(password)
                .role("USER")
                .createTimeStamp(LocalDateTime.now().toString())
                .email("test@email.com")
                .build();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userProfile);
        when(jwtUtil.generateToken(eq(username), anyString())).thenReturn("mocked-jwt-token");

        String token = authService.generateAuthToken(username, password);

        assertNotNull(token, "Token should not be null");
        assertEquals("mocked-jwt-token", token);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService, times(1)).loadUserByUsername(username);
        verify(jwtUtil, times(1)).generateToken(eq(username), anyString());
    }

    @Test
    void testGenerateAuthTokenForAdmin_Success() {
        String username = "user@example.com";
        String password = "password123";
        Authentication authentication = mock(Authentication.class);
        UserProfile userProfile = UserProfile.builder()
                .username(username)
                .password(password)
                .role("ADMIN")
                .createTimeStamp(LocalDateTime.now().toString())
                .email("test@email.com")
                .build();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userProfile);
        when(jwtUtil.generateToken(eq(username), anyString())).thenReturn("mocked-jwt-token");

        String token = authService.generateAuthToken(username, password);

        assertNotNull(token, "Token should not be null");
        assertEquals("mocked-jwt-token", token);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService, times(1)).loadUserByUsername(username);
        verify(jwtUtil, times(1)).generateToken(eq(username), anyString());
    }

    @Test
    void testGenerateAuthToken_InvalidCredentials() {
        AuthenticationRequest authRequest = new AuthenticationRequest("user@example.com", "wrongpassword");
        UserDetails userDetails = UserProfile.builder().username(authRequest.getUsername()).password(authRequest.getPassword()).build();

        when(userDetailsService.loadUserByUsername(authRequest.getUsername())).thenReturn(userDetails);
        when(passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> authService.generateAuthToken(authRequest.getUsername(), authRequest.getPassword()));
        verify(userDetailsService, times(1)).loadUserByUsername(authRequest.getUsername());
    }

    @Test
    void testGenerateAuthToken_AuthManagerFailure() {
        AuthenticationRequest authRequest = new AuthenticationRequest("user@example.com", "wrongpassword");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Authentication failed"));
        assertThrows(RuntimeException.class, () -> authService.generateAuthToken(authRequest.getUsername(), authRequest.getPassword()));
    }

    @Test
    void testCreateNewUser_Success() {
        UserDTO userDTO = new UserDTO("user", "password", "test", "user", "user@example.com", "281-330-8004");
        User user = new User(1L, userDTO.getUsername(), "encodedPassword", SecurityRoles.USER.getRole(), userDTO.getEmail(), userDTO.getPhone(), LocalDateTime.now().toString());
        when(userRepository.save(any(User.class))).thenReturn(user);
        BaseResponse response = authService.createNewUser(userDTO, SecurityRoles.USER.getRole());
        assertNotNull(response);
        assertTrue(response.isSuccess());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateNewAdmin_Success() {
        UserDTO userDTO = new UserDTO("user", "password", "test", "user", "user@example.com", "281-330-8004");
        User user = new User(1L, userDTO.getUsername(), "encodedPassword", SecurityRoles.ADMIN.getRole(), userDTO.getEmail(), userDTO.getPhone(), LocalDateTime.now().toString());
        when(userRepository.save(any(User.class))).thenReturn(user);
        BaseResponse response = authService.createNewUser(userDTO, SecurityRoles.ADMIN.getRole());
        assertNotNull(response);
        assertTrue(response.isSuccess());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateNewUser_Failure() {
        UserDTO userDTO = new UserDTO("user", "password", "test", "user", "user@example.com", "281-330-8004");

        when(passwordEncoder.encode(userDTO.getPassword())).thenThrow(new RuntimeException("Encoding error"));

        assertThrows(RuntimeException.class, () -> authService.createNewUser(userDTO, SecurityRoles.USER.getRole()));
        verify(passwordEncoder, times(1)).encode(userDTO.getPassword());
    }

    @Test
    void testCreateNewAdmin_ExistingUser() {
        UserDTO userDTO = new UserDTO("user", "password", "test", "user", "user@example.com", "281-330-8004");
        User user = new User(1L, userDTO.getUsername(), "encodedPassword", SecurityRoles.ADMIN.getRole(), userDTO.getEmail(), userDTO.getPhone(), LocalDateTime.now().toString());
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(user);
        BaseResponse response = authService.createNewUser(userDTO, SecurityRoles.ADMIN.getRole());
        assertNotNull(response);
        assertFalse(response.isSuccess());
        verify(userRepository, times(1)).findByUsername(anyString());
    }
}

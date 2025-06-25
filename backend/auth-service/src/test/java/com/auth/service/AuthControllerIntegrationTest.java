package com.auth.service;

import com.auth.exception.AuthException;
import com.auth.model.dto.AuthenticationRequest;
import com.auth.model.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AuthControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockitoBean
    private AuthService authService;
    @MockitoBean
    private ProducerService producerService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAuthenticate_Success() throws Exception {
        AuthenticationRequest authRequest = new AuthenticationRequest("test", "password");
        String authResponse = "mocked-jwt-token";

        when(authService.authenticateUser(authRequest.getUsername(), authRequest.getPassword()))
                .thenReturn(authResponse);

        mockMvc.perform(post("/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-jwt-token"));

        verify(authService, times(1)).authenticateUser(authRequest.getUsername(), authRequest.getPassword());
    }

    @Test
    void testAuthenticate_Failure() throws Exception {
        AuthenticationRequest authRequest = new AuthenticationRequest("test", "wrong-password");
        when(authService.authenticateUser(authRequest.getUsername(), authRequest.getPassword()))
                .thenThrow(new AuthException("Invalid username or password", HttpStatusCode.valueOf(401)));
        mockMvc.perform(post("/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isUnauthorized());
        verify(authService, times(1)).authenticateUser(authRequest.getUsername(), authRequest.getPassword());
    }

    @Test
    void testRegisterUser_Success() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .username("user")
                .password("password")
                .email("user@example.com")
                .role("USER")
                .build();

        when(authService.createNewUser(any(UserDTO.class)))
                .thenReturn("User user created.");

        mockMvc.perform(post("/auth/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User created."))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void testRegisterAdmin_Success() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .username("admin")
                .password("password")
                .email("admin@example.com")
                .role("ADMIN")
                .build();
        when(authService.createNewUser(any(UserDTO.class)))
                .thenReturn("User admin created.");
        mockMvc.perform(post("/auth/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Admin created."))
                .andExpect(jsonPath("$.success").value(true));
    }
}

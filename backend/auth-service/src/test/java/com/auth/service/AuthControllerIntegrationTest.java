package com.auth.service;

import com.auth.dto.AuthenticationRequest;
import com.auth.dto.AuthenticationResponse;
import com.auth.dto.UserDTO;
import com.auth.exception.AuthException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resume.common.library.dto.BaseResponse;
import com.resume.common.library.dto.SecurityRoles;
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
        AuthenticationResponse authResponse = new AuthenticationResponse("mocked-jwt-token");

        when(authService.generateAuthToken(authRequest.getUsername(), authRequest.getPassword()))
                .thenReturn(authResponse.getToken());

        mockMvc.perform(post("/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-jwt-token"));

        verify(authService, times(1)).generateAuthToken(authRequest.getUsername(), authRequest.getPassword());
    }

    @Test
    void testAuthenticate_Failure() throws Exception {
        AuthenticationRequest authRequest = new AuthenticationRequest("test", "wrong-password");
        when(authService.generateAuthToken(authRequest.getUsername(), authRequest.getPassword()))
                .thenThrow(new AuthException("Invalid username or password", HttpStatusCode.valueOf(401)));
        mockMvc.perform(post("/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isUnauthorized());
        verify(authService, times(1)).generateAuthToken(authRequest.getUsername(), authRequest.getPassword());
    }

    @Test
    void testRegisterUser_Success() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .username("user")
                .password("password")
                .email("user@example.com")
                .build();
        BaseResponse baseResponse = new BaseResponse("User created.", null, true);

        when(authService.createNewUser(any(UserDTO.class), eq(SecurityRoles.USER.getRole())))
                .thenReturn(baseResponse);

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
                .build();
        BaseResponse baseResponse = new BaseResponse("Admin created.", null, true);

        when(authService.createNewUser(any(UserDTO.class), eq(SecurityRoles.ADMIN.getRole())))
                .thenReturn(baseResponse);

        mockMvc.perform(post("/auth/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Admin created."))
                .andExpect(jsonPath("$.success").value(true));
    }
}

package com.reptrack.api.security.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService service;

    @InjectMocks
    private AuthenticationController authenticationController;

    private RegisterRequest registerRequest;
    private AuthenticationRequest authenticationRequest;
    private AuthenticationResponse authenticationResponse;

    @BeforeEach
    void setUp() {
        registerRequest = RegisterRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john@example.com")
                .password("password123")
                .build();

        authenticationRequest = AuthenticationRequest.builder()
                .email("john@example.com")
                .password("password123")
                .build();

        authenticationResponse = AuthenticationResponse.builder()
                .token("jwt.token.here")
                .build();
    }

    @Test
    void register_ShouldReturnAuthenticationResponse() {
        when(service.register(any(RegisterRequest.class))).thenReturn(authenticationResponse);

        ResponseEntity<AuthenticationResponse> response = authenticationController.register(registerRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(authenticationResponse, response.getBody());
        verify(service, times(1)).register(registerRequest);
    }

    @Test
    void authenticate_ShouldReturnAuthenticationResponse() {
        when(service.authenticate(any(AuthenticationRequest.class))).thenReturn(authenticationResponse);

        ResponseEntity<AuthenticationResponse> response = authenticationController.register(authenticationRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(authenticationResponse, response.getBody());
        verify(service, times(1)).authenticate(authenticationRequest);
    }
} 
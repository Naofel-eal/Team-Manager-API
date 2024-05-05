package com.naofeleal.teammanager.infrastructure.ihm.controller;

import com.naofeleal.teammanager.core.application.exception.authentication.AlreadyUsedEmailException;
import com.naofeleal.teammanager.core.application.usecase.authentication.dto.RegisterUserDTO;
import com.naofeleal.teammanager.core.application.usecase.authentication.interfaces.ILoginUseCase;
import com.naofeleal.teammanager.core.application.usecase.authentication.interfaces.IRegisterUseCase;
import com.naofeleal.teammanager.infrastructure.ihm.mapper.authentication.IRegisterMapper;
import com.naofeleal.teammanager.infrastructure.ihm.model.request.AuthenticationRequest;
import com.naofeleal.teammanager.infrastructure.ihm.model.request.RegisterRequest;
import com.naofeleal.teammanager.infrastructure.ihm.model.response.AuthenticationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    @Mock
    private IRegisterUseCase registerUseCase;

    @Mock
    private ILoginUseCase loginUseCase;

    @Mock
    private IRegisterMapper registerMapper;

    @InjectMocks
    private AuthController authController;

    @Test
    void testRegister_Success() {
        RegisterRequest registerRequest = new RegisterRequest("newuser@example.com", "password123", "John", "Doe");
        RegisterUserDTO registerUserDTO = new RegisterUserDTO("newuser@example.com", "password123", "John", "Doe");

        when(registerMapper.toDomain(any(RegisterRequest.class))).thenReturn(registerUserDTO);
        doNothing().when(registerUseCase).execute(any(RegisterUserDTO.class));

        ResponseEntity<Void> response = authController.register(registerRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(registerMapper, times(1)).toDomain(registerRequest);
        verify(registerUseCase, times(1)).execute(registerUserDTO);
    }

    @Test
    void testRegister_Failure() {
        RegisterRequest registerRequest = new RegisterRequest("newuser@example.com", "password123", "John", "Doe");
        RegisterUserDTO registerUserDTO = new RegisterUserDTO("newuser@example.com", "password123", "John", "Doe");

        when(registerMapper.toDomain(any(RegisterRequest.class))).thenReturn(registerUserDTO);
        doThrow(AlreadyUsedEmailException.class).when(registerUseCase).execute(any(RegisterUserDTO.class));

        assertThrows(AlreadyUsedEmailException.class, () -> authController.register(registerRequest));

        verify(registerMapper, times(1)).toDomain(registerRequest);
        verify(registerUseCase, times(1)).execute(registerUserDTO);
    }

    @Test
    void testLogin_Success() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("existinguser@example.com", "password123");
        String expectedJwt = "dummy-jwt-token";

        when(loginUseCase.execute(anyString(), anyString())).thenReturn(expectedJwt);

        ResponseEntity<AuthenticationResponse> response = authController.login(authenticationRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedJwt, response.getBody().token());
        verify(loginUseCase, times(1)).execute("existinguser@example.com", "password123");
    }

    @Test
    void testLogin_Failure() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("wronguser@example.com", "wrongpassword");

        when(loginUseCase.execute(anyString(), anyString())).thenThrow(new RuntimeException("Invalid credentials"));

        assertThrows(RuntimeException.class, () -> authController.login(authenticationRequest));

        verify(loginUseCase, times(1)).execute("wronguser@example.com", "wrongpassword");
    }

    @Test
    void testLogin_InternalError() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("existinguser@example.com", "password123");

        when(loginUseCase.execute(anyString(), anyString())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> authController.login(authenticationRequest));

        verify(loginUseCase, times(1)).execute("existinguser@example.com", "password123");
    }
}
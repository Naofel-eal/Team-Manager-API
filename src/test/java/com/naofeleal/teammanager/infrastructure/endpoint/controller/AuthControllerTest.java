package com.naofeleal.teammanager.infrastructure.endpoint.controller;

import com.naofeleal.teammanager.core.application.exception.user.AlreadyUsedEmailException;
import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.application.usecase.authentication.dto.RegisterUserDTO;
import com.naofeleal.teammanager.core.application.usecase.authentication.interfaces.ILoginUseCase;
import com.naofeleal.teammanager.core.application.usecase.authentication.interfaces.IRegisterUseCase;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;
import com.naofeleal.teammanager.infrastructure.endpoint.mapper.model.IUserMapper;
import com.naofeleal.teammanager.infrastructure.endpoint.model.authentication.request.AuthenticationRequest;
import com.naofeleal.teammanager.infrastructure.endpoint.model.authentication.response.AuthenticationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

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
    IUserRepository userRepository;

    @Mock
    IUserMapper userMapper;

    @InjectMocks
    private AuthController authController;

    @Test
    void registerShouldSuccess() {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO("newuser@example.com", "password123", "John", "Doe");

        doNothing().when(registerUseCase).execute(
                any(String.class),
                any(String.class),
                any(String.class),
                any(String.class)
        );

        ResponseEntity<Void> response = authController.register(registerUserDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(registerUseCase, times(1)).execute(
                registerUserDTO.firstname(),
                registerUserDTO.lastname(),
                registerUserDTO.email(),
                registerUserDTO.password()
        );
    }

    @Test
    void registerShouldFail() {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO("newuser@example.com", "password123", "John", "Doe");

        doThrow(AlreadyUsedEmailException.class).when(registerUseCase).execute(
                any(String.class),
                any(String.class),
                any(String.class),
                any(String.class)
        );

        assertThrows(AlreadyUsedEmailException.class, () -> authController.register(registerUserDTO));

        verify(registerUseCase, times(1)).execute(
                registerUserDTO.firstname(),
                registerUserDTO.lastname(),
                registerUserDTO.email(),
                registerUserDTO.password()
        );
    }

    @Test
    void loginShouldSuccess() {
        BaseUser user = new SimpleUser(
                1L,
                new Name("Nao"),
                new Name("Fel"),
                new Email("example@gmail.com"),
                new Password("insecure_password")
        );
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("example@gmail.com", "insecure_password");
        String expectedJwt = "dummy-jwt-token";

        when(loginUseCase.execute(anyString(), anyString())).thenReturn(expectedJwt);
        when(userRepository.findByEmail(authenticationRequest.email())).thenReturn(Optional.of(user));

        ResponseEntity<AuthenticationResponse> response = authController.login(authenticationRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedJwt, response.getBody().token());
        verify(loginUseCase, times(1)).execute("example@gmail.com", "insecure_password");
    }

    @Test
    void loginShouldFail() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("wronguser@example.com", "wrongpassword");

        when(loginUseCase.execute(anyString(), anyString())).thenThrow(new RuntimeException("Invalid credentials"));

        assertThrows(RuntimeException.class, () -> authController.login(authenticationRequest));

        verify(loginUseCase, times(1)).execute("wronguser@example.com", "wrongpassword");
    }
}
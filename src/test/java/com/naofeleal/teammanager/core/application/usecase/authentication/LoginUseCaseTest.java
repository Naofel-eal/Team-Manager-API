package com.naofeleal.teammanager.core.application.usecase.authentication;

import com.naofeleal.teammanager.core.application.exception.user.EmailNotFoundException;
import com.naofeleal.teammanager.core.application.repository.IJWTService;
import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.domain.exception.authentication.InvalidPasswordException;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private IJWTService jwtService;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private LoginUseCase loginUseCase;

    private SimpleUser user;

    @BeforeEach
    void setUp() {
        user = new SimpleUser(
                1L,
                new Name("Nao"),
                new Name("Fel"),
                new Email("example@gmail.com"),
                new Password("insecure_password")
        );
    }

    @Test
    void executeShouldReturnJwtTokenForValidUser() {
        String email = "example@gmail.com";
        String password = "insecure_password";
        String expectedJwtToken = "jwt-token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(mock(UsernamePasswordAuthenticationToken.class));
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(expectedJwtToken);

        String actualJwtToken = loginUseCase.execute(email, password);

        assertEquals(expectedJwtToken, actualJwtToken);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail(email);
        verify(jwtService).generateToken(user);
    }

    @Test
    void executeShouldThrowEmailNotFoundExceptionForInvalidUser() {
        String email = "nonexistent@gmail.com";
        String password = "some_password";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(mock(UsernamePasswordAuthenticationToken.class));
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        EmailNotFoundException exception = assertThrows(
            EmailNotFoundException.class,
            () -> loginUseCase.execute(email, password)
        );

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail(email);
        verify(jwtService, never()).generateToken(any(BaseUser.class));
    }

    @Test
    void executeShouldThrowInvalidPasswordExceptionForInvalidPassword() {
        String email = "example@gmail.com";
        String password = "wrong_password";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new InvalidPasswordException());

        assertThrows(
            InvalidPasswordException.class,
            () -> loginUseCase.execute(email, password)
        );

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, never()).findByEmail(anyString());
        verify(jwtService, never()).generateToken(any(BaseUser.class));
    }
}

package com.naofeleal.teammanager.core.application.usecase.authentication;

import com.naofeleal.teammanager.core.application.exception.authentication.AlreadyUsedEmailException;
import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.application.usecase.authentication.dto.RegisterUserDTO;
import com.naofeleal.teammanager.core.domain.model.role.RoleEnum;
import com.naofeleal.teammanager.core.domain.model.user.Email;
import com.naofeleal.teammanager.core.domain.model.user.Name;
import com.naofeleal.teammanager.core.domain.model.user.Password;
import com.naofeleal.teammanager.core.domain.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUseCaseTest {
    @Mock
    private IUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterUseCase registerUseCase;

    private RegisterUserDTO registerUserDTO;
    private User user;

    @BeforeEach
    void setUp() {
        registerUserDTO = new RegisterUserDTO("Nao", "Fel", "example@gmail.com", "8AbcDefGh8");

        when(passwordEncoder.encode("8AbcDefGh8")).thenReturn("encoded_password");
    }

    @Test
    void executeShouldRegisterUserForNewEmail() {
        when(userRepository.findByEmail("example@gmail.com")).thenReturn(Optional.empty());

        registerUseCase.execute(registerUserDTO);

        verify(userRepository).findByEmail("example@gmail.com");
        verify(passwordEncoder).encode("8AbcDefGh8");
        verify(userRepository).register(any(User.class));
    }

    @Test
    void executeShouldThrowAlreadyUsedEmailExceptionForExistingEmail() {
        user = new User(
            new Name("Nao"),
            new Name("Fel"),
            new Email("example@gmail.com"),
            Password.fromRaw("8AbcDefGh8", passwordEncoder::encode),
            RoleEnum.USER
        );
        when(userRepository.findByEmail("example@gmail.com")).thenReturn(Optional.of(user));

        assertThrows(
                AlreadyUsedEmailException.class,
                () -> registerUseCase.execute(registerUserDTO)
        );

        verify(userRepository).findByEmail("example@gmail.com");
        verify(userRepository, never()).register(any(User.class));
    }

    @Test
    void executeShouldRegisterUserWithEncodedPassword() {
        when(userRepository.findByEmail("example@gmail.com")).thenReturn(Optional.empty());

        registerUseCase.execute(registerUserDTO);

        verify(userRepository).findByEmail("example@gmail.com");
        verify(passwordEncoder).encode("8AbcDefGh8");
        verify(userRepository).register(argThat(user ->
                user.getPassword().equals("encoded_password") &&
                        user.getEmail().equals("example@gmail.com")
        ));
    }
}

package com.naofeleal.teammanager.core.application.usecase.user;

import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindFreeUsersUseCaseTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private FindFreeUsersUseCase findFreeUsersUseCase;

    @Test
    void findFreeUsersShouldReturnSetOfFreeSimpleUsers() {
        SimpleUser user1 = new SimpleUser(
                new Name("Nao"),
                new Name("Fel"),
                new Email("test@example.com"),
                new Password("insecure_encoded_password")
        );

        SimpleUser user2 = new SimpleUser(
                new Name("Fel"),
                new Name("Nao"),
                new Email("test2@example.com"),
                new Password("insecure_encoded_password")
        );

        Set<SimpleUser> freeUsers = Set.of(user1, user2);
        when(userRepository.findFreeSimpleUsers()).thenReturn(freeUsers);

        Set<SimpleUser> result = findFreeUsersUseCase.findFreeUsers();

        assertEquals(2, result.size());
        assertTrue(result.contains(user1));
        assertTrue(result.contains(user2));
    }

    @Test
    void findFreeUsersShouldReturnEmptySetIfNoFreeUsers() {
        when(userRepository.findFreeSimpleUsers()).thenReturn(Collections.emptySet());

        Set<SimpleUser> result = findFreeUsersUseCase.findFreeUsers();

        assertTrue(result.isEmpty());
    }

    @Test
    void findFreeUsersShouldThrowExceptionOnRepositoryError() {
        when(userRepository.findFreeSimpleUsers()).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> findFreeUsersUseCase.findFreeUsers());
        assertEquals("Database error", exception.getMessage());
    }
}

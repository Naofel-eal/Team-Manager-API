package com.naofeleal.teammanager.infrastructure.endpoint.controller;

import com.naofeleal.teammanager.core.application.usecase.user.interfaces.IFindFreeUsersUseCase;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;
import com.naofeleal.teammanager.infrastructure.endpoint.mapper.model.IUserMapper;
import com.naofeleal.teammanager.infrastructure.endpoint.model.user.UserDTO;
import com.naofeleal.teammanager.infrastructure.endpoint.model.user.response.FindFreeUsersResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private IFindFreeUsersUseCase findFreeUsersUseCase;

    @Mock
    private IUserMapper userMapper;

    @InjectMocks
    private UserController userController;

    private SimpleUser user1;
    private SimpleUser user2;
    private UserDTO userDTO1;
    private UserDTO userDTO2;

    @BeforeEach
    void setUp() {
        user1 = new SimpleUser(
                new Name("Nao"),
                new Name("Fel"),
                new Email("test@example.com"),
                new Password("insecure_encoded_password")
        );

        user2 = new SimpleUser(
                new Name("Fel"),
                new Name("Nao"),
                new Email("test2@example.com"),
                new Password("insecure_encoded_password")
        );

        userDTO1 = new UserDTO(
                "Nao",
                "Fel",
                "test@example.com",
                "USER"
        );

        userDTO2 = new UserDTO(
                "Fel",
                "Nao",
                "test2@example.com",
                "USER"
        );
    }

    @Test
    void getFreeUsersShouldReturnListOfFreeUserDTOs() {
        Set<SimpleUser> freeUsers = Set.of(user1, user2);
        List<UserDTO> userDTOs = List.of(userDTO1, userDTO2);

        when(findFreeUsersUseCase.findFreeUsers()).thenReturn(freeUsers);
        when(userMapper.toDTOs(anyList())).thenReturn(userDTOs);

        ResponseEntity<FindFreeUsersResponse> response = userController.getFreeUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().freeUsers().size());
        assertEquals(userDTOs, response.getBody().freeUsers());

        verify(findFreeUsersUseCase, times(1)).findFreeUsers();
        verify(userMapper, times(1)).toDTOs(anyList());
    }

    @Test
    void getFreeUsersShouldReturnEmptyListIfNoFreeUsers() {
        Set<SimpleUser> emptyUsers = Collections.emptySet();
        List<UserDTO> emptyUserDTOs = Collections.emptyList();

        when(findFreeUsersUseCase.findFreeUsers()).thenReturn(emptyUsers);
        when(userMapper.toDTOs(anyList())).thenReturn(emptyUserDTOs);

        ResponseEntity<FindFreeUsersResponse> response = userController.getFreeUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().freeUsers().isEmpty());

        verify(findFreeUsersUseCase, times(1)).findFreeUsers();
        verify(userMapper, times(1)).toDTOs(anyList());
    }

    @Test
    void getFreeUsersShouldThrowException() {
        when(findFreeUsersUseCase.findFreeUsers()).thenThrow(new RuntimeException("Error fetching free users"));

        assertThrows(RuntimeException.class, () -> userController.getFreeUsers());

        verify(findFreeUsersUseCase, times(1)).findFreeUsers();
        verify(userMapper, never()).toDTOs(anyList());
    }
}

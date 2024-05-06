package com.naofeleal.teammanager.core.application.usecase.team;

import com.naofeleal.teammanager.core.application.repository.ITeamRepository;
import com.naofeleal.teammanager.core.domain.model.role.Manager;
import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.core.domain.model.user.Email;
import com.naofeleal.teammanager.core.domain.model.user.Name;
import com.naofeleal.teammanager.core.domain.model.user.Password;
import com.naofeleal.teammanager.core.domain.model.user.User;
import com.naofeleal.teammanager.infrastructure.ihm.model.team.response.TeamDTO;
import com.naofeleal.teammanager.infrastructure.ihm.model.user.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindAllTeamsUseCaseTest {

    @Mock
    private ITeamRepository teamRepository;

    @InjectMocks
    private FindAllTeamsUseCase findAllTeamsUseCase;

    private Team _team;

    @BeforeEach
    void setUp() {
        User user1 = new User(
                new Name("Nao"),
                new Name("Fel"),
                new Email("test@example.com"),
                new Password("insecure_encoded_password")
        );

        User user2 = new User(
                new Name("Fel"),
                new Name("Nao"),
                new Email("test2@example.com"),
                new Password("insecure_encoded_password")
        );

        User user3 = new User(
                new Name("No"),
                new Name("Inspiration"),
                new Email("test3@example.com"),
                new Password("insecure_encoded_password")
        );

        Manager _manager = new Manager(user1);

        _team = new Team(_manager, new ArrayList<>(List.of(user2, user3)));
    }

    @Test
    void executeShouldReturnAllTeams() {
        when(teamRepository.findAllTeams()).thenReturn(List.of(_team, _team));

        List<Team> result = findAllTeamsUseCase.execute();

        assertEquals(2, result.size());

        verify(teamRepository).findAllTeams();
    }

    @Test
    void executeShouldReturnEmptyListWhenNoTeamsFound() {
        when(teamRepository.findAllTeams()).thenReturn(Collections.emptyList());

        List<Team> result = findAllTeamsUseCase.execute();

        assertEquals(0, result.size());
        verify(teamRepository).findAllTeams();
    }

    @Test
    void executeShouldHandleRepositoryException() {
        when(teamRepository.findAllTeams()).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, findAllTeamsUseCase::execute);

        assertEquals("Database error", exception.getMessage());
        verify(teamRepository).findAllTeams();
    }
}

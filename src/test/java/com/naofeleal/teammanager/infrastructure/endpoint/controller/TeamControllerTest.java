package com.naofeleal.teammanager.infrastructure.endpoint.controller;

import com.naofeleal.teammanager.core.application.usecase.team.interfaces.IFindAllTeamsUseCase;
import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.core.domain.model.user.Manager;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;
import com.naofeleal.teammanager.infrastructure.endpoint.mapper.model.ITeamMapper;
import com.naofeleal.teammanager.infrastructure.endpoint.model.team.response.FindAllTeamsResponse;
import com.naofeleal.teammanager.infrastructure.endpoint.model.team.response.TeamDTO;
import com.naofeleal.teammanager.infrastructure.endpoint.model.user.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamControllerTest {

    @Mock
    private IFindAllTeamsUseCase _findAllTeamsUseCase;

    @Mock
    private ITeamMapper _teamMapper;

    @InjectMocks
    private TeamController _teamController;

    private Team _team;
    private TeamDTO _teamDTO;

    @BeforeEach
    void setUp() {
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

        SimpleUser user3 = new SimpleUser(
                new Name("No"),
                new Name("Inspiration"),
                new Email("test3@example.com"),
                new Password("insecure_encoded_password")
        );

        UserDTO userDTO1 = new UserDTO(
                "Nao",
                "Fel",
                "test@example.com",
                "MANAGER"
        );

        UserDTO userDTO2 = new UserDTO(
                "Fel",
                "Nao",
                "test2@example.com",
                "USER"
        );

        UserDTO userDTO3 = new UserDTO(
                "No",
                "Inspiration",
                "test3@example.com",
                "USER"
        );

        Manager _manager = new Manager(user1);

        _team = new Team(_manager, new ArrayList<>(List.of(user2, user3)));
        _teamDTO = new TeamDTO(userDTO1, new ArrayList<>(List.of(userDTO2, userDTO3)));
    }

    @Test
    void findAllTeamsShouldSuccess() {

        when(_findAllTeamsUseCase.execute()).thenReturn(List.of(_team));
        when(_teamMapper.fromDomainModels(anyList())).thenReturn(List.of(_teamDTO));

        ResponseEntity<FindAllTeamsResponse> response = _teamController.findAllTeams();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().teams().size());
        assertEquals(2, response.getBody().teams().getFirst().members().size());

        verify(_findAllTeamsUseCase, times(1)).execute();
        verify(_teamMapper, times(1)).fromDomainModels(List.of(_team));
    }

    @Test
    void findAllTeamsShouldReturnsEmptyList() {
        List<Team> emptyTeams = Collections.emptyList();
        List<TeamDTO> emptyTeamDTOs = Collections.emptyList();

        when(_findAllTeamsUseCase.execute()).thenReturn(emptyTeams);
        when(_teamMapper.fromDomainModels(anyList())).thenReturn(emptyTeamDTOs);

        ResponseEntity<FindAllTeamsResponse> response = _teamController.findAllTeams();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().teams().isEmpty());

        verify(_findAllTeamsUseCase, times(1)).execute();
        verify(_teamMapper, times(1)).fromDomainModels(emptyTeams);
    }

    @Test
    void findAllTeamsShouldThrowsException() {
        when(_findAllTeamsUseCase.execute()).thenThrow(new RuntimeException("Error fetching teams"));

        assertThrows(RuntimeException.class, () -> _teamController.findAllTeams());

        verify(_findAllTeamsUseCase, times(1)).execute();
        verify(_teamMapper, never()).fromDomainModels(anyList());
    }
}

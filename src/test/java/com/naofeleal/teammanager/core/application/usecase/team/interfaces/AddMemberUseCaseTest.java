package com.naofeleal.teammanager.core.application.usecase.team.interfaces;

import com.naofeleal.teammanager.core.application.exception.team.TeamNotFoundException;
import com.naofeleal.teammanager.core.application.exception.user.EmailNotFoundException;
import com.naofeleal.teammanager.core.application.exception.user.UnauthorizedOperationException;
import com.naofeleal.teammanager.core.application.repository.ITeamRepository;
import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.application.usecase.permission.interfaces.ICanAddMemberToATeamUseCase;
import com.naofeleal.teammanager.core.application.usecase.team.AddMemberUseCase;
import com.naofeleal.teammanager.core.domain.exception.team.UserAlreadyMemberException;
import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.core.domain.model.user.Manager;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddMemberUseCaseTest {
    @Mock
    private ITeamRepository teamRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private ICanAddMemberToATeamUseCase canAddMemberToATeamUseCase;

    @InjectMocks
    private AddMemberUseCase addMemberUseCase;

    private Team mockTeam;
    private SimpleUser mockUser;

    @BeforeEach
    void setUp() {
        Manager manager = new Manager(
                new Name("Nao"),
                new Name("Fel"),
                new Email("test@example.com"),
                new Password("insecure_encoded_password")
        );
        mockTeam = new Team(1L, manager, new ArrayList<>());
        mockUser = new SimpleUser(
                new Name("Fel"),
                new Name("Nao"),
                new Email("test2@example.com"),
                new Password("insecure_encoded_password"));
    }

    @Test
    void addMemberToExistingTeamSuccess() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(mockTeam));
        when(userRepository.findByEmail("test2@example.com")).thenReturn(Optional.of(mockUser));
        when(teamRepository.save(mockTeam)).thenReturn(mockTeam);
        when(canAddMemberToATeamUseCase.execute(mockTeam.manager, mockTeam)).thenReturn(true);

        Team result = addMemberUseCase.execute(mockTeam.manager, 1L, "test2@example.com");

        assertEquals(1L, result.id);
        assertEquals(1, result.members.size());
        assertEquals(mockUser, result.members.stream().findFirst().get());

        verify(teamRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findByEmail("test2@example.com");
        verify(teamRepository, times(1)).save(mockTeam);
    }

    @Test
    void addMemberAlreadyInTeamThrowsUserAlreadyMemberException() {
        mockTeam.addMember(mockUser);
        SimpleUser userCopy = new SimpleUser(
                mockUser.firstname,
                mockUser.lastname,
                mockUser.email,
                mockUser.password
        );

        when(teamRepository.findById(1L)).thenReturn(Optional.of(mockTeam));
        when(userRepository.findByEmail("test2@example.com")).thenReturn(Optional.of(userCopy));
        when(canAddMemberToATeamUseCase.execute(mockTeam.manager, mockTeam)).thenReturn(true);

        assertThrows(UserAlreadyMemberException.class, () -> addMemberUseCase.execute(mockTeam.manager, 1L, "test2@example.com"));

        verify(teamRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findByEmail("test2@example.com");
        verify(teamRepository, never()).save(mockTeam);
    }

    @Test
    void addMemberToNonExistingTeamThrowsTeamNotFoundException() {
        when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TeamNotFoundException.class, () -> addMemberUseCase.execute(mockTeam.manager, 1L, "test2@example.com"));

        verify(teamRepository, times(1)).findById(1L);
        verify(userRepository, never()).findByEmail(anyString());
    }

    @Test
    void addNonExistingUserToTeamThrowsEmailNotFoundException() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(mockTeam));
        when(userRepository.findByEmail("test2@example.com")).thenReturn(Optional.empty());
        when(canAddMemberToATeamUseCase.execute(mockTeam.manager, mockTeam)).thenReturn(true);

        assertThrows(EmailNotFoundException.class, () -> addMemberUseCase.execute(mockTeam.manager,1L, "test2@example.com"));

        verify(teamRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findByEmail("test2@example.com");
    }

    @Test
    void initiatorHasNotThePermissionThrowsUnauthorizedOperationExceptionO() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(mockTeam));
        when(canAddMemberToATeamUseCase.execute(mockUser, mockTeam)).thenReturn(false);

        assertThrows(UnauthorizedOperationException.class, () -> addMemberUseCase.execute(mockUser,1L, "test2@example.com"));
    }
}

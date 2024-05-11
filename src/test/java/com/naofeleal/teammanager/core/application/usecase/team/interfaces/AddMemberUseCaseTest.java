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
import com.naofeleal.teammanager.core.domain.model.user.Admin;
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
    private Admin initiator;
    private Manager manager;

    @BeforeEach
    void setUp() {
        manager = new Manager(
                new Name("Nao"),
                new Name("Fel"),
                new Email("test@example.com"),
                new Password("insecure_encoded_password")
        );

        initiator = new Admin(
                new Name("Admin"),
                new Name("Admin"),
                new Email("Admin@example.com"),
                new Password("insecure_encoded_password")
        );

        manager.team = mockTeam = new Team(1L, manager, new ArrayList<>());
        mockTeam.manager = manager;

        mockUser = new SimpleUser(
                new Name("Fel"),
                new Name("Nao"),
                new Email("test2@example.com"),
                new Password("insecure_encoded_password"));
    }

    @Test
    void addMemberToExistingTeamSuccess() {
        when(userRepository.findByEmail(mockTeam.manager.getEmail())).thenReturn(Optional.of(mockTeam.manager));
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));

        when(teamRepository.save(mockTeam)).thenReturn(mockTeam);
        when(canAddMemberToATeamUseCase.execute(initiator, mockTeam)).thenReturn(true);

        Team result = addMemberUseCase.execute(initiator, manager.getEmail(), mockUser.getEmail());

        assertEquals(1L, result.id);
        assertEquals(1, result.members.size());
        assertEquals(mockUser, result.members.stream().findFirst().get());

        verify(userRepository, times(1)).findByEmail("test2@example.com");
        verify(teamRepository, times(1)).save(mockTeam);
    }

    @Test
    void addMemberAlreadyInTeamThrowsUserAlreadyMemberException() {
        mockTeam.addMember(mockUser);

        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        when(userRepository.findByEmail(manager.getEmail())).thenReturn(Optional.of(manager));
        when(canAddMemberToATeamUseCase.execute(initiator, mockTeam)).thenReturn(true);

        assertThrows(UserAlreadyMemberException.class, () -> addMemberUseCase.execute(initiator, manager.getEmail(), mockUser.getEmail()));

        verify(userRepository, times(1)).findByEmail(mockUser.getEmail());
        verify(teamRepository, never()).save(mockTeam);
    }

    @Test
    void addMemberToNonExistingTeamThrowsTeamNotFoundException() {
        when(userRepository.findByEmail(manager.getEmail())).thenReturn(Optional.empty());

        assertThrows(TeamNotFoundException.class, () -> addMemberUseCase.execute(initiator, manager.getEmail(), mockUser.getEmail()));
    }

    @Test
    void addNonExistingUserToTeamThrowsEmailNotFoundException() {
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(manager.getEmail())).thenReturn(Optional.of(manager));
        when(canAddMemberToATeamUseCase.execute(initiator, manager.team)).thenReturn(true);

        assertThrows(EmailNotFoundException.class, () -> addMemberUseCase.execute(initiator, manager.getEmail(), mockUser.getEmail()));

        verify(userRepository, times(1)).findByEmail(mockUser.getEmail());
    }

    @Test
    void initiatorHasNotThePermissionThrowsUnauthorizedOperationExceptionO() {
        when(userRepository.findByEmail(manager.getEmail())).thenReturn(Optional.of(manager));
        when(canAddMemberToATeamUseCase.execute(mockUser, mockTeam)).thenReturn(false);

        assertThrows(UnauthorizedOperationException.class, () -> addMemberUseCase.execute(mockUser, manager.getEmail(), "test2@example.com"));
    }
}

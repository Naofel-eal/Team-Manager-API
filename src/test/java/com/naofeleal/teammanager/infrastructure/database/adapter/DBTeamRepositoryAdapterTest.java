package com.naofeleal.teammanager.infrastructure.database.adapter;

import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.core.domain.model.user.Manager;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;
import com.naofeleal.teammanager.infrastructure.database.mapper.IDBTeamMapper;
import com.naofeleal.teammanager.infrastructure.database.model.DBManager;
import com.naofeleal.teammanager.infrastructure.database.model.DBTeam;
import com.naofeleal.teammanager.infrastructure.database.model.DBUser;
import com.naofeleal.teammanager.infrastructure.database.repository.IDBTeamRepository;
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
class DBTeamRepositoryAdapterTest {

    @Mock
    private IDBTeamRepository _teamRepository;

    @Mock
    private IDBTeamMapper _teamMapper;

    @InjectMocks
    private DBTeamRepositoryAdapter _dbTeamRepositoryAdapter;

    private DBTeam _dbTeam;
    private Team _team;

    @BeforeEach
    void setUp() {
        DBManager dbBaseUser1 = new DBManager(
                new DBUser(
                        1L,
                    "Nao",
                    "Fel",
                    "example@gmail.com",
                    "still_unsecure_but_encoded_password"
                ),
                null
        );

        DBUser dbBaseUser2 = new DBUser(
                2L,
                "Fel",
                "Nao",
                "example2@gmail.com",
                "still_unsecure_but_encoded_password"
        );

        DBUser dbBaseUser3 = new DBUser(
                3L,
            "No",
            "Inspiration",
            "example3@gmail.com",
            "still_unsecure_but_encoded_password"
        );

        Manager user1 = new Manager(
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


        _dbTeam = new DBTeam(dbBaseUser1, new ArrayList<>(List.of(dbBaseUser2, dbBaseUser3)));
        _team = new Team(user1, List.of(user2, user3));
    }

    @Test
    void findAllTeamsShouldReturnMappedTeams() {
        when(_teamRepository.findAll()).thenReturn(List.of(_dbTeam));
        when(_teamMapper.toDomainModels(List.of(_dbTeam))).thenReturn(List.of(_team));

        List<Team> result = _dbTeamRepositoryAdapter.findAllTeams();

        assertEquals(1, result.size());

        verify(_teamRepository).findAll();
        verify(_teamMapper).toDomainModels(List.of(_dbTeam));
    }

    @Test
    void findAllTeamsShouldReturnEmptyListWhenNoTeams() {
        List<DBTeam> emptyDbTeams = Collections.emptyList();
        List<Team> emptyTeams = Collections.emptyList();

        when(_teamRepository.findAll()).thenReturn(emptyDbTeams);
        when(_teamMapper.toDomainModels(emptyDbTeams)).thenReturn(emptyTeams);

        List<Team> result = _dbTeamRepositoryAdapter.findAllTeams();

        assertEquals(0, result.size());
        verify(_teamRepository).findAll();
        verify(_teamMapper).toDomainModels(emptyDbTeams);
    }

    @Test
    void findAllTeamsShouldHandleRepositoryException() {
        when(_teamRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, _dbTeamRepositoryAdapter::findAllTeams);

        assertEquals("Database error", exception.getMessage());
        verify(_teamRepository).findAll();
        verify(_teamMapper, never()).toDomainModels(anyList());
    }
}

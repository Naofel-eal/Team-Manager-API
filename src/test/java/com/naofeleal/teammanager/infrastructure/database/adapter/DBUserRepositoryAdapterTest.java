package com.naofeleal.teammanager.infrastructure.database.adapter;

import com.naofeleal.teammanager.core.domain.model.role.RoleCode;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.Manager;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;
import com.naofeleal.teammanager.infrastructure.database.mapper.IDBUserMapper;
import com.naofeleal.teammanager.infrastructure.database.model.DBUser;
import com.naofeleal.teammanager.infrastructure.database.repository.IDBUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DBUserRepositoryAdapterTest {

    @Mock
    private IDBUserRepository userRepository;

    @Mock
    private IDBUserMapper userMapper;

    @InjectMocks
    private DBUserRepositoryAdapter dbUserRepositoryAdapter;

    private BaseUser user;
    private DBUser dbUser;

    @BeforeEach
    void setUp() {
        user = new Manager(
                new Name("Nao"),
                new Name("Fel"),
                new Email("example@gmail.com"),
                new Password("still_unsecure_but_encoded_password")
        );

        dbUser = new DBUser(
                "Nao",
                "Fel",
                "example@gmail.com",
                "still_unsecure_but_encoded_password",
                "MANAGER"
        );
    }

    @Test
    void registerShouldSaveDBUser() {
        when(userMapper.fromDomainModel(user)).thenReturn(dbUser);

        dbUserRepositoryAdapter.register(user);

        verify(userMapper).fromDomainModel(user);
        verify(userRepository).save(dbUser);
    }

    @Test
    void findByEmailShouldReturnUserWhenFound() {
        String email = "example@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(dbUser));
        when(userMapper.toDomainModel(dbUser)).thenReturn(user);

        Optional<BaseUser> result = dbUserRepositoryAdapter.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository).findByEmail(email);
        verify(userMapper).toDomainModel(dbUser);
    }

    @Test
    void findByEmailShouldReturnEmptyWhenNotFound() {
        String email = "nonexistent@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<BaseUser> result = dbUserRepositoryAdapter.findByEmail(email);

        assertFalse(result.isPresent());
        verify(userRepository).findByEmail(email);
        verify(userMapper, never()).toDomainModel(any(DBUser.class));
    }
}

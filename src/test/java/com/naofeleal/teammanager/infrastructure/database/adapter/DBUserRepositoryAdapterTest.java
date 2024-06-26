package com.naofeleal.teammanager.infrastructure.database.adapter;

import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;
import com.naofeleal.teammanager.infrastructure.database.mapper.IDBUserMapper;
import com.naofeleal.teammanager.infrastructure.database.model.DBUser;
import com.naofeleal.teammanager.infrastructure.database.repository.IDBAdminRepository;
import com.naofeleal.teammanager.infrastructure.database.repository.IDBManagerRepository;
import com.naofeleal.teammanager.infrastructure.database.repository.IDBUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DBUserRepositoryAdapterTest {

    @Mock
    private IDBUserRepository userRepository;

    @Mock
    private IDBManagerRepository managerRepository;

    @Mock
    private IDBAdminRepository adminRepository;

    @Mock
    private IDBUserMapper userMapper;

    @InjectMocks
    private DBUserRepositoryAdapter dbUserRepositoryAdapter;

    private SimpleUser user;
    private DBUser dbUser;

    @BeforeEach
    void setUp() {
        user = new SimpleUser(
                1L,
                new Name("Nao"),
                new Name("Fel"),
                new Email("example@gmail.com"),
                new Password("still_unsecure_but_encoded_password")
        );

        dbUser = new DBUser(
                1L,
                "Nao",
                "Fel",
                "example@gmail.com",
                "still_unsecure_but_encoded_password"
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
        when(managerRepository.findByUserId(dbUser.id)).thenReturn(Optional.empty());
        when(adminRepository.findByUserId(dbUser.id)).thenReturn(Optional.empty());
        when(userMapper.toDomainModel(dbUser)).thenReturn(user);

        Optional<BaseUser> result =  dbUserRepositoryAdapter.findByEmail(email);

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

    @Test
    void findFreeSimpleUsersShouldReturnSetOfSimpleUsers() {
        DBUser dbBaseUser2 = new DBUser(2L, "Fel", "Nao", "example@gmail.com", "still_unsecure_but_encoded_password");
        SimpleUser simpleUser2 = new SimpleUser(2L, new Name("Fel"), new Name("Nao"), new Email("example@gmail.com"), new Password("still_unsecure_but_encoded_password"));

        Set<DBUser> dbUsers = Set.of(dbUser, dbBaseUser2);

        when(userRepository.findByTeamIsNullAndAdminIsNullAndManagerIsNull()).thenReturn(dbUsers);
        when(userMapper.toDomainModel(dbUser)).thenReturn(user);
        when(userMapper.toDomainModel(dbBaseUser2)).thenReturn(simpleUser2);

        Set<SimpleUser> result = dbUserRepositoryAdapter.findFreeSimpleUsers();

        assertEquals(2, result.size());
        assertTrue(result.contains(simpleUser2));
    }
}

package com.naofeleal.teammanager.infrastructure.database.adapter;

import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;
import com.naofeleal.teammanager.infrastructure.database.mapper.IDBUserMapper;
import com.naofeleal.teammanager.infrastructure.database.model.DBBaseUser;
import com.naofeleal.teammanager.infrastructure.database.model.DBSimpleUser;
import com.naofeleal.teammanager.infrastructure.database.repository.IDBAdminRepository;
import com.naofeleal.teammanager.infrastructure.database.repository.IDBManagerRepository;
import com.naofeleal.teammanager.infrastructure.database.repository.IDBSimpleUserRepository;
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
    private IDBSimpleUserRepository simpleUserRepository;

    @Mock
    private IDBManagerRepository managerRepository;

    @Mock
    private IDBAdminRepository adminRepository;

    @Mock
    private IDBUserMapper userMapper;

    @InjectMocks
    private DBUserRepositoryAdapter dbUserRepositoryAdapter;

    private SimpleUser user;
    private DBSimpleUser dbUser;

    @BeforeEach
    void setUp() {
        user = new SimpleUser(
                new Name("Nao"),
                new Name("Fel"),
                new Email("example@gmail.com"),
                new Password("still_unsecure_but_encoded_password")
        );

        dbUser = new DBSimpleUser(
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
        verify(simpleUserRepository).save(dbUser);
    }

    @Test
    void findByEmailShouldReturnUserWhenFound() {
        String email = "example@gmail.com";
        when(simpleUserRepository.findByEmail(email)).thenReturn(Optional.of(dbUser));
        when(userMapper.toDomainModel(dbUser)).thenReturn(user);

        Optional<BaseUser> result = dbUserRepositoryAdapter.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(simpleUserRepository).findByEmail(email);
        verify(userMapper).toDomainModel(dbUser);
    }

    @Test
    void findByEmailShouldReturnEmptyWhenNotFound() {
        String email = "nonexistent@gmail.com";
        when(simpleUserRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(managerRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(adminRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<BaseUser> result = dbUserRepositoryAdapter.findByEmail(email);

        assertFalse(result.isPresent());
        verify(simpleUserRepository).findByEmail(email);
        verify(userMapper, never()).toDomainModel(any(DBBaseUser.class));
    }

    @Test
    void findFreeSimpleUsersShouldReturnSetOfSimpleUsers() {
        DBSimpleUser dbBaseUser2 = new DBSimpleUser("Fel", "Nao", "example@gmail.com", "still_unsecure_but_encoded_password");
        SimpleUser simpleUser2 = new SimpleUser(new Name("Fel"), new Name("Nao"), new Email("example@gmail.com"), new Password("still_unsecure_but_encoded_password"));

        Set<DBBaseUser> dbBaseUsers = Set.of(dbUser, dbBaseUser2);

        when(simpleUserRepository.findSimpleUsersWithoutTeam()).thenReturn(dbBaseUsers);
        when(userMapper.toDomainModel(dbUser)).thenReturn(user);
        when(userMapper.toDomainModel(dbBaseUser2)).thenReturn(simpleUser2);

        Set<SimpleUser> result = dbUserRepositoryAdapter.findFreeSimpleUsers();

        assertEquals(2, result.size());
        assertTrue(result.contains(simpleUser2));
    }
}

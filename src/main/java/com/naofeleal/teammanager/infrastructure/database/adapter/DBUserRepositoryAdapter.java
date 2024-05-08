package com.naofeleal.teammanager.infrastructure.database.adapter;

import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.infrastructure.database.mapper.IDBUserMapper;
import com.naofeleal.teammanager.infrastructure.database.model.DBBaseUser;
import com.naofeleal.teammanager.infrastructure.database.model.DBSimpleUser;
import com.naofeleal.teammanager.infrastructure.database.repository.IDBAdminRepository;
import com.naofeleal.teammanager.infrastructure.database.repository.IDBManagerRepository;
import com.naofeleal.teammanager.infrastructure.database.repository.IDBSimpleUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class DBUserRepositoryAdapter implements IUserRepository {
    private final IDBSimpleUserRepository _simpleUserRepository;
    private final IDBManagerRepository _managerRepository;
    private final IDBAdminRepository _adminRepository;
    private final IDBUserMapper _userMapper;

    public DBUserRepositoryAdapter(
            final IDBSimpleUserRepository userRepository,
            final IDBManagerRepository managerRepository,
            final IDBAdminRepository adminRepository,
            final IDBUserMapper userMapper
    ) {
        _simpleUserRepository = userRepository;
        _managerRepository = managerRepository;
        _adminRepository = adminRepository;
        _userMapper = userMapper;
    }

    @Override
    public void register(BaseUser user) {
        DBBaseUser dbBaseUser = _userMapper.fromDomainModel(user);
        _simpleUserRepository.save((DBSimpleUser) dbBaseUser);
    }

    @Override
    public Optional<BaseUser> findByEmail(String email) {
        Optional<? extends DBBaseUser> user = _simpleUserRepository.findByEmail(email);
        if (user.isEmpty()) {
            user = _managerRepository.findByEmail(email);
            if (user.isEmpty()) {
                user = _adminRepository.findByEmail(email);
            }
        }
        return user.map(_userMapper::toDomainModel);
    }

    @Override
    public Set<SimpleUser> findFreeSimpleUsers() {
        Set<DBBaseUser> dbBaseUsers = _simpleUserRepository.findSimpleUsersWithoutTeam();

        return dbBaseUsers.stream()
            .map(_userMapper::toDomainModel)
            .filter(SimpleUser.class::isInstance)
            .map(SimpleUser.class::cast)
            .collect(Collectors.toSet());
    }
}
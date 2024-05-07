package com.naofeleal.teammanager.infrastructure.database.adapter;

import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.infrastructure.database.mapper.IDBUserMapper;
import com.naofeleal.teammanager.infrastructure.database.model.DBUser;
import com.naofeleal.teammanager.infrastructure.database.repository.IDBUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class DBUserRepositoryAdapter implements IUserRepository {
    private final IDBUserRepository _userRepository;
    private final IDBUserMapper _userMapper;

    public DBUserRepositoryAdapter(
            final IDBUserRepository userRepository,
            final IDBUserMapper userMapper
    ) {
        _userRepository = userRepository;
        _userMapper = userMapper;
    }

    @Override
    public void register(BaseUser user) {
        DBUser dbUser = _userMapper.fromDomainModel(user);
        _userRepository.save(dbUser);
    }

    @Override
    public Optional<BaseUser> findByEmail(String email) {
        final Optional<DBUser> dbUser = _userRepository.findByEmail(email);
        return dbUser.map(_userMapper::toDomainModel);
    }

    @Override
    public Set<SimpleUser> findFreeSimpleUsers() {
        Set<DBUser> dbUsers = _userRepository.findUsersWithoutTeamAndWithSimpleUserRole();

        return dbUsers.stream()
            .map(_userMapper::toDomainModel)
            .filter(SimpleUser.class::isInstance)
            .map(SimpleUser.class::cast)
            .collect(Collectors.toSet());
    }
}
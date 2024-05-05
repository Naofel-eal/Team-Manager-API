package com.naofeleal.teammanager.infrastructure.database.adapter;

import com.naofeleal.teammanager.core.application.exception.authentication.EmailNotFoundException;
import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.domain.model.user.User;
import com.naofeleal.teammanager.infrastructure.database.mapper.IDBUserMapper;
import com.naofeleal.teammanager.infrastructure.database.model.account.DBUser;
import com.naofeleal.teammanager.infrastructure.database.repository.IDBUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
    public void register(User user) {
        DBUser dbUser = _userMapper.fromDomainModel(user);
        _userRepository.save(dbUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        final Optional<DBUser> dbUser = _userRepository.findByEmail(email);
        return dbUser.map(_userMapper::toDomainModel);
    }
}
package com.naofeleal.teammanager.infrastructure.security.service;

import com.naofeleal.teammanager.core.application.exception.authentication.EmailNotFoundException;
import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.infrastructure.database.mapper.IDBUserMapper;
import com.naofeleal.teammanager.infrastructure.database.model.DBBaseUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUserRepository _userRepository;
    private final IDBUserMapper _dbUserMapper;

    public UserDetailsServiceImpl(
            IUserRepository dbUserRepository,
            IDBUserMapper dbUserMapper
    ) {
        _userRepository = dbUserRepository;
        _dbUserMapper = dbUserMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws EmailNotFoundException {
        Optional<BaseUser> user = this._userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new EmailNotFoundException(email);
        }
        return _dbUserMapper.fromDomainModel(user.get());
    }
}

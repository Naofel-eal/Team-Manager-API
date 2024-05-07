package com.naofeleal.teammanager.infrastructure.security.service;

import com.naofeleal.teammanager.core.application.exception.authentication.EmailNotFoundException;
import com.naofeleal.teammanager.infrastructure.database.model.DBUser;
import com.naofeleal.teammanager.infrastructure.database.repository.IDBUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IDBUserRepository _dbUserRepository;

    public UserDetailsServiceImpl(IDBUserRepository dbUserRepository) {
        _dbUserRepository = dbUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws EmailNotFoundException {
        Optional<DBUser> dbUser = this._dbUserRepository.findByEmail(email);
        if (dbUser.isEmpty()) {
            throw new EmailNotFoundException(email);
        }
        return dbUser.get();
    }
}

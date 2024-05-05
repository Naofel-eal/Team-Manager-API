package com.naofeleal.teammanager.core.application.repository;

import com.naofeleal.teammanager.core.domain.model.user.User;

import java.util.Optional;

public interface IUserRepository {
    void register(User user);
    Optional<User> findByEmail(String email);
}

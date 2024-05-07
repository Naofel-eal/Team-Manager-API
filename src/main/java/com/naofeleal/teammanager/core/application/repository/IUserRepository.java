package com.naofeleal.teammanager.core.application.repository;

import com.naofeleal.teammanager.core.domain.model.user.BaseUser;

import java.util.Optional;

public interface IUserRepository {
    void register(BaseUser user);
    Optional<BaseUser> findByEmail(String email);
}

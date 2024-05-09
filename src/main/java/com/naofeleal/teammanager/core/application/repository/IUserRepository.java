package com.naofeleal.teammanager.core.application.repository;

import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;

import java.util.Optional;
import java.util.Set;

public interface IUserRepository {
    void delete(BaseUser user);
    void save(BaseUser user);
    void register(BaseUser user);
    Optional<BaseUser> findByEmail(String email);
    Set<SimpleUser> findFreeSimpleUsers();
}

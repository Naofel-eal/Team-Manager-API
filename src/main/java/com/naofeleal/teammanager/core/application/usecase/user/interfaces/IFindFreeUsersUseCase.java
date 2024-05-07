package com.naofeleal.teammanager.core.application.usecase.user.interfaces;

import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;

import java.util.Set;

public interface IFindFreeUsersUseCase {
    Set<SimpleUser> findFreeUsers();
}

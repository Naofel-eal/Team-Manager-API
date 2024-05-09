package com.naofeleal.teammanager.core.application.usecase.permission.interfaces;

import com.naofeleal.teammanager.core.domain.model.user.BaseUser;

public interface ICanDeleteUserUseCase {
    boolean execute(BaseUser initiator, BaseUser userToDelete);
}

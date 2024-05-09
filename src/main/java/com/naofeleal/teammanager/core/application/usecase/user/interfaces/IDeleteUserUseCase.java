package com.naofeleal.teammanager.core.application.usecase.user.interfaces;

import com.naofeleal.teammanager.core.domain.model.user.BaseUser;

public interface IDeleteUserUseCase {
    void execute(BaseUser initiator, String email);
}

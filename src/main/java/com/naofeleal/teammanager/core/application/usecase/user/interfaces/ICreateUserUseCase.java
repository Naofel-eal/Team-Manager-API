package com.naofeleal.teammanager.core.application.usecase.user.interfaces;

import com.naofeleal.teammanager.core.domain.model.user.BaseUser;

public interface ICreateUserUseCase {
    void execute(BaseUser initiator, String firstName, String lastName, String email, String password);
}

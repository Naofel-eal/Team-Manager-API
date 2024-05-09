package com.naofeleal.teammanager.core.application.usecase.authentication.interfaces;

import com.naofeleal.teammanager.core.application.usecase.authentication.dto.RegisterUserDTO;

public interface IRegisterUseCase {
    void execute(String firstName, String lastName, String email, String password);
}

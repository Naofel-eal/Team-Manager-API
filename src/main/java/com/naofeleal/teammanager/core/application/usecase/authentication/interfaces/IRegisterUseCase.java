package com.naofeleal.teammanager.core.application.usecase.authentication.interfaces;

import com.naofeleal.teammanager.core.application.usecase.authentication.dto.RegisterUserDTO;

public interface IRegisterUseCase {
    void execute(RegisterUserDTO registerUserDTO);
}

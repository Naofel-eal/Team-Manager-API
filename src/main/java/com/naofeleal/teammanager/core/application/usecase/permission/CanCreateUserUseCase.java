package com.naofeleal.teammanager.core.application.usecase.permission;

import com.naofeleal.teammanager.core.application.usecase.permission.interfaces.ICanCreateUserUseCase;
import com.naofeleal.teammanager.core.domain.model.permission.PermissionCode;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import org.springframework.stereotype.Service;

@Service
public class CanCreateUserUseCase implements ICanCreateUserUseCase {
    public CanCreateUserUseCase() {}

    @Override
    public boolean execute(BaseUser initiator) {
        return initiator.hasPermission(PermissionCode.CAN_CREATE_USER_ACCOUNT);
    }
}

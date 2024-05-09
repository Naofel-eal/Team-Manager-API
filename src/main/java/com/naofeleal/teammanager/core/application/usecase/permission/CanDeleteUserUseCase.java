package com.naofeleal.teammanager.core.application.usecase.permission;

import com.naofeleal.teammanager.core.application.usecase.permission.interfaces.ICanDeleteUserUseCase;
import com.naofeleal.teammanager.core.domain.model.permission.PermissionCode;
import com.naofeleal.teammanager.core.domain.model.role.RoleCode;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import org.springframework.stereotype.Service;

@Service
public class CanDeleteUserUseCase implements ICanDeleteUserUseCase {
    public CanDeleteUserUseCase() {}

    @Override
    public boolean execute(BaseUser initiator, BaseUser userToDelete) {
        return initiator.hasPermission(PermissionCode.CAN_DELETE_USER_ACCOUNT) &&
                !userToDelete.getRole().equals(RoleCode.ADMIN.toString());
    }
}

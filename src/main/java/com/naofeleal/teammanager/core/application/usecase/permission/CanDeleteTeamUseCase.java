package com.naofeleal.teammanager.core.application.usecase.permission;

import com.naofeleal.teammanager.core.application.usecase.permission.interfaces.ICanDeleteTeamUseCase;
import com.naofeleal.teammanager.core.domain.model.permission.PermissionCode;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import org.springframework.stereotype.Service;

@Service
public class CanDeleteTeamUseCase implements ICanDeleteTeamUseCase {
    @Override
    public boolean execute(BaseUser user) {
        return user.hasPermission(PermissionCode.CAN_DELETE_TEAM);
    }
}

package com.naofeleal.teammanager.core.application.usecase.permission;

import com.naofeleal.teammanager.core.application.usecase.permission.interfaces.ICanCreateTeamUseCase;
import com.naofeleal.teammanager.core.domain.model.permission.PermissionCode;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class CanCreateTeamUseCase implements ICanCreateTeamUseCase {
    @Override
    public boolean execute(BaseUser user) {
        return user.hasPermission(PermissionCode.CAN_CREATE_TEAM);
    }
}

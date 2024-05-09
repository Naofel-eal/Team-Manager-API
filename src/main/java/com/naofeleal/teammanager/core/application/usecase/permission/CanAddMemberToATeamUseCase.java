package com.naofeleal.teammanager.core.application.usecase.permission;

import com.naofeleal.teammanager.core.application.usecase.permission.interfaces.ICanAddMemberToATeamUseCase;
import com.naofeleal.teammanager.core.domain.model.permission.PermissionCode;
import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import org.springframework.stereotype.Service;

@Service
public class CanAddMemberToATeamUseCase implements ICanAddMemberToATeamUseCase {
    @Override
    public boolean execute(BaseUser user, Team team) {
        return user.hasPermission(PermissionCode.CAN_ADD_ANY_TEAM_MEMBER) || user.equals(team.manager);
    }
}

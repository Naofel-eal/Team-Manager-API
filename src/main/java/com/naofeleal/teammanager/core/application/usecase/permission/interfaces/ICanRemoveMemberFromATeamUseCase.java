package com.naofeleal.teammanager.core.application.usecase.permission.interfaces;

import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;

public interface ICanRemoveMemberFromATeamUseCase {
    boolean execute(BaseUser user, Team team);
}

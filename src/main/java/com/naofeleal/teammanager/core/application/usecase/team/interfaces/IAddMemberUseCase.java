package com.naofeleal.teammanager.core.application.usecase.team.interfaces;

import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;

public interface IAddMemberUseCase {
    Team execute(BaseUser user, String managerEmail, String userEmail);
}

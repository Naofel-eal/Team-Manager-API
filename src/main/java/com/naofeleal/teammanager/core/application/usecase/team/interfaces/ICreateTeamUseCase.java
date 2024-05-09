package com.naofeleal.teammanager.core.application.usecase.team.interfaces;

import com.naofeleal.teammanager.core.domain.model.user.BaseUser;

public interface ICreateTeamUseCase {
    void execute(BaseUser initiator, String futureManagerEmail);
}

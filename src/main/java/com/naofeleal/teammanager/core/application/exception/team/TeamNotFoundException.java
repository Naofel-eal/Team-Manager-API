package com.naofeleal.teammanager.core.application.exception.team;

import com.naofeleal.teammanager.core.domain.exception.ExceptionCode;
import com.naofeleal.teammanager.core.domain.exception.TeamManagerRuntimeException;

public class TeamNotFoundException extends TeamManagerRuntimeException {
    public TeamNotFoundException(Long teamId) {
        super(ExceptionCode.TEAM_NOT_FOUND, "Team not found with id ".concat(teamId.toString()));
    }
}

package com.naofeleal.teammanager.core.application.exception.team;

import com.naofeleal.teammanager.core.domain.exception.ExceptionCode;
import com.naofeleal.teammanager.core.domain.exception.TeamManagerRuntimeException;

public class TeamNotFoundException extends TeamManagerRuntimeException {
    public TeamNotFoundException(String managerEmail) {
        super(ExceptionCode.TEAM_NOT_FOUND, "Team not found with manager email ".concat(managerEmail));
    }
}

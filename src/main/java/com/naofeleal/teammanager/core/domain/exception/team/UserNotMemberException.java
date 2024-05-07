package com.naofeleal.teammanager.core.domain.exception.team;

import com.naofeleal.teammanager.core.domain.exception.ExceptionCode;
import com.naofeleal.teammanager.core.domain.exception.TeamManagerRuntimeException;

public class UserNotMemberException extends TeamManagerRuntimeException {
    public UserNotMemberException(String userEmail, String managerEmail) {
        super(ExceptionCode.USER_NOT_MEMBER, userEmail.concat(" is not member of the team of ".concat(managerEmail)));
    }
}

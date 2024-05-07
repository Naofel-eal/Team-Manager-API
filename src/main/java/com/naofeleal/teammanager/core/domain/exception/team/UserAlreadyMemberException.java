package com.naofeleal.teammanager.core.domain.exception.team;

import com.naofeleal.teammanager.core.domain.exception.ExceptionCode;
import com.naofeleal.teammanager.core.domain.exception.TeamManagerRuntimeException;

public class UserAlreadyMemberException extends TeamManagerRuntimeException {
    public UserAlreadyMemberException(String userEmail, String managerEmail) {
        super(ExceptionCode.USER_ALREADY_MEMBER, userEmail.concat(" already member of the team of ").concat(managerEmail));
    }
}

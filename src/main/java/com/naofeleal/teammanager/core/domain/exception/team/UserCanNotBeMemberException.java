package com.naofeleal.teammanager.core.domain.exception.team;

import com.naofeleal.teammanager.core.domain.exception.ExceptionCode;
import com.naofeleal.teammanager.core.domain.exception.TeamManagerRuntimeException;

public class UserCanNotBeMemberException extends TeamManagerRuntimeException {
    public UserCanNotBeMemberException() {
        super(ExceptionCode.USER_CAN_NOT_BE_MEMBER, "User can't be member of a team.");
    }
}

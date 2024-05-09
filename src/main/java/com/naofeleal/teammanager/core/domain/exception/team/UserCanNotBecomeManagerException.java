package com.naofeleal.teammanager.core.domain.exception.team;

import com.naofeleal.teammanager.core.domain.exception.ExceptionCode;
import com.naofeleal.teammanager.core.domain.exception.TeamManagerRuntimeException;

public class UserCanNotBecomeManagerException extends TeamManagerRuntimeException {
    public UserCanNotBecomeManagerException() {
        super(ExceptionCode.USER_CAN_NOT_BECOME_MANAGER, "User can not become manager");
    }
}

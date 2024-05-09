package com.naofeleal.teammanager.core.domain.exception.team;

import com.naofeleal.teammanager.core.domain.exception.ExceptionCode;
import com.naofeleal.teammanager.core.domain.exception.TeamManagerRuntimeException;

public class NotAManagerException extends TeamManagerRuntimeException {
    public NotAManagerException(String email) {
        super(ExceptionCode.USER_NOT_A_MANAGER, email.concat(" is not a manager"));
    }
}

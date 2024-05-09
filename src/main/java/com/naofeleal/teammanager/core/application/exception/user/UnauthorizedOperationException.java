package com.naofeleal.teammanager.core.application.exception.user;

import com.naofeleal.teammanager.core.domain.exception.ExceptionCode;
import com.naofeleal.teammanager.core.domain.exception.TeamManagerRuntimeException;

public class UnauthorizedOperationException extends TeamManagerRuntimeException {
    public UnauthorizedOperationException() {
        super(ExceptionCode.UNAUTHORIZED_OPERATION, "Unauthorized operation");
    }
}

package com.naofeleal.teammanager.core.application.exception.user;

import com.naofeleal.teammanager.core.domain.exception.ExceptionCode;
import com.naofeleal.teammanager.core.domain.exception.TeamManagerRuntimeException;

public class InvalidTokenException extends TeamManagerRuntimeException {
    public InvalidTokenException() {
        super(ExceptionCode.INVALID_TOKEN, "Invalid token");
    }
}

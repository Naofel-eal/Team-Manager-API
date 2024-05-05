package com.naofeleal.teammanager.core.domain.exception.authentication;

import com.naofeleal.teammanager.core.domain.exception.ExceptionCode;
import com.naofeleal.teammanager.core.domain.exception.TeamManagerRuntimeException;

public class InvalidPasswordException extends TeamManagerRuntimeException {
    public InvalidPasswordException() {
        super(ExceptionCode.INVALID_PASSWORD, "Invalid password");
    }
}

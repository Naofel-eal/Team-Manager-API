package com.naofeleal.teammanager.core.domain.exception.authentication;

import com.naofeleal.teammanager.core.domain.exception.ExceptionCode;
import com.naofeleal.teammanager.core.domain.exception.TeamManagerRuntimeException;

public class InvalidEmailException extends TeamManagerRuntimeException {
    public InvalidEmailException() {
        super(ExceptionCode.INVALID_EMAIL, "Invalid email");
    }
}

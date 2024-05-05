package com.naofeleal.teammanager.core.domain.exception.authentication;

import com.naofeleal.teammanager.core.domain.exception.ExceptionCode;
import com.naofeleal.teammanager.core.domain.exception.TeamManagerRuntimeException;

public class InvalidNameException extends TeamManagerRuntimeException {
    public InvalidNameException(final String name) {
        super(ExceptionCode.INVALID_NAME, "Invalid name: ".concat(name));
    }
}

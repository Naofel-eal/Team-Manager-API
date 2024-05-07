package com.naofeleal.teammanager.infrastructure.database.exception;

import com.naofeleal.teammanager.core.domain.exception.ExceptionCode;
import com.naofeleal.teammanager.core.domain.exception.TeamManagerRuntimeException;

public class UnknownRoleException extends TeamManagerRuntimeException {
    public UnknownRoleException(String role) {
        super(ExceptionCode.UNKNOWN_ROLE, "Unknwon role: ".concat(role));
    }
}

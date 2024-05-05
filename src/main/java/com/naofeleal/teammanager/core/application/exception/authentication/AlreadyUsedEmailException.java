package com.naofeleal.teammanager.core.application.exception.authentication;

import com.naofeleal.teammanager.core.domain.exception.ExceptionCode;
import com.naofeleal.teammanager.core.domain.exception.TeamManagerRuntimeException;

public class AlreadyUsedEmailException extends TeamManagerRuntimeException {
    public AlreadyUsedEmailException() {
        super(ExceptionCode.ALREADY_USED_EMAIL, "Email already used");
    }
}

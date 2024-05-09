package com.naofeleal.teammanager.core.application.exception.user;

import com.naofeleal.teammanager.core.domain.exception.ExceptionCode;
import com.naofeleal.teammanager.core.domain.exception.TeamManagerRuntimeException;

public class EmailNotFoundException extends TeamManagerRuntimeException {
    public EmailNotFoundException(final String email) {
        super(ExceptionCode.EMAIL_NOT_FOUND, "Email not found: ".concat(email));
    }
}

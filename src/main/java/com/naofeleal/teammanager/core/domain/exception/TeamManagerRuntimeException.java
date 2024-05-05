package com.naofeleal.teammanager.core.domain.exception;

public abstract class TeamManagerRuntimeException extends RuntimeException {
    private final ExceptionCode _exceptionCode;
    protected TeamManagerRuntimeException(final ExceptionCode exceptionCode, final String msg) {
        super(msg);
        this._exceptionCode = exceptionCode;
    }

    public String getErrorCode() {
        return _exceptionCode.toString();
    }
}

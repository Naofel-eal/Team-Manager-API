package com.naofeleal.teammanager.infrastructure.exception_handler;

public class ExceptionResponse {
    private final String _exceptionCode;
    private final String _exceptionMessage;

    public ExceptionResponse(String exceptionCode, String exceptionMessage) {
        this._exceptionCode = exceptionCode;
        this._exceptionMessage = exceptionMessage;
    }

    public String getExceptionCode() {
        return _exceptionCode;
    }

    public String getExceptionMessage() {
        return _exceptionMessage;
    }

    @Override
    public String toString() {
        return "[".concat(_exceptionCode).concat("]".concat(" ").concat(_exceptionMessage));
    }
}

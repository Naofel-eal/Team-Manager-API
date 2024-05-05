package com.naofeleal.teammanager.core.domain.exception;

public enum ExceptionCode {
    // Auth
    INVALID_PASSWORD("AUTH_001"),
    INVALID_EMAIL("AUTH_002"),
    ALREADY_USED_EMAIL("AUTH_003"),
    INVALID_NAME("AUTH_004"),
    EMAIL_NOT_FOUND("AUTH_005")
    ;

    private final String _value;

    ExceptionCode(final String value) {
        this._value = value;
    }

    @Override
    public String toString() {
        return _value;
    }
}

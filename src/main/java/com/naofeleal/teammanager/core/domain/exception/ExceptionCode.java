package com.naofeleal.teammanager.core.domain.exception;

public enum ExceptionCode {
    // Authentication
    INVALID_PASSWORD("AUTH_001"),
    INVALID_EMAIL("AUTH_002"),
    ALREADY_USED_EMAIL("AUTH_003"),
    INVALID_NAME("AUTH_004"),
    EMAIL_NOT_FOUND("AUTH_005"),
    INVALID_TOKEN("INVALID_TOKEN"),

    // Role
    UNKNOWN_ROLE("ROLE_001"),
    UNAUTHORIZED_OPERATION("ROLE_002"),

    // Team
    USER_ALREADY_MEMBER("TEAM_001"),
    USER_NOT_MEMBER("TEAM_002"),
    TEAM_NOT_FOUND("TEAM_003"),
    USER_CAN_NOT_BE_MEMBER("TEAM_004"),
    USER_CAN_NOT_BECOME_MANAGER("TEAM_005"),
    USER_NOT_A_MANAGER("TEAM_006"),
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

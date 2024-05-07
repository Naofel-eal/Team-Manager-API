package com.naofeleal.teammanager.core.domain.model.role;

public enum RoleCode {
    SIMPLE_USER("SIMPLE_USER"),
    MANAGER("MANAGER"),
    ADMIN("ADMIN");

    private final String _value;

    RoleCode(final String value) {
        this._value = value;
    }

    @Override
    public String toString() {
        return _value;
    }
}

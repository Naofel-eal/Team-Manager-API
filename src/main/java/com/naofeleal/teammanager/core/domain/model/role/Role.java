package com.naofeleal.teammanager.core.domain.model.role;

public enum Role {
    USER("USER"),
    MANAGER("MANAGER"),
    ADMIN("ADMIN");

    private final String _value;

    Role(final String value) {
        this._value = value;
    }

    @Override
    public String toString() {
        return _value;
    }
}

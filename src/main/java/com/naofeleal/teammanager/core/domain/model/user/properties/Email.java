package com.naofeleal.teammanager.core.domain.model.user.properties;

public class Email {
    private final String _value;

    public Email(String value) {
        this._value = value;
    }

    @Override
    public String toString() {
        return _value;
    }
}

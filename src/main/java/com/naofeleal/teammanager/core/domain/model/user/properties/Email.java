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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return this.toString().equals(email.toString());
    }

    @Override
    public int hashCode() {
        return _value.hashCode();
    }
}

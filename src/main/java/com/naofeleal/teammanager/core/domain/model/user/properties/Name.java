package com.naofeleal.teammanager.core.domain.model.user.properties;

import com.naofeleal.teammanager.core.domain.exception.authentication.InvalidNameException;
import com.naofeleal.teammanager.shared.utils.StringUtils;

public class Name {
    private final String _value;

    public Name(final String value) throws InvalidNameException {
        if (!this.isValid(value)) {
            throw new InvalidNameException(value);
        }
        this._value = value;
    }

    private boolean isValid(final String value) {
        final String nameWithoutWhiteSpaces = StringUtils.removeWhiteSpaces(value);
        return !nameWithoutWhiteSpaces.isEmpty() &&
            StringUtils.isAlphabetic(nameWithoutWhiteSpaces);
    }

    @Override
    public String toString() {
        return _value;
    }
}

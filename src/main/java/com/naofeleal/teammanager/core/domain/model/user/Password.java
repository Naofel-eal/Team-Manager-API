package com.naofeleal.teammanager.core.domain.model.user;

import com.naofeleal.teammanager.core.domain.exception.authentication.InvalidPasswordException;
import com.naofeleal.teammanager.shared.StringUtils;

import java.util.function.UnaryOperator;

public class Password {
    private static final int _MIN_PASSWORD_LENGTH = 8;
    private final String _value;

    public Password(final String encodedValue) {
        this._value = encodedValue;
    }

    public static Password fromRaw(final String rawValue, final UnaryOperator<String> encoderFn) {
        if (!Password.isValid(rawValue)) {
            throw new InvalidPasswordException();
        }
        return new Password(encoderFn.apply(rawValue));
    }
    private static boolean isValid(final String value) {
        return value.length() >= _MIN_PASSWORD_LENGTH &&
                StringUtils.containsDigit(value) &&
                StringUtils.containsUpperCase(value) &&
                StringUtils.containsLowerCase(value);
    }

    @Override
    public String toString() {
        return _value;
    }
}

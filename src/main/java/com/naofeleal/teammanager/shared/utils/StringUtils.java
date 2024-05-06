package com.naofeleal.teammanager.shared.utils;

public class StringUtils {
    public static String removeWhiteSpaces(final String value) {
        return value.replaceAll("\\s+", "");
    }

    public static boolean isAlphabetic(final String input) {
        return input.matches("^[a-zA-Z]+$");
    }

    public static boolean containsUpperCase(final String str) {
        return str.matches(".*[A-Z].*");
    }

    public static boolean containsLowerCase(final String str) {
        return str.matches(".*[a-z].*");
    }

    public static boolean containsDigit(final String str) {
        return str.matches(".*\\d.*");
    }
}

package com.skillproof.validators;

public final class RegEx {
    private RegEx(){}

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$^_.'`@~\\-]*$";
    public static final String DEFAULT_ALLOWED_CHARACTERS_REGEX = "([a-zA-Z0-9](([a-zA-Z0-9-._ ]*)[a-zA-Z0-9^-])?)?";
    public static final String STRING_CHARACTERS_REGEX = "^(?! )[\\S ]*(?<! )$";
    public static final String URL_REGEX = "^$|(?i)(http:\\/\\/|https:\\/\\/)([a-z0-9./\\-_.~+=:;%&?]+)$";
}

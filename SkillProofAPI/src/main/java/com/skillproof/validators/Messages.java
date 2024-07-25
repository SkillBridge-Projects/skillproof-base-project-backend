package com.skillproof.validators;

import org.slf4j.helpers.MessageFormatter;

public final class Messages {

    private Messages(){}

    public static final String EMAIL_VALIDATION_PROPERTY = "'{propertyName}' must only contain alphanumeric characters and some special characters that are legal in email addresses; e.g., @.-_ with embedded spaces";
    public static final String DEFAULT_CHARACTERS_PROPERTY = "'{propertyName}' only contain alphanumeric characters and some special characters i.e. .-_:/ with embedded spaces and MUST NOT have leading or trailing white spaces";
    public static final String SIZE_VALIDATION_PROPERTY = "Only {max} characters allowed in '{propertyName}'";
    public static final String REQUIRED_PROPERTY = "'{propertyName}' field required";
    public static final String NO_EMPTY_PROPERTY = "'{propertyName}' cannot be empty";
    public static final String NO_WHITESPACE_PROPERTY = "'{propertyName}' MUST NOT contain blank characters, leading or trailing white spaces";
}

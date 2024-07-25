package com.skillproof.exceptions;

public class EmailNotFoundException extends ResourceNotFoundException {

    public EmailNotFoundException(String object, String field, String id) {
        super(object, field, id);
    }
}

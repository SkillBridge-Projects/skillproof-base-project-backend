package com.skillproof.skillproofapi.exceptions;

public class EmailNotFoundException extends ResourceNotFoundException {

    public EmailNotFoundException(String object, String field, String id) {
        super(object, field, id);
    }
}

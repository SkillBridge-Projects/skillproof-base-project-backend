package com.skillproof.skillproofapi.exceptions;

public class EmailNotFoundException extends ResourceNotFoundException {

    public EmailNotFoundException(String email) {
        super("Email "+ email +" doesn't exist");
    }
}

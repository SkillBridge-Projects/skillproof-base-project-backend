package com.skillproof.skillproofapi.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("Email "+ email +" exists already");
    }
}

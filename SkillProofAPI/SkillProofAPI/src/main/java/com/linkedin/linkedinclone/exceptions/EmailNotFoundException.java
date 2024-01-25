package com.linkedin.linkedinclone.exceptions;

public class EmailNotFoundException extends ResourceNotFoundException {

    public EmailNotFoundException(String email) {
        super("Email "+ email +" doesn't exist");
    }
}

package com.linkedin.linkedinclone.exceptions;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String str) {
        super(str);
    }
}

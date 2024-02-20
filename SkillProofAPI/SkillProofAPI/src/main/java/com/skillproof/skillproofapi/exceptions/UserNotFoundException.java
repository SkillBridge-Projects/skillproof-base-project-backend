package com.skillproof.skillproofapi.exceptions;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String str) {
        super(str);
    }
}

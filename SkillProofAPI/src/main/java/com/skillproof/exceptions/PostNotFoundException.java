package com.skillproof.exceptions;

public class PostNotFoundException extends ResourceNotFoundException {
    public PostNotFoundException(String str) {
        super(str);
    }
}

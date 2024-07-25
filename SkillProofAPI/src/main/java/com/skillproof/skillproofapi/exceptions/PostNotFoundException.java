package com.skillproof.skillproofapi.exceptions;

public class PostNotFoundException extends ResourceNotFoundException {
    public PostNotFoundException(String str) {
        super(str);
    }
}

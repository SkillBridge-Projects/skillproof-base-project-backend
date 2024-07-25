package com.skillproof.exceptions;

public class ObjectExistsException extends RuntimeException {
    public ObjectExistsException(String str) {
        super(str);
    }
}

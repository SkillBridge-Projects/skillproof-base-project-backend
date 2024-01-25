package com.linkedin.linkedinclone.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String str) {
        super(str);
    }
}

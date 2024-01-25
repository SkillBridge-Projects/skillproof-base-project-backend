package com.linkedin.linkedinclone.exceptions;
public class PostNotFoundException extends ResourceNotFoundException {
    public PostNotFoundException(String str) {
        super(str);
    }
}

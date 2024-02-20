package com.skillproof.skillproofapi.exceptions;

public class PasswordsNotSameException extends RuntimeException {
    public PasswordsNotSameException() {
        super("Passwords are different");
    }
}

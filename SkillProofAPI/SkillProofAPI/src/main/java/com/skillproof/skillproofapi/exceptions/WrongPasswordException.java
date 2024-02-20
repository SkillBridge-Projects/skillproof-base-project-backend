package com.skillproof.skillproofapi.exceptions;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
        super("Wrong password");
    }}

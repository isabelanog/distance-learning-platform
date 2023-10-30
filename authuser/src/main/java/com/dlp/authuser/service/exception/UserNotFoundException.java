package com.dlp.authuser.service.exception;

public class UserNotFoundException extends RuntimeException {
    private static final long seriaLVersionUID = 1L;
    public UserNotFoundException(String msg) {
        super(msg);
    }
}

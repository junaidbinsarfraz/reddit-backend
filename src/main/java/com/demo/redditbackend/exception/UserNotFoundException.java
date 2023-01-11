package com.demo.redditbackend.exception;

public class UserNotFoundException extends Throwable {

    public UserNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}

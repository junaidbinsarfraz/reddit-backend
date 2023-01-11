package com.demo.redditbackend.exception;

public class PostNotFoundException extends Throwable {

    public PostNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}

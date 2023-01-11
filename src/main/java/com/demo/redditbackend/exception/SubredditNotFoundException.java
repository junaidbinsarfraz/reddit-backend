package com.demo.redditbackend.exception;

public class SubredditNotFoundException extends Throwable {

    public SubredditNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}

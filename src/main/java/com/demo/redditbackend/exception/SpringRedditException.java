package com.demo.redditbackend.exception;

public class SpringRedditException extends Throwable {
    public SpringRedditException(String exceptionMessage) {
        super(exceptionMessage);
    }
}

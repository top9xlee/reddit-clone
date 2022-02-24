package com.Website.Step2.exception;

public class SpringException extends RuntimeException {
    public SpringException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }
    public SpringException(String message) {
        super(message);
    }
}
package com.stackify.sandbox.exceptions;

public class CustomThrownException extends Exception {
    public CustomThrownException(String errorMessage) {
        super(errorMessage);
    }
}

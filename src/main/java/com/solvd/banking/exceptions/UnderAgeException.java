package com.solvd.banking.exceptions;

public class UnderAgeException extends RuntimeException {

    public UnderAgeException() {
    }

    public UnderAgeException(String message) {
        super(message);
    }
}

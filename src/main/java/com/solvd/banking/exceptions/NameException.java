package com.solvd.banking.exceptions;

public class NameException extends RuntimeException {

    public NameException() {
    }

    public NameException(String message) {
        super(message);
    }
}

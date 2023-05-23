package com.solvd.banking.exceptions;

public class InvalidPurchaseException extends RuntimeException {
    public InvalidPurchaseException() {
    }

    public InvalidPurchaseException(String message) {
        super(message);
    }
}

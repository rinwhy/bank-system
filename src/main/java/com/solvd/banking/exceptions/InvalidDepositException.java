package com.solvd.banking.exceptions;

public class InvalidDepositException extends RuntimeException {

    public InvalidDepositException() {
    }

    public InvalidDepositException(String message) {
        super(message);
    }
}

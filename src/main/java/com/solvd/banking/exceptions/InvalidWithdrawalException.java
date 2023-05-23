package com.solvd.banking.exceptions;

public class InvalidWithdrawalException extends RuntimeException {

    public InvalidWithdrawalException() {
    }

    public InvalidWithdrawalException(String message) {
        super(message);
    }
}

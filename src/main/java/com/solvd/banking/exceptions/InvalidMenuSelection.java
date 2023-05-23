package com.solvd.banking.exceptions;

public class InvalidMenuSelection extends RuntimeException {

    public InvalidMenuSelection() {
    }

    public InvalidMenuSelection(String message) {
        super(message);
    }

}

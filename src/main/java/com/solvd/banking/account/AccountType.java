package com.solvd.banking.account;

public enum AccountType {
    CHECKING("Checking"),
    SAVINGS("Savings"),
    CREDIT("Credit");

    private String accType;

    AccountType(String accType) {
        this.accType = accType;
    }

}

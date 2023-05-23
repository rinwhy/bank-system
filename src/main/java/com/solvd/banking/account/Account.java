package com.solvd.banking.account;

public abstract class Account {

    private final AccountType accountType;     // the type of account is specified

    //constructor
    public Account(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public abstract void printBalance();

}
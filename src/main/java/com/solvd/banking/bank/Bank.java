package com.solvd.banking.bank;

public class Bank {

    private final String bankName;
    private final String bankAddress;
    private final String bankEmail;
    private final String bankPhoneNumber;

    public Bank(String bankName, String bankAddress, String bankEmail, String bankPhoneNumber) {
        this.bankName = bankName;
        this.bankAddress = bankAddress;
        this.bankEmail = bankEmail;
        this.bankPhoneNumber = bankPhoneNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public String getBankEmail() {
        return bankEmail;
    }

    public String getBankPhoneNumber() {
        return bankPhoneNumber;
    }

}
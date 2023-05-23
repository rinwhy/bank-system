package com.solvd.banking;

import com.solvd.banking.bank.BankApp;


public class Main {

    public static void main(String[] args) {

        BankApp bankApp = new BankApp();
        bankApp.startBankApp();

        //FunctionalInterface Runnable
        Runtime.getRuntime().addShutdownHook(new Thread(() -> bankApp.shutdown()));
    }
}
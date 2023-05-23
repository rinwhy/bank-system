package com.solvd.banking.database;

import com.solvd.banking.account.Account;
import com.solvd.banking.iFunctional.TransactionAccChecker;
import com.solvd.banking.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Transaction {

    private final int transactionID;
    private final String type;
    private final String timeOfTransaction;
    private final String fromAccount;
    private final String toAccount;
    private final double amount;


    //constructor
    public Transaction(User user, String type, Account fromAccount, Account toAccount, double amount) {

        this.transactionID = TransactionHistory.getTransactionCount(user) + 1;
        this.type = type;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyy, hh:mm:ss a");
        timeOfTransaction = LocalDateTime.now().format(formatter);

        TransactionAccChecker checker = (account) -> account != null;

        if (checker.checkAccount(fromAccount)) {
            this.fromAccount = fromAccount.getAccountType().toString();
        } else this.fromAccount = null;

        if (checker.checkAccount(toAccount)) {
            this.toAccount = toAccount.getAccountType().toString();
        } else this.toAccount = null;

        this.amount = amount;
        TransactionHistory.addToHistory(user, this);
    }


    //region getters
    public int getTransactionID() {
        return transactionID;
    }

    public String getType() {
        return type;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public double getAmount() {
        return amount;
    }

    public String getTimeOfTransaction() {
        return timeOfTransaction;
    }
    //endregion
}
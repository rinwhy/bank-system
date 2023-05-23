package com.solvd.banking.account;

import com.solvd.banking.database.Database;
import com.solvd.banking.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreditAccount extends Account implements ISwipeCard {

    private static final Logger LOGGER = LogManager.getLogger(CreditAccount.class);

    private double creditLimit;
    private double remainingCredit;
    private double outstandingBalance;       // the outstanding balance is what needs to be paid off

    //Constructor
    public CreditAccount(User user, Database db) {
        super(AccountType.CREDIT);
        db.addNewBankAccount(user, this);
    }

    public static String generalInformation() {
        return "\nThe credit account has a credit limit of $8,000";
    }

    //region Getters and Setters
    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void setRemainingCredit(double remainingCredit) {
        this.remainingCredit = remainingCredit;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public double getRemainingCredit() {
        return creditLimit - outstandingBalance;
    }

    public double getOutstandingBalance() {
        return outstandingBalance;
    }

    //endregion

    public boolean payOutstandingBalance(double amount) {
        if (amount > 0 && amount <= outstandingBalance) {
            outstandingBalance -= amount;
            remainingCredit += amount;
            return true;
        }
        return false;
    }

    @Override
    public void printBalance() {
        LOGGER.info(String.format("\nOutstanding Balance:$%.2f", outstandingBalance));
        LOGGER.info(String.format("\nAvailable Credit:$%.2f", remainingCredit));
    }

    @Override
    public boolean swipeCard(double cost) {
        if (cost <= remainingCredit) {
            remainingCredit -= cost;
            outstandingBalance += cost;
            return true;
        } else {
            LOGGER.info("\nYou do not have enough credit!");
            LOGGER.info("\nThe remaining credit is $" + remainingCredit);
            return false;
        }
    }
}
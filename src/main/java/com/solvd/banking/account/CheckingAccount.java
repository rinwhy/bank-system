package com.solvd.banking.account;

import com.solvd.banking.database.Database;
import com.solvd.banking.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckingAccount extends Account implements IWithdraw, IDeposit, ITransferable, ISwipeCard {

    private static final Logger LOGGER = LogManager.getLogger(CheckingAccount.class);

    private double minBalance;
    private double maxWithdrawal;
    private double balance;

    //Constructor
    public CheckingAccount(User user, double initialDeposit, Database database) {
        super(AccountType.CHECKING);
        this.balance = initialDeposit;

        database.addNewBankAccount(user, this);
    }

    public static String generalInformation() {
        return "\nThe checking account has a minimum daily balance of $500 and a max withdrawal of $10,000";
    }

    //region Getters and Setters
    public double getBalance() {
        return balance;
    }

    public void setBalance(double amount) {
        balance = amount;
    }

    public double getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(double minBalance) {
        this.minBalance = minBalance;
    }

    public double getMaxWithdrawal() {
        return maxWithdrawal;
    }

    public void setMaxWithdrawal(double maxWithdrawal) {
        this.maxWithdrawal = maxWithdrawal;
    }
    //endregion

    @Override
    public boolean withdraw(double amount) {
        if (amount <= maxWithdrawal && balance - amount >= minBalance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public double getMaxWithdrawLimit() {
        return maxWithdrawal;
    }

    @Override
    public void printBalance() {
        LOGGER.info(String.format("\nAvailable Balance:$%.2f", balance));
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public boolean swipeCard(double cost) {    // swiping as a debit card
        if (cost <= balance) {
            balance -= cost;
            return true;
        }
        return false;
    }

    @Override
    public boolean transferSend(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public void transferReceive(double amount) {
        balance += amount;
    }
}
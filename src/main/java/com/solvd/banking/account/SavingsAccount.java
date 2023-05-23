package com.solvd.banking.account;

import com.solvd.banking.database.Database;
import com.solvd.banking.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SavingsAccount extends Account implements IWithdraw, IDeposit, ITransferable {

    private static final Logger LOGGER = LogManager.getLogger(SavingsAccount.class);

    private double minBalance;
    private double maxWithdrawal;
    private double balance;
    private double interestRate;

    //Constructor
    public SavingsAccount(User user, double initialDeposit, Database db) {
        super(AccountType.SAVINGS);
        this.balance = initialDeposit;

        db.addNewBankAccount(user, this);
    }

    public static String generalInformation() {
        return "\nThe Savings account has a minimum daily balance of $500, a max withdrawal of $10,000 and an interest rate of 0.02%";
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

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    private void calculateInterestGain() {
        balance = balance + (balance * interestRate);
        printBalance();
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
    public void deposit(double amount) {
        balance += amount;
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
        setBalance(getBalance() + amount);
    }

    @Override
    public void printBalance() {
        LOGGER.info(String.format("\nAvailable Balance:$%.2f", balance));
    }
}
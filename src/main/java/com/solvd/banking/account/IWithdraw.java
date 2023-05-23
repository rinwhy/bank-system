package com.solvd.banking.account;

public interface IWithdraw {
    boolean withdraw(double amount);

    double getMaxWithdrawLimit();
}

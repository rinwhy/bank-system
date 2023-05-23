package com.solvd.banking.iFunctional;
import com.solvd.banking.account.Account;

@FunctionalInterface
public interface TransactionAccChecker {
    boolean checkAccount(Account account);
}

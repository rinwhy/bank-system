package com.solvd.banking.user;

import com.solvd.banking.account.CheckingAccount;
import com.solvd.banking.account.CreditAccount;
import com.solvd.banking.account.SavingsAccount;
import com.solvd.banking.onlineBanking.OnlineBankingAccount;

public class UserData {

    private User user;
    private CheckingAccount checkingAccount;
    private SavingsAccount savingsAccount;
    private CreditAccount creditAccount;
    private OnlineBankingAccount onlineAcc;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CheckingAccount getCheckingAccount() {
        return checkingAccount;
    }

    public void setCheckingAccount(CheckingAccount checkingAccount) {
        this.checkingAccount = checkingAccount;
    }

    public SavingsAccount getSavingsAccount() {
        return savingsAccount;
    }

    public void setSavingsAccount(SavingsAccount savingsAccount) {
        this.savingsAccount = savingsAccount;
    }

    public CreditAccount getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(CreditAccount creditAccount) {
        this.creditAccount = creditAccount;
    }

    public OnlineBankingAccount getOnlineAcc() {
        return onlineAcc;
    }

    public void setOnlineAcc(OnlineBankingAccount onlineAcc) {
        this.onlineAcc = onlineAcc;
    }

}

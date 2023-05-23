package com.solvd.banking.onlineBanking;

public class OnlineBankingAccount {

    private String username;
    private String password;

    public OnlineBankingAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

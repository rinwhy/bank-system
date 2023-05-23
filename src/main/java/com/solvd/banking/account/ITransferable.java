package com.solvd.banking.account;

public interface ITransferable {

    boolean transferSend(double amount);

    void transferReceive(double amount);

}

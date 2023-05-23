package com.solvd.banking.onlineBanking;

import com.solvd.banking.account.*;
import com.solvd.banking.database.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OnlineBanking {

    private static final Logger LOGGER = LogManager.getLogger(OnlineBanking.class);


    public static void printAccountBalance(Account account) {
        account.printBalance();
    }

    public static void withdrawFromAccount(IWithdraw withdrawAcc, double amount) {
        if (withdrawAcc.withdraw(amount)) {
            Account fromAccount = (Account) withdrawAcc;
            new Transaction(OnlineBankingManager.getCurrentUser(), "Withdrawal", fromAccount, null, amount);
            LOGGER.info("\nWithdrawal Successful");
        } else {
            LOGGER.info("\nWithdrawal Unsuccessful!!");
            LOGGER.info(String.format("\nWithdrawal limit is:$%.2f ", withdrawAcc.getMaxWithdrawLimit()));
        }
    }


    public static void depositInAccount(IDeposit depositAcc, double amount) {
        depositAcc.deposit(amount);
        Account toAccount = (Account) depositAcc;
        new Transaction(OnlineBankingManager.getCurrentUser(), "Deposit", null, toAccount, amount);
        LOGGER.info("\nDeposit Successful");
    }

    public static void payCCBalance(ITransferable transferSender, CreditAccount creditAccount, double amount) {
        if (amount > 0 && creditAccount.getOutstandingBalance() >= amount) {
            if (transferSender.transferSend(amount)) {
                creditAccount.payOutstandingBalance(amount);

                Account fromAccount = (Account) transferSender;
                new Transaction(OnlineBankingManager.getCurrentUser(), "Credit Card payment", fromAccount, creditAccount, amount);
            }
        }
    }

    public static void transferBetweenAccounts(ITransferable transferSender, ITransferable transferReceiver, double amount) {
        if (transferSender.transferSend(amount)) {
            transferReceiver.transferReceive(amount);

            Account fromAccount = (Account) transferSender;
            Account toAccount = (Account) transferReceiver;
            new Transaction(OnlineBankingManager.getCurrentUser(), "Transfer", fromAccount, toAccount, amount);
            LOGGER.info("\nTransfer Successful");
        } else LOGGER.info("\nTransfer Unsuccessful");
    }

    public static void swipeCard(ISwipeCard card, double amount) {
        if (card.swipeCard(amount)) {
            Account account = (Account) card;
            if (account.getAccountType().equals(AccountType.CREDIT)) {
                new Transaction(OnlineBankingManager.getCurrentUser(), "Credit Card Swiped", null, null, amount);
                LOGGER.info("\nCredit card payment Successful");
            } else {
                new Transaction(OnlineBankingManager.getCurrentUser(), "Debit Card Swiped", null, null, amount);
                LOGGER.info("\nDebit card payment Successful");
            }
        } else LOGGER.info("\nPayment unsuccessful");
    }

//    public static void

}
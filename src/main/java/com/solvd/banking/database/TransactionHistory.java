package com.solvd.banking.database;

import com.solvd.banking.fileHandling.FileHandling;
import com.solvd.banking.user.User;
import com.solvd.banking.user.UserData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;

public final class TransactionHistory {

    private static final Logger LOGGER = LogManager.getLogger(TransactionHistory.class);
    private static final String JSON_PATH = "src/main/resources/banking/transactionHistory.json";

    private static Map<Integer, ArrayList<Transaction>> transactionHistory = new HashMap<>();

    public static void loadHistoryFromJson() throws IOException {
        if (!FileHandling.readFile(JSON_PATH).isEmpty()) {
            transactionHistory = FileHandling.retrieveTransactionHistory(JSON_PATH);
        }
    }

    public static void updateJson() {
        FileHandling.updateTransactionHistory(JSON_PATH, transactionHistory);
    }


    public static void addNewUser(UserData userData) {
        transactionHistory.put(userData.getUser().getUserID(), new ArrayList<>());
        FileHandling.updateTransactionHistory(JSON_PATH, transactionHistory);
    }

    public static void addToHistory(User user, Transaction transaction) {
        transactionHistory.get(user.getUserID()).add(transaction);
    }

    public static int getTransactionCount(User user) {
        if (transactionHistory.get(user.getUserID()) != null) {
            return transactionHistory.get(user.getUserID()).size();
        } else return 1;
    }

    public static void showTransactionHistory(User user) {
        LOGGER.info("\n\nShowing Transaction History for: " + user.getFullName() + "\nuserID:" + user.getUserID());
        transactionHistory.get(user.getUserID())
                .forEach(transaction -> printHistory(transaction));
    }

    private static void printHistory(Transaction transaction) {
        LOGGER.info("\n__________________________________________________________");
        LOGGER.info("\nTransactionID:" + transaction.getTransactionID());
        LOGGER.info("\nTimestamp:" + transaction.getTimeOfTransaction());
        LOGGER.info("\nType:" + transaction.getType());
        LOGGER.info(String.format("\nAmount:$%.2f", transaction.getAmount()));
        if (transaction.getFromAccount() != null) {
            LOGGER.info("\nFrom:" + transaction.getFromAccount() + " Account");
        }
        if (transaction.getToAccount() != null) {
            LOGGER.info("\nTo:" + transaction.getToAccount() + " Account");
        }
    }
}
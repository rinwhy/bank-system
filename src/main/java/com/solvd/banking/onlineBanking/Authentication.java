package com.solvd.banking.onlineBanking;

import com.solvd.banking.database.Database;

public class Authentication {

    // verifies the username and password of a user trying to login
    public static boolean verifyLoginAttempt(String[] loginInfo, Database db) {
        return db.getOnlineBankingAccountList().stream()
                .anyMatch(account -> account.getUsername().equals(loginInfo[0])
                && account.getPassword().equals(loginInfo[1]));
    }

    //checks the database that the username doesn't already exist
    public static boolean checkValidUsername(String username, Database db) {
        return db.getOnlineBankingAccountList().stream()
                .noneMatch(account -> account.getUsername().equals(username));
    }
}

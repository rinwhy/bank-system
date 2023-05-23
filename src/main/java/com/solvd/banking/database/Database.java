package com.solvd.banking.database;

import com.solvd.banking.account.Account;
import com.solvd.banking.account.AccountType;
import com.solvd.banking.fileHandling.FileHandling;
import com.solvd.banking.onlineBanking.OnlineBankingAccount;
import com.solvd.banking.user.User;
import com.solvd.banking.user.UserData;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class handles the users data for the bank application
 * The stored data includes, the Users information, users bank accounts, users login credentials
 *
 * @author Paramvir Singh
 */

public class Database {

    private static final String JSON_PATH = "src/main/resources/banking/userData.json";

    private ArrayList<UserData> userDataList = new ArrayList<>();
    private final LinkedList<User> users = new LinkedList<>();
    private final LinkedList<OnlineBankingAccount> onlineAccountsList = new LinkedList<>();
    private final Map<User, LinkedList<Account>> bankAccountsMap = new HashMap<>();                    // create a hashmap to store the bank accounts under any given user account
    private final Map<User, OnlineBankingAccount> onlineAccountsMap = new HashMap<>();


    public Database() {
        try {
            initializeDB();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeDB() throws IOException {
        TransactionHistory.loadHistoryFromJson();
        if (!FileHandling.readFile(JSON_PATH).isEmpty()) {

            userDataList = FileHandling.retrieveUserData(JSON_PATH);
            userDataList.stream().forEach(userData -> {
                addNewUser(userData.getUser());
                addNewBankAccount(userData.getUser(), userData.getCheckingAccount());
                addNewBankAccount(userData.getUser(), userData.getSavingsAccount());
                addNewBankAccount(userData.getUser(), userData.getCreditAccount());
                addNewOnlineAccount(userData.getUser(), userData.getOnlineAcc());
            });
        }
    }

    public void addNewUserData(UserData userData) {
        userDataList.add(userData);
        FileHandling.addUserData(JSON_PATH, userData);
    }

    public void updateJson() {
        FileHandling.updateUserDataJson(JSON_PATH, userDataList);
    }


    /**
     * When a new user is created, they are added as keys to the maps for their respective bank accounts and online login info.
     *
     * @param user the user that is added to the database.
     */
    public void addNewUser(User user) {
        users.add(user);
        bankAccountsMap.put(user, new LinkedList<>());
    }

    /**
     * Upon creation of an account (checking, savings, credit), the account will be linked with the user who creates it.
     *
     * @param user    Specifies the user that will have a new type of bank account created.
     * @param account Specifies the types of account that is to be created.
     */
    public void addNewBankAccount(User user, Account account) {
        bankAccountsMap.get(user).add(account);
    }

    public void addNewOnlineAccount(User user, OnlineBankingAccount newAcc) {
        onlineAccountsMap.put(user, newAcc);
        onlineAccountsList.add(newAcc);
    }

    public LinkedList<OnlineBankingAccount> getOnlineBankingAccountList() {
        return onlineAccountsList;
    }


    public int getAmountOfUsers() {
        return users.size();
    }


    public User getUser(String[] loginInfo) {

        return onlineAccountsMap.entrySet().stream()
                .filter(entry -> loginInfo[0].equals(entry.getValue().getUsername()))
                .filter(entry -> loginInfo[1].equals(entry.getValue().getPassword()))
                .map(Map.Entry::getKey).collect(Collectors.toList()).get(0);
    }

    public Account getUsersCheckingAccount(User user) {

        return bankAccountsMap.get(user).stream()
                .filter(acc -> acc.getAccountType() == AccountType.CHECKING)
                .findFirst().orElse(null);
    }

    public Account getUsersSavingsAccount(User user) {

        return bankAccountsMap.get(user).stream()
                .filter(acc -> acc.getAccountType() == AccountType.SAVINGS)
                .findFirst().orElse(null);
    }

    public Account getUsersCreditAccount(User user) {

        return bankAccountsMap.get(user).stream()
                .filter(acc -> acc.getAccountType() == AccountType.CREDIT)
                .findFirst().orElse(null);
    }
}
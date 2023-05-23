package com.solvd.banking.bank;

import com.solvd.banking.account.*;
import com.solvd.banking.iFunctional.Printable;
import com.solvd.banking.user.UserBuilder;
import com.solvd.banking.database.Database;
import com.solvd.banking.database.TransactionHistory;
import com.solvd.banking.exceptions.InvalidDepositException;
import com.solvd.banking.exceptions.InvalidMenuSelection;
import com.solvd.banking.exceptions.InvalidPurchaseException;
import com.solvd.banking.exceptions.InvalidWithdrawalException;
import com.solvd.banking.onlineBanking.OnlineBanking;
import com.solvd.banking.onlineBanking.OnlineBankingAccount;
import com.solvd.banking.onlineBanking.OnlineBankingManager;
import com.solvd.banking.user.User;
import com.solvd.banking.user.UserData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;


/**
 * This class handles the main operation of the application
 *
 * @author Paramvir Singh
 */

public final class BankApp {

    private static final Logger LOGGER = LogManager.getLogger(BankApp.class);
    private final Scanner scanner = new Scanner(System.in);
    private final Bank bank = new Bank("PiggyBank", "123 Street,Nyc", "PiggyEmail@email.com", "917-345-6556");
    private final Database database = new Database();
    boolean isAppRunning = false;


    public void startBankApp() {
        isAppRunning = true;
        mainMenu(bank);
    }

    private void mainMenu(Bank bank) {
        while (isAppRunning) {
            BankAppGUI.mainMenuGUI(bank);

            String selectionString = scanner.nextLine();
            if (!validateSelection(2, selectionString)) {
                continue;
            }

            int selection = Integer.parseInt(selectionString);

            switch (selection) {
                case 1:
                    newUserSetUp();
                    break;
                case 2:
                    loginMenu();
                case 0:
                    isAppRunning = false;
                    break;
                default:
                    mainMenu(bank);
            }
        }
    }

    private void loginMenu() {
        String[] loginInfo = BankAppGUI.loginMenuGUI(scanner);

        if (OnlineBankingManager.login(loginInfo, database)) {
            LOGGER.info("\nYou have successfully logged in");
            usersHomePage();
        } else {
            LOGGER.info("\nUsername or password is incorrect!!!");
            mainMenu(bank);
        }
    }


    //region Online Banking
    private void usersHomePage() {
        while (isAppRunning) {
            BankAppGUI.usersHomePageGUI(bank, OnlineBankingManager.getCurrentUser());

            String selectionString = scanner.nextLine();
            if (!validateSelection(5, selectionString)) {
                continue;
            }
            int selection = Integer.parseInt(selectionString);

            switch (selection) {
                case 1:
                    checkingAccountMenu();
                    break;
                case 2:
                    savingsAccountMenu();
                    break;
                case 3:
                    creditAccountMenu();
                    break;
                case 4:
                    TransactionHistory.showTransactionHistory(OnlineBankingManager.getCurrentUser());
                    LOGGER.info("\n\npress enter to continue...");
                    scanner.nextLine();
                    break;
                case 5:
                    userProfileMenu();
                    break;
                case 0:
                    OnlineBankingManager.logout();
                    mainMenu(bank);
                    break;
                default:
                    usersHomePage();
            }
        }
    }

    private void checkingAccountMenu() {
        while (isAppRunning) {
            BankAppGUI.checkingAccountMenuGUI((CheckingAccount) OnlineBankingManager.getCurrentCheckingAcc());

            String selectionString = scanner.nextLine();
            if (!validateSelection(3, selectionString)) {
                continue;
            }
            int selection = Integer.parseInt(selectionString);


            double amount;
            switch (selection) {
                case 1:
                    LOGGER.info("How much would you like to withdraw? ");
                    String withdrawAmount = scanner.nextLine();
                    if (!validateWithdrawalAmount(withdrawAmount)) {
                        continue;
                    }
                    amount = Double.parseDouble(withdrawAmount);

                    OnlineBanking.withdrawFromAccount((IWithdraw) OnlineBankingManager.getCurrentCheckingAcc(), amount);
                    break;
                case 2:
                    LOGGER.info("How much would you like to Deposit? ");
                    String depositAmount = scanner.nextLine();
                    if (!validateDepositAmount(depositAmount)) {
                        continue;
                    }
                    amount = Double.parseDouble(depositAmount);

                    OnlineBanking.depositInAccount((IDeposit) OnlineBankingManager.getCurrentCheckingAcc(), amount);
                    break;
                case 3:
                    LOGGER.info("How much is the purchase? ");

                    String purchaseAmount = scanner.nextLine();
                    if (!validatePurchaseAmount(purchaseAmount)) {
                        continue;
                    }
                    amount = Double.parseDouble(purchaseAmount);

                    OnlineBanking.swipeCard((ISwipeCard) OnlineBankingManager.getCurrentCheckingAcc(), amount);
                    break;
                case 0:
                    usersHomePage();
                    break;
                default:
                    checkingAccountMenu();
            }
        }
    }

    private void savingsAccountMenu() {
        while (isAppRunning) {
            BankAppGUI.savingsAccountMenuGUI((SavingsAccount) OnlineBankingManager.getCurrentSavingAcc());

            String selectionString = scanner.nextLine();
            if (!validateSelection(2, selectionString)) {
                continue;
            }
            int selection = Integer.parseInt(selectionString);

            double amount;
            switch (selection) {
                case 1:
                    LOGGER.info("How much would you like to withdraw? ");
                    String withdrawAmount = scanner.nextLine();
                    if (!validateWithdrawalAmount(withdrawAmount)) {
                        continue;
                    }
                    amount = Double.parseDouble(withdrawAmount);

                    OnlineBanking.withdrawFromAccount((IWithdraw) OnlineBankingManager.getCurrentSavingAcc(), amount);
                    break;
                case 2:
                    LOGGER.info("How much would you like to Deposit? ");
                    String depositAmount = scanner.nextLine();
                    if (!validateDepositAmount(depositAmount)) {
                        continue;
                    }
                    amount = Double.parseDouble(depositAmount);

                    OnlineBanking.depositInAccount((IDeposit) OnlineBankingManager.getCurrentSavingAcc(), amount);
                    break;
                case 0:
                    usersHomePage();
                    break;
                default:
                    savingsAccountMenu();
            }
        }
    }

    private void creditAccountMenu() {
        while (isAppRunning) {
            BankAppGUI.creditAccountMenuGUI((CreditAccount) OnlineBankingManager.getCurrentCreditAcc());

            String selectionString = scanner.nextLine();
            if (!validateSelection(3, selectionString)) {
                continue;
            }

            int selection = Integer.parseInt(selectionString);

            double amount;

            switch (selection) {
                case 1:
                    LOGGER.info("How much would you like to pay? $");
                    amount = Double.parseDouble(scanner.nextLine());
                    OnlineBanking.payCCBalance((ITransferable) OnlineBankingManager.getCurrentCheckingAcc(), (CreditAccount) OnlineBankingManager.getCurrentCreditAcc(), amount);
                    creditAccountMenu();
                    break;
                case 2:
                    LOGGER.info("How much would you like to pay? $");
                    amount = Double.parseDouble(scanner.nextLine());
                    OnlineBanking.payCCBalance((ITransferable) OnlineBankingManager.getCurrentSavingAcc(), (CreditAccount) OnlineBankingManager.getCurrentCreditAcc(), amount);
                    creditAccountMenu();
                    break;
                case 3:
                    LOGGER.info("How much is the purchase? ");
                    String purchaseAmount = scanner.nextLine();
                    if (!validatePurchaseAmount(purchaseAmount)) {
                        continue;
                    }
                    amount = Double.parseDouble(purchaseAmount);
                    OnlineBanking.swipeCard((ISwipeCard) OnlineBankingManager.getCurrentCreditAcc(), amount);
                    creditAccountMenu();
                case 0:
                    usersHomePage();
                    break;
                default:
                    creditAccountMenu();
            }
        }
    }

    private void userProfileMenu() {
        while (isAppRunning) {
            BankAppGUI.userProfileMenuGUI(OnlineBankingManager.getCurrentUser());

            String selectionString = scanner.nextLine();
            if (!validateSelection(1, selectionString)) {
                continue;
            }

            int selection = Integer.parseInt(selectionString);

            switch (selection) {
                case 1:
                    userProfileEditMenu();
                    break;
                case 0:
                    usersHomePage();
                    break;
                default:
                    userProfileMenu();
            }
        }
    }

    private void userProfileEditMenu() {
        while (isAppRunning) {
            BankAppGUI.userProfileEditorGUI(OnlineBankingManager.getCurrentUser());

            String selectionString = scanner.nextLine();
            if (!validateSelection(4, selectionString)) {
                continue;
            }

            int selection = Integer.parseInt(selectionString);

            switch (selection) {
                case 1:     //edit name
                    OnlineBankingManager.updateUserInfo(OnlineBankingManager.getCurrentUser(), "fullName", scanner);
                    break;
                case 2:     //edit age
                    OnlineBankingManager.updateUserInfo(OnlineBankingManager.getCurrentUser(), "age", scanner);
                    break;
                case 3:     //edit address
                    OnlineBankingManager.updateUserInfo(OnlineBankingManager.getCurrentUser(), "address", scanner);
                    break;
                case 4:     // edit phone number
                    OnlineBankingManager.updateUserInfo(OnlineBankingManager.getCurrentUser(), "phoneNumber", scanner);
                    break;
                case 0:
                    userProfileMenu();
                default:
            }

        }
    }
    //endregion


    //region Setting up a new user and their accounts
    private void newUserSetUp() {
        UserData userData = new UserData();

        userData.setUser(createUser());
        userData.setCheckingAccount(createCheckingAcc());
        userData.setSavingsAccount(createSavingsAcc());
        userData.setCreditAccount(createCreditAcc());
        userData.setOnlineAcc(createOnlineAcc());

        database.addNewUserData(userData);
        TransactionHistory.addNewUser(userData);

        loginMenu();
    }


    private User createUser() {
        User newUser = new UserBuilder().createUser(database, scanner);

        OnlineBankingManager.setCurrentUser(newUser);
        database.addNewUser(newUser);
        return newUser;
    }

    private OnlineBankingAccount createOnlineAcc() {
        boolean validUsername = false;
        String[] loginInfo = {""};

        while (!validUsername) {
            loginInfo = BankAppGUI.createOnlineAccGUI(scanner);
            if (OnlineBankingManager.createOnlineAccount(loginInfo, database)) {
                validUsername = true;
            }
        }
        OnlineBankingAccount newAcc = new OnlineBankingAccount(loginInfo[0], loginInfo[1]);
        database.addNewOnlineAccount(OnlineBankingManager.getCurrentUser(), newAcc);
        return newAcc;
    }

    private CheckingAccount createCheckingAcc() {
        BankAppGUI.openNewCheckingGUI();
        int amount = Integer.parseInt(scanner.nextLine());
        CheckingAccount newAcc = new CheckingAccount(OnlineBankingManager.getCurrentUser(), amount, database);
        newAcc.setMinBalance(500);
        newAcc.setMaxWithdrawal(10000);

        LOGGER.info("\n__________________________________________________________");
        LOGGER.info("\nYour checking account was successfully created");

        return newAcc;
    }

    private SavingsAccount createSavingsAcc() {
        BankAppGUI.openNewSavingsGUI();
        double amount = Integer.parseInt(scanner.nextLine());
        SavingsAccount newAcc = new SavingsAccount(OnlineBankingManager.getCurrentUser(), amount, database);
        newAcc.setMinBalance(200);
        newAcc.setMaxWithdrawal(10000);
        newAcc.setInterestRate(0.02f);

        LOGGER.info("\n__________________________________________________________");
        LOGGER.info("\nYour savings account was successfully created");

        return newAcc;
    }

    private CreditAccount createCreditAcc() {
        BankAppGUI.openNewCreditGUI();
        CreditAccount newAcc = new CreditAccount(OnlineBankingManager.getCurrentUser(), database);
        newAcc.setCreditLimit(8000);
        newAcc.setRemainingCredit(newAcc.getCreditLimit());

        LOGGER.info("\n__________________________________________________________");
        LOGGER.info("\nYour accounts have been successfully created, please login to manage your accounts");

        return newAcc;
    }
    //endregion


    private boolean validateSelection(int selectionLimit, String selection) {
        try {
            int selectionInt = Integer.parseInt(selection);
            if (selectionInt > selectionLimit || selectionInt < 0) {
                throw new InvalidMenuSelection("Please choose from the available options");
            } else {
                return true;
            }
        } catch (NumberFormatException | InvalidMenuSelection e) {
            LOGGER.warn("Invalid selection: " + e);
            return false;
        }
    }

    private boolean validateDepositAmount(String enteredAmount) {
        try {
            double amount = Double.parseDouble(enteredAmount);
            if (amount < 0) {
                throw new InvalidDepositException("Please enter a valid amount to deposit");
            } else {
                return true;
            }
        } catch (NumberFormatException | InvalidDepositException e) {
            LOGGER.warn("Invalid Deposit: " + e);
            return false;
        }
    }

    private boolean validateWithdrawalAmount(String enteredAmount) {
        try {
            double amount = Double.parseDouble(enteredAmount);
            if (amount < 0) {
                throw new InvalidWithdrawalException("Please enter a valid amount to withdraw");
            } else {
                return true;
            }
        } catch (NumberFormatException | InvalidWithdrawalException e) {
            LOGGER.warn("Invalid Withdrawal: " + e);
            return false;
        }
    }

    private boolean validatePurchaseAmount(String enteredAmount) {
        try {
            double amount = Double.parseDouble(enteredAmount);
            if (amount < 0) {
                throw new InvalidPurchaseException("Please enter a valid amount");
            } else {
                return true;
            }
        } catch (NumberFormatException | InvalidPurchaseException e) {
            LOGGER.warn("Invalid Purchase: " + e);
            return false;
        }
    }

    public void shutdown() {
        database.updateJson();
        TransactionHistory.updateJson();

        Printable print = () -> LOGGER.info("Closing Application ... GoodBye");
        print.print();
    }
}
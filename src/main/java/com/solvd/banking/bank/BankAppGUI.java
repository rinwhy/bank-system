package com.solvd.banking.bank;

import com.solvd.banking.account.*;
import com.solvd.banking.onlineBanking.OnlineBanking;
import com.solvd.banking.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

/**
 * This class contains all the strings for creating a console interface for user input
 *
 * @author Paramvir Singh
 */

public final class BankAppGUI {

    private static final Logger LOGGER = LogManager.getLogger(BankAppGUI.class);

    public static void mainMenuGUI(Bank bank) {
        LOGGER.info("\n__________________________________________________________");
        LOGGER.info("\n            Welcome to the " + bank.getBankName());
        LOGGER.info("\n            Address:" + bank.getBankAddress());
        LOGGER.info("\n            Email:" + bank.getBankEmail());
        LOGGER.info("\n            Phone:" + bank.getBankPhoneNumber());
        LOGGER.info("\n__________________________________________________________");
        LOGGER.info("\n[1] Create a new bank account");
        LOGGER.info("\n[2] Log in");
        LOGGER.info("\n\n[0] Quit App\n");
    }

    public static String[] loginMenuGUI(Scanner scanner) {
        String[] loginInfo = new String[2];
        LOGGER.info("\n__________________________________________________________");
        LOGGER.info("\nOnline banking login");
        LOGGER.info("\nUsername:");
        loginInfo[0] = scanner.nextLine();
        LOGGER.info("Password:");
        loginInfo[1] = scanner.nextLine();
        LOGGER.info("__________________________________________________________");
        return loginInfo;
    }

    public static String[] createOnlineAccGUI(Scanner scanner) {
        String[] loginInfo = new String[2];
        LOGGER.info("\n__________________________________________________________");
        LOGGER.info("\nCreating a new Online Banking Account");
        LOGGER.info("\nEnter a new Username:");
        loginInfo[0] = scanner.nextLine();
        LOGGER.info("Enter a new Password:");
        loginInfo[1] = scanner.nextLine();
        LOGGER.info("__________________________________________________________");
        return loginInfo;
    }

    public static void openNewCheckingGUI() {
        LOGGER.info("\n__________________________________________________________");
        LOGGER.info("\nChecking Account Information");
        LOGGER.info(CheckingAccount.generalInformation());
        LOGGER.info("\n\nPlease deposit a starting balance: $");
    }

    public static void openNewSavingsGUI() {
        LOGGER.info("\n__________________________________________________________");
        LOGGER.info("\nSavings Account Information");
        LOGGER.info(SavingsAccount.generalInformation());
        LOGGER.info("\n\nPlease deposit a starting balance: $");
    }

    public static void openNewCreditGUI() {
        LOGGER.info("\n__________________________________________________________");
        LOGGER.info("\nCredit Account Information");
        LOGGER.info(CreditAccount.generalInformation());
    }

    public static void usersHomePageGUI(Bank bank, User user) {
        LOGGER.info("\n__________________________________________________________");
        LOGGER.info("\n            " + bank.getBankName() + " Online Banking\n");
        LOGGER.info("\nWelcome, " + user.getFullName() + "\n");
        LOGGER.info("\n[1] Checking account");
        LOGGER.info("\n[2] Savings account");
        LOGGER.info("\n[3] Credit account");
        LOGGER.info("\n[4] View Transaction History");
        LOGGER.info("\n\n[5] Manage user profile");
        LOGGER.info("\n[0] Logout\n");
    }

    public static void checkingAccountMenuGUI(CheckingAccount checkingAccount) {
        LOGGER.info("\n__________________________________________________________");
        LOGGER.info("\nChecking Account");
        OnlineBanking.printAccountBalance(checkingAccount);
        LOGGER.info("\n\n[1] Make a withdrawal");
        LOGGER.info("\n[2] Make a Deposit");
        LOGGER.info("\n[3] Make purchase with Debit card");
        LOGGER.info("\n\n[0] Back\n");
    }

    public static void savingsAccountMenuGUI(SavingsAccount savingsAccount) {
        LOGGER.info("\n__________________________________________________________");
        LOGGER.info("\nSavings Account");
        OnlineBanking.printAccountBalance(savingsAccount);
        LOGGER.info("\n\n[1] Make a withdrawal");
        LOGGER.info("\n[2] Make a Deposit");
        LOGGER.info("\n\n[0] Back\n");
    }

    public static void creditAccountMenuGUI(CreditAccount creditAccount) {
        LOGGER.info("\n__________________________________________________________");
        LOGGER.info("\nCredit Account");
        OnlineBanking.printAccountBalance(creditAccount);
        LOGGER.info("\n\n[1] Make a payment from checking account");
        LOGGER.info("\n[2] Make a payment from savings account");
        LOGGER.info("\n[3] Make a purchase with credit card");
        LOGGER.info("\n\n[0] Back\n");
    }

    public static void userProfileMenuGUI(User user) {
        LOGGER.info("\n__________________________________________________________");
        LOGGER.info("\n            User Profile");
        LOGGER.info("\n\nName: " + user.getFullName());
        LOGGER.info("\nAge: " + user.getAge());
        LOGGER.info("\nAddress: " + user.getAddress());
        LOGGER.info("\nPhone number: " + user.getPhoneNumber());
        LOGGER.info("\n\n[1] Edit profile");
        LOGGER.info("\n[0] back\n");
    }

    public static void userProfileEditorGUI(User user) {
        LOGGER.info("\n__________________________________________________________");
        LOGGER.info("\nWhat information would you like to edit?");
        LOGGER.info("\n\n[1] Name: " + user.getFullName());
        LOGGER.info("\n[2] Age: " + user.getAge());
        LOGGER.info("\n[3] Address: " + user.getAddress());
        LOGGER.info("\n[4] Phone number: " + user.getPhoneNumber());

        LOGGER.info("\n\n[0] back\n");
    }

}
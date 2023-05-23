package com.solvd.banking.user;

import com.solvd.banking.database.Database;
import com.solvd.banking.exceptions.NameException;
import com.solvd.banking.exceptions.UnderAgeException;

import com.solvd.banking.iFunctional.AgeChecker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.function.Predicate;


public class UserBuilder {

    private static final Logger LOGGER = LogManager.getLogger(UserBuilder.class);

    public User createUser(Database db, Scanner scanner) {
        //creating the user and setting it inside UserData
        User user = new User();
        getUserInfoFromConsole(db, scanner, user);

        return user;
    }

    private void getUserInfoFromConsole(Database db, Scanner scanner, User user) {
        LOGGER.info("\n__________________________________________________________");
        LOGGER.info("\nPlease enter the following information:");
        updateName(scanner, user);
        updateAge(scanner, user);
        updateAddress(scanner, user);
        updatePhoneNumber(scanner, user);

        user.setUserID(db.getAmountOfUsers() + 1);
    }


    //recursive method, will throw exceptions for invalid names
    private void updateName(Scanner scanner, User user) throws NameException {
        try {
            LOGGER.info("\nName:");
            String name = scanner.nextLine();

            Predicate<String> myPredicate = (str) -> str.matches("^[a-zA-Z ]+$");
            if (myPredicate.test(name)) {
                user.setFullName(name);
            } else {
                throw new NameException("Name should only contain letters");
            }
        } catch (NumberFormatException | NameException e) {
            LOGGER.warn("Invalid Name! " + e);
            updateName(scanner, user);
        }
    }


    private void updateAge(Scanner scanner, User user) throws UnderAgeException {
        try {
            LOGGER.info("Age:");
            int ageInput = Integer.parseInt(scanner.nextLine());

            AgeChecker checker = age -> age >= 18;

            if (checker.checkAge(ageInput)) {
                user.setAge(ageInput);
            } else {
                throw new UnderAgeException("You must be at least 18 to create a bank account");
            }
        } catch (NumberFormatException | UnderAgeException e) {
            LOGGER.warn("Age not accepted: " + e);
            updateAge(scanner, user);
        }
    }

    private void updateAddress(Scanner scanner, User user) {
        LOGGER.info("Address:");
        user.setAddress(scanner.nextLine());
    }

    private void updatePhoneNumber(Scanner scanner, User user) {
        LOGGER.info("Phone#:");
        user.setPhoneNumber(scanner.nextLine());
    }

}

package com.solvd.banking.onlineBanking;

import com.solvd.banking.account.Account;
import com.solvd.banking.database.Database;
import com.solvd.banking.user.User;
import com.solvd.banking.user.UserBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class OnlineBankingManager {

    private static final Logger LOGGER = LogManager.getLogger(OnlineBankingManager.class);

    private static User currentUser;            // user that is currently logged in
    private static Account currentCheckingAcc;
    private static Account currentSavingAcc;
    private static Account currentCreditAcc;


    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        OnlineBankingManager.currentUser = currentUser;
    }

    public static Account getCurrentCheckingAcc() {
        return currentCheckingAcc;
    }

    public static void setCurrentCheckingAcc(Account currentCheckingAcc) {
        OnlineBankingManager.currentCheckingAcc = currentCheckingAcc;
    }

    public static Account getCurrentSavingAcc() {
        return currentSavingAcc;
    }

    public static void setCurrentSavingAcc(Account currentSavingAcc) {
        OnlineBankingManager.currentSavingAcc = currentSavingAcc;
    }

    public static Account getCurrentCreditAcc() {
        return currentCreditAcc;
    }

    public static void setCurrentCreditAcc(Account currentCreditAcc) {
        OnlineBankingManager.currentCreditAcc = currentCreditAcc;
    }

    public static boolean login(String[] loginInfo, Database database) {
        //if login is successful, update the user and account data
        if (Authentication.verifyLoginAttempt(loginInfo, database)) {
            setCurrentUser(database.getUser(loginInfo));
            setCurrentCheckingAcc(database.getUsersCheckingAccount(currentUser));
            setCurrentSavingAcc(database.getUsersSavingsAccount(currentUser));
            setCurrentCreditAcc(database.getUsersCreditAccount(currentUser));
            return true;
        }
        return false;
    }

    public static void logout() {
        setCurrentUser(null);
        setCurrentSavingAcc(null);
        setCurrentCheckingAcc(null);
        setCurrentCreditAcc(null);
    }

    public static boolean createOnlineAccount(String[] loginInfo, Database database) {

        if (Authentication.checkValidUsername(loginInfo[0], database)) {
            LOGGER.info("\nOnline account created successfully");
            return true;
        } else {
            LOGGER.info("\nAccount creation failed!! This username already exists!");
            return false;
        }
    }

    public static void updateUserInfo(User user, String fieldString, Scanner scanner) {

        Object userBuilderObj = new UserBuilder();
        List<Method> builderMethods = Arrays.stream(UserBuilder.class.getDeclaredMethods()).collect(Collectors.toList());
        List<Field> fieldList = Arrays.stream(user.getClass().getDeclaredFields()).collect(Collectors.toList());

        Field fieldToUpdate = fieldList.stream()
                .filter(field -> field.getName().equals(fieldString))
                .collect(Collectors.toList()).get(0);

        Method method;

        switch (fieldToUpdate.getName()) {
            case ("fullName"):
                method = builderMethods.stream()
                        .filter(streamMethod -> streamMethod.getName().equals("updateName"))
                        .collect(Collectors.toList()).get(0);
                try {
                    method.setAccessible(true);
                    method.invoke(userBuilderObj, scanner, user);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    LOGGER.error("Error invoking the method " + e);
                }
                break;

            case "age":
                method = builderMethods.stream()
                        .filter(streamMethod -> streamMethod.getName().equals("updateAge"))
                        .collect(Collectors.toList()).get(0);
                try {
                    method.setAccessible(true);
                    method.invoke(userBuilderObj, scanner, user);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    LOGGER.error("Error invoking the method " + e);
                }
                break;

            case "address":
                method = builderMethods.stream()
                        .filter(streamMethod -> streamMethod.getName().equals("updateAddress"))
                        .collect(Collectors.toList()).get(0);
                try {
                    method.setAccessible(true);
                    method.invoke(userBuilderObj, scanner, user);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    LOGGER.error("Error invoking the method " + e);
                }
                break;

            case "phoneNumber":
                method = builderMethods.stream()
                        .filter(streamMethod -> streamMethod.getName().equals("updatePhoneNumber"))
                        .collect(Collectors.toList()).get(0);
                try {
                    method.setAccessible(true);
                    method.invoke(userBuilderObj, scanner, user);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    LOGGER.error("Error invoking the method " + e);
                }
                break;
            default:
                break;
        }
    }

}

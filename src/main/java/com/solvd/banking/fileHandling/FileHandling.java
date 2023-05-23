package com.solvd.banking.fileHandling;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.solvd.banking.database.Transaction;
import com.solvd.banking.user.UserData;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;


public class FileHandling {

    public static final Logger LOGGER = LogManager.getLogger(FileHandling.class);

    private static final Type USER_DATA_TYPE_TOKEN = new TypeToken<ArrayList<UserData>>() {}.getType();
    private static final Type TRANSACTION_TYPE_TOKEN = new TypeToken<Map<Integer, ArrayList<Transaction>>>() {}.getType();
    private static ArrayList<UserData> userDataList = new ArrayList<>();

    public static String readFile(String filePath) {
        try {
            return FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("Error reading the file" , e);
        }
        return null;
    }

    public static void writeFile(String filePath, String content, boolean append) {
        try {
            FileUtils.writeStringToFile(new File(filePath), content, append);
        } catch (IOException e) {
            LOGGER.error("Error writing to the file" , e);
        }
    }

    public static void addUserData(String filePath, UserData userData) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonContent = readFile(filePath);

        if (jsonContent.isEmpty()) {
            userDataList.add(userData);
        } else {
            userDataList = gson.fromJson(jsonContent, USER_DATA_TYPE_TOKEN);
            userDataList.add(userData);
        }

        jsonContent = gson.toJson(userDataList);
        writeFile(filePath, jsonContent, false);
    }

    public static void updateUserDataJson(String filePath, ArrayList<UserData> userDataList) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String jsonContent = gson.toJson(userDataList);
        writeFile(filePath, jsonContent, false);
    }

    public static ArrayList<UserData> retrieveUserData(String filePath) throws IOException {

        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(readFile(filePath), JsonArray.class);

        ArrayList<UserData> retrievedList;
        retrievedList = gson.fromJson(jsonArray, USER_DATA_TYPE_TOKEN);

        return retrievedList;
    }


    public static void updateTransactionHistory(String filePath, Map<Integer, ArrayList<Transaction>> transactionHistory) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String jsonContent = gson.toJson(transactionHistory);
        writeFile(filePath, jsonContent, false);
    }

    public static Map<Integer, ArrayList<Transaction>> retrieveTransactionHistory(String filePath) throws IOException {

        Gson gson = new Gson();

        Map<Integer, ArrayList<Transaction>> retrievedMap;
        retrievedMap = gson.fromJson(readFile(filePath), TRANSACTION_TYPE_TOKEN);

        return retrievedMap;
    }

}

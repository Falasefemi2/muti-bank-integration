package com.example.bank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

public class BankService {
    public static void saveAccountToFile(BankAccount account, String filename) {
        JSONObject json = account.seriliaze();
        try (FileWriter file = new FileWriter(filename)) {
            file.write(json.toString(2));
            System.out.println("Account saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving account: " + e.getMessage());
        }
    }

    public static BankAccount loadAccountFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            JSONObject obj = new JSONObject(jsonString.toString());
            return BankAccount.deserialize(obj);
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
        return null;
    }

}
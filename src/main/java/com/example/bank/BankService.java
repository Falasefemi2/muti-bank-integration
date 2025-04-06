package com.example.bank;

import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

public class BankService {
    public static void saveAccountToFile(BankAccount account, String filename) {
        JSONObject json = account.serialize();
        try (FileWriter file = new FileWriter(filename)) {
            file.write(json.toString(2));
            System.out.println("Account saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving account: " + e.getMessage());
        }
    }

    public static BankAccount loadAccountFromFile(String filename) {
        // Implement file reading and deserialization
        // Omitted for brevity, similar to previous example
        return null; // Replace with full implementation
    }

}

package com.example.transaction;

import java.time.LocalDateTime;

import org.json.JSONObject;

public class Transaction implements TransactionInterface {
    private double amount;
    private String description;
    private LocalDateTime timestamp;
    private String type;

    // Constructor
    public Transaction(double amount, String description, String type) {
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    public String getType() {
        return type;
    }

    @Override
    public JSONObject serialize() {
        JSONObject json = new JSONObject();
        json.put("amount", amount);
        json.put("description", description);
        json.put("timestamp", timestamp.toString());
        json.put("type", type);
        return json;
    }

    public static Transaction deserialize(JSONObject json) {
        return new Transaction(
                json.getDouble("amount"),
                json.getString("description"),
                json.getString("type"));
    }

}

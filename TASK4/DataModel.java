package com.example.recommendation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class DataModel {
    private Map<String, Map<String, Integer>> userPreferences;

    public DataModel() {
        userPreferences = new HashMap<>();
    }

    public void addPreference(String user, String item, int rating) {
        userPreferences.computeIfAbsent(user, k -> new HashMap<>()).put(item, rating);
    }

    public Map<String, Integer> getUserPreferences(String user) {
        return userPreferences.getOrDefault(user, new HashMap<>());
    }

    public Map<String, Map<String, Integer>> getAllUserPreferences() {
        return userPreferences;
    }

    public void saveToFile(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            new Gson().toJson(userPreferences, writer);
        }
    }

    public void loadFromFile(String fileName) throws IOException {
        try (FileReader reader = new FileReader(fileName)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Map<String, Integer>>>() {
            }.getType();
            userPreferences = gson.fromJson(reader, type);
            System.out.println("Loaded user preferences: " + userPreferences); // Debugging

        }
    }
}

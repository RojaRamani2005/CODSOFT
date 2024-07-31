package com.example.recommendation;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DataModel dataModel = new DataModel();

        try {
            dataModel.loadFromFile("userPreferences.json");
        } catch (IOException e) {
            System.err.println("Error loading user preferences: " + e.getMessage());
            return;
        }

        CollaborativeFiltering recommender = new CollaborativeFiltering(dataModel);

        List<String> recommendations = recommender.recommend("Alice", 2);

        System.out.println("Recommendations for Alice: " + recommendations);
    }
}

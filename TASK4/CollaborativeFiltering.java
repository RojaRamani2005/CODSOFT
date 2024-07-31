package com.example.recommendation;

import java.util.*;
import java.util.stream.Collectors;

public class CollaborativeFiltering {
    private final DataModel dataModel;

    public CollaborativeFiltering(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    public double cosineSimilarity(Map<String, Integer> userPreferences1, Map<String, Integer> userPreferences2) {
        Set<String> commonItems = userPreferences1.keySet().stream()
                .filter(userPreferences2::containsKey)
                .collect(Collectors.toSet());

        if (commonItems.isEmpty()) {
            return 0.0;
        }

        double dotProduct = commonItems.stream()
                .mapToDouble(item -> userPreferences1.get(item) * userPreferences2.get(item))
                .sum();

        double magnitude1 = Math.sqrt(userPreferences1.values().stream()
                .mapToDouble(rating -> rating * rating)
                .sum());

        double magnitude2 = Math.sqrt(userPreferences2.values().stream()
                .mapToDouble(rating -> rating * rating)
                .sum());

        return dotProduct / (magnitude1 * magnitude2);
    }

    public List<String> recommend(String user, int k) {
        Map<String, Integer> userPreferences = dataModel.getUserPreferences(user);
        if (userPreferences.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, Double> similarityScores = new HashMap<>();
        for (String otherUser : dataModel.getAllUserPreferences().keySet()) {
            if (!otherUser.equals(user)) {
                double similarity = cosineSimilarity(userPreferences, dataModel.getUserPreferences(otherUser));
                similarityScores.put(otherUser, similarity);
            }
        }

        System.out.println("Similarity Scores: " + similarityScores); // Debugging

        // Get the top K similar users
        List<String> topKUsers = similarityScores.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(k)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        System.out.println("Top K Users: " + topKUsers); // Debugging

        // Aggregate the items rated by the top K similar users
        Map<String, Double> recommendedItems = new HashMap<>();
        for (String similarUser : topKUsers) {
            Map<String, Integer> similarUserPreferences = dataModel.getUserPreferences(similarUser);
            for (Map.Entry<String, Integer> entry : similarUserPreferences.entrySet()) {
                String item = entry.getKey();
                int rating = entry.getValue();

                // Only consider items that the target user has not rated
                if (!userPreferences.containsKey(item)) {
                    recommendedItems.merge(item, rating * similarityScores.get(similarUser), Double::sum);
                    System.out.println("Item: " + item + ", Rating: " + rating + ", Similarity: " + similarityScores.get(similarUser)); // Debugging
                }
            }
        }

        System.out.println("Recommended Items Before Sorting: " + recommendedItems); // Debugging

        // Sort items by their aggregated score in descending order
        return recommendedItems.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}

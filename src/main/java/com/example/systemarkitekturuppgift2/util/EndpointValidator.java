package com.example.systemarkitekturuppgift2.util;

import com.example.systemarkitekturuppgift2.entities.Category;

public final class EndpointValidator {
    private EndpointValidator() {}

    public static boolean isValidCategory(Category category) {
        // Check if the given category is one of the valid categories from the enum
        for (Category validCategory : Category.values()) {
            if (validCategory == category) {
                return true;
            }
        }
        return false;
    }
}

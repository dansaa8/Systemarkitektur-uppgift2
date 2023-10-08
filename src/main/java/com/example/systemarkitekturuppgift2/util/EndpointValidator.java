package com.example.systemarkitekturuppgift2.util;

import com.example.systemarkitekturuppgift2.entities.Category;
import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public static LocalDate parseDate(String dateParam) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateParam, formatter);
        } catch (Exception e) {
            return null; // Return null if parsing fails
        }
    }
}

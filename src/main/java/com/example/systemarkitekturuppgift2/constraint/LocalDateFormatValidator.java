package com.example.systemarkitekturuppgift2.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateFormatValidator implements ConstraintValidator<ValidLocalDateFormat, LocalDate> {
    private static final String DATE_FORMAT = "yyyy-MM-dd"; // Modify this to match your desired date format

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if (date == null) {
            return true;  // Let other validation handle null values
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            LocalDate.parse(date.toString(), formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

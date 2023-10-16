package com.example.systemarkitekturuppgift2.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateFormatValidator implements ConstraintValidator<ValidLocalDateFormat, LocalDate> {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if (date == null) {
            return true;  // Let other validation handle null values
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            LocalDate parsedDate = LocalDate.parse(date.toString(), formatter);

            int year = parsedDate.getYear();
            int month = parsedDate.getMonthValue();
            int day = parsedDate.getDayOfMonth();

            if (year >= 1900 && year <= 2099 && month >= 1 && month <= 12 && day >= 1 && day <= 31) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

}

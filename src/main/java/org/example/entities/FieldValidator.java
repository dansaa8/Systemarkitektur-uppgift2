package org.example.entities;

public final class FieldValidator {
    private FieldValidator() {}

    public static boolean isValid(ProductRecord p) {
        if (p == null) return false;

        return isInRange(p.rating()) &&
                !isNullOrEmpty(p.name()) &&
                p.id() >= 0;
    }

    private static boolean isInRange(int rating) {
        return rating >= 0 && rating <= 10;
    }

    private static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}

package com.petstoremanagement.Global;

import java.io.File;

public class ServiceValidate {
    public static boolean isValidPrice(String price) {
        if (price == null || price.isEmpty()) {
            return false;
        }
        try {
            double parsedPrice = Double.parseDouble(price);
            return parsedPrice > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNotEmpty(String field) {
        return field != null && !field.trim().isEmpty();
    }
}

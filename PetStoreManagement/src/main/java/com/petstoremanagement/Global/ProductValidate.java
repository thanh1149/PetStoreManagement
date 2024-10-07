package com.petstoremanagement.Global;

public class ProductValidate {
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

    public static boolean isValidQuantity(String quantity) {
        if (quantity == null || quantity.isEmpty()) {
            return false;
        }
        try {
            int parsedQuantity = Integer.parseInt(quantity);
            return parsedQuantity > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNotEmpty(String field) {
        return field != null && !field.trim().isEmpty();
    }
}

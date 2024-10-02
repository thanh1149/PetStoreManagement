package com.petstoremanagement.Global;

import java.util.regex.Pattern;

public class StaffValidate {

    // Validate email format
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }

    // Validate phone number (must be a number and contain exactly 10 digits)
    public static boolean isValidPhone(String phone) {
        String phoneRegex = "^\\d{10}$";
        return phone != null && phone.matches(phoneRegex);
    }

    // Validate password (must be at least 5 characters)
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 5;
    }

    // Validate that a field is not empty
    public static boolean isNotEmpty(String field) {
        return field != null && !field.trim().isEmpty();
    }
}

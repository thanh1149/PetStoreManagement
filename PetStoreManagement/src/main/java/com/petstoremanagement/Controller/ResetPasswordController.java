package com.petstoremanagement.Controller;

import com.petstoremanagement.Global.StaffValidate;
import com.petstoremanagement.Service.StaffService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class ResetPasswordController {

    @FXML
    private Label staffIdLabel;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button resetButton;
    @FXML
    private Label messageLabel;

    private String email;
    private StaffService staffService = new StaffService();

    @FXML
    public void initialize() {
        resetButton.setOnAction(event -> handleResetPassword());
    }

    public void setEmail(String email) {
        this.email = email;
        int staffId = staffService.getStaffIdByEmail(email);
        staffIdLabel.setText("Staff ID: " + staffId);
    }

    private void handleResetPassword() {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate password match
        if (!newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match.");
            return;
        }

        // Validate password strength
        if (!StaffValidate.isValidPassword(newPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password must be at least 5 characters long.");
            return;
        }

        // Perform password reset
        boolean success = staffService.updatePassword(email, newPassword);
        if (success) {
            staffService.clearResetToken(email);  // Clear the reset token
            showAlert(Alert.AlertType.INFORMATION, "Success", "Password reset successfully.");
            redirectToLogin();  // Redirect after showing success alert
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to reset password. Please try again.");
        }
    }

    // Method to show an alert dialog
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();  // This blocks until the alert is dismissed
    }

    // Method to redirect to the login page
    private void redirectToLogin() {
        try {
            // Ensure that the FXML file exists at the correct location
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/petstoremanagement/login.fxml"));
            Parent root = loader.load();

            // Get the current stage and change the scene to the login page
            Stage stage = (Stage) resetButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error returning to the login screen. Please check the FXML path.");
        }
    }

}

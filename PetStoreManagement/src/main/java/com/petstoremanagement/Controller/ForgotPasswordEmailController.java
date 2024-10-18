package com.petstoremanagement.Controller;

import com.petstoremanagement.Service.EmailService;
import com.petstoremanagement.Service.StaffService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgotPasswordEmailController {

    @FXML
    private TextField emailField;
    @FXML
    private Button submitButton;
    @FXML
    private Label messageLabel;

    private StaffService staffService = new StaffService();
    private EmailService emailService = new EmailService();

    @FXML
    public void initialize() {
        submitButton.setOnAction(event -> handleSubmit());
    }

    private void handleSubmit() {
        String email = emailField.getText();
        if (staffService.isEmailRegistered(email)) {
            String token = generateToken();
            if (staffService.saveResetToken(email, token)) {
                emailService.sendResetToken(email, token);
                messageLabel.setText("A reset token has been sent to your email.");
                openTokenInputScreen(email);
            } else {
                messageLabel.setText("An error occurred. Please try again.");
            }
        } else {
            messageLabel.setText("Email not found in our records.");
        }
    }

    private String generateToken() {
        return String.format("%06d", (int)(Math.random() * 1000000));
    }

    private void openTokenInputScreen(String email) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/petstoremanagement/view/forgot_password/forgot_password_token.fxml"));
            Parent root = loader.load();

            ForgotPasswordTokenController controller = loader.getController();
            controller.setEmail(email);

            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Error opening token input screen.");
        }
    }
}
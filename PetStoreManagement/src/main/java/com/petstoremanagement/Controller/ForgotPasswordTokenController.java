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

public class ForgotPasswordTokenController {

    @FXML
    private TextField tokenField;
    @FXML
    private Button submitButton;
    @FXML
    private Label messageLabel;

    private String email;
    private int attempts = 0;
    private StaffService staffService = new StaffService();

    @FXML
    public void initialize() {
        submitButton.setOnAction(event -> handleSubmit());
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private void handleSubmit() {
        String enteredToken = tokenField.getText();

        if (staffService.verifyResetToken(email, enteredToken)) {
            openResetPasswordScreen();
        } else {
            attempts++;
            if (attempts >= 3) {
                String newToken = generateNewToken();
                if (staffService.saveResetToken(email, newToken)) {
                    new EmailService().sendResetToken(email, newToken);
                    messageLabel.setText("Too many attempts. A new token has been sent to your email.");
                } else {
                    messageLabel.setText("An error occurred. Please try again later.");
                }
            } else {
                messageLabel.setText("Incorrect token. Attempts remaining: " + (3 - attempts));
            }
        }
    }

    private String generateNewToken() {
        return String.format("%06d", (int)(Math.random() * 1000000));
    }

    private void openResetPasswordScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/petstoremanagement/view/forgot_password/reset_password.fxml"));
            Parent root = loader.load();

            ResetPasswordController controller = loader.getController();
            controller.setEmail(email);

            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Error opening reset password screen.");
        }
    }
}
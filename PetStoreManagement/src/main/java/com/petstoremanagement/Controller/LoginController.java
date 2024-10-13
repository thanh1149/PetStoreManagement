package com.petstoremanagement.Controller;

import com.petstoremanagement.Global.AppProperties;
import com.petstoremanagement.Service.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML private TextField txtUsername;
    @FXML private TextField txtPassword;
    @FXML private Button btnLogin;
    @FXML private Label lbInform;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnLogin.setOnAction(this::handleLogin);
    }

    private void handleLogin(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Username or Password must not be empty.");
            return;
        }

        if (!LoginService.login(username, password)) {
            showError("Invalid username or password");
            return;
        }

        AppProperties.setProperty("user.name", username);
        AppProperties.setProperty("user.loggedin", "true");

        loadMasterView(event);
    }

    private void showError(String message) {
        lbInform.setText(message);
        lbInform.setStyle("-fx-text-fill: red");
    }

    private void loadMasterView(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/petstoremanagement/view/master.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            Scene scene = new Scene(root, 1200, 700);
            stage.setTitle("PetStoreManagement");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to load master view. Error: " + e.getMessage());
        }
    }
}
package com.petstoremanagement.Controller;

import com.petstoremanagement.Global.AppProperties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MasterController implements Initializable {
    @FXML private Label lblUsrName;
    @FXML private Button btnLogout;
    @FXML private Button btnHome;
    @FXML private Button btnProducts;
    @FXML private Button btnCustomers;
    @FXML private Button btnService;
    @FXML private Button btnStaff;
    @FXML private StackPane dashContent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String userNameLogged = AppProperties.getProperty("user.name");
        boolean isLogged = Boolean.parseBoolean(AppProperties.getProperty("user.loggedin"));
        if (isLogged) {
            lblUsrName.setText(userNameLogged);
            lblUsrName.setVisible(true);
        }

        btnLogout.setOnAction(this::showLogoutConfirmation);
        btnStaff.setOnAction(this::handleStaffButton);
        btnService.setOnAction(this::handleServiceButton);
        btnHome.setOnAction(this::handleHomeButton);
        btnProducts.setOnAction(this::handleProductButton);

        dashContent.getChildren().clear();
    }

    private void showLogoutConfirmation(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("Ok to confirm, cancel to return.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            handleLogout(event);
        } else {
            alert.close();
        }
    }

    private void handleLogout(ActionEvent event) {
        try {
            Parent loginView = FXMLLoader.load(getClass().getResource("/com/petstoremanagement/login.fxml"));
            Scene loginScene = new Scene(loginView);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            currentStage.setScene(loginScene);
            currentStage.show();

            AppProperties.setProperty("user.loggedin", "false");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleStaffButton(ActionEvent event) {
        loadContent("/com/petstoremanagement/view/staff/staff.fxml");
    }

    public void handleServiceButton(ActionEvent event) {
        loadContent("/com/petstoremanagement/view/service/service.fxml");
    }

    public void handleProductButton(ActionEvent event) {
        loadContent("/com/petstoremanagement/view/product/product.fxml");
    }

    public void handleHomeButton(ActionEvent event) {
        // Clear the dashContent when returning to home
        dashContent.getChildren().clear();
    }

    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent content = loader.load();

            dashContent.getChildren().clear();
            dashContent.getChildren().add(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
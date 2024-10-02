package com.petstoremanagement.Controller.staff;

import com.petstoremanagement.Global.StaffValidate;
import com.petstoremanagement.Model.Role;
import com.petstoremanagement.Service.RoleService;
import com.petstoremanagement.Service.StaffService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.util.ResourceBundle;

public class AddStaffController implements Initializable {
    @FXML
    private TextField txtFullName;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtConfirmPw;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhone;
    @FXML
    private ChoiceBox<Role> cbRole;
    @FXML
    private Button btnAdd;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbRole.setItems(RoleService.getAllRole());

        btnAdd.setOnAction(event -> handleAdd(event));
    }

    private void handleAdd(ActionEvent event) {
        String fullName = txtFullName.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPw.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        Role selectedRole = cbRole.getValue();

        if (!validateFields(fullName, username, password, confirmPassword, email, phone, selectedRole)) {
            return;
        }

        boolean addSuccess = StaffService.addStaff(selectedRole.getId(), username, password, fullName, email, phone);
        if (addSuccess) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notification");
            alert.setHeaderText(null);
            alert.setContentText("Add staff success!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/petstoremanagement/view/staff/staff.fxml"));
                        Parent newContent = loader.load();
                        StackPane dashContent = (StackPane) btnAdd.getScene().lookup("#dashContent");

                        dashContent.getChildren().clear();
                        dashContent.getChildren().add(newContent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Can't add staff, please try again.");
        }
    }

    // Thêm phần kiểm tra (validate)
    private boolean validateFields(String fullName, String username, String password, String confirmPassword,
                                   String email, String phone, Role selectedRole) {
        if (!StaffValidate.isNotEmpty(fullName) || !StaffValidate.isNotEmpty(username)
                || !StaffValidate.isNotEmpty(password) || !StaffValidate.isNotEmpty(confirmPassword)
                || !StaffValidate.isNotEmpty(email) || !StaffValidate.isNotEmpty(phone)
                || selectedRole == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
            return false;
        }

        if (!StaffValidate.isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid email format.");
            return false;
        }

        if (!StaffValidate.isValidPhone(phone)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Phone number must be 10 digits.");
            return false;
        }

        if (!StaffValidate.isValidPassword(password)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password must be at least 5 characters.");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match.");
            return false;
        }

        return true;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

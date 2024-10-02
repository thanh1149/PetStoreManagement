package com.petstoremanagement.Controller.staff;

import com.petstoremanagement.Global.StaffValidate;
import com.petstoremanagement.Model.Role;
import com.petstoremanagement.Model.Staff;
import com.petstoremanagement.Service.RoleService;
import com.petstoremanagement.Service.StaffService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditStaffController implements Initializable {
    @FXML
    private TextField txtFullName;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhone;
    @FXML
    private ChoiceBox<Role> cbRole;
    @FXML
    private Button btnEdit;

    private Staff selectedStaff;
    private final StaffService staffService = new StaffService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbRole.setItems(RoleService.getAllRole());
        btnEdit.setOnAction(event -> handleEditStaff());
    }

    public void setSelectedStaff(Staff staff) {
        this.selectedStaff = staff;
        if (staff != null) {
            txtFullName.setText(staff.getFullName());
            txtUsername.setText(staff.getUsername());
            txtEmail.setText(staff.getEmail());
            txtPhone.setText(staff.getPhone());

            if (staff.getRole() != null) {
                for (Role role : cbRole.getItems()) {
                    if (role.getId().equals(staff.getRole().getId())) {
                        cbRole.setValue(role);
                        break;
                    }
                }
            }
        }
    }

    private void handleEditStaff() {
        if (validateFields()) {
            selectedStaff.setFullName(txtFullName.getText());
            selectedStaff.setUsername(txtUsername.getText());
            selectedStaff.setEmail(txtEmail.getText());
            selectedStaff.setPhone(txtPhone.getText());
            selectedStaff.setRole(cbRole.getValue());

            if (!txtPassword.getText().isEmpty()) {
                selectedStaff.setPassword(txtPassword.getText());
            }

            boolean success = StaffService.edit(selectedStaff);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Staff updated successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update staff.");
            }
        }
    }

    // Thêm phần kiểm tra (validate)
    private boolean validateFields() {
        String fullName = txtFullName.getText();
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String password = txtPassword.getText();
        Role selectedRole = cbRole.getValue();

        if (!StaffValidate.isNotEmpty(fullName) || !StaffValidate.isNotEmpty(username)
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

        if (!password.isEmpty() && !StaffValidate.isValidPassword(password)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password must be at least 5 characters.");
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

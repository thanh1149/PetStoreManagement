package com.petstoremanagement.Controller.staff;

import com.petstoremanagement.Model.Role;
import com.petstoremanagement.Model.Staff;
import com.petstoremanagement.Service.RoleService;
import com.petstoremanagement.Service.StaffService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

            // Safely handle the case where staff's role might be null
            if (staff.getRole() != null) {
                for (Role role : cbRole.getItems()) {
                    if (role.getId().equals(staff.getRole().getId())) {
                        cbRole.setValue(role);
                        break;
                    }
                }
            } else {
                // If staff has no role, select the first role in the list or clear the selection
                if (!cbRole.getItems().isEmpty()) {
                    cbRole.setValue(cbRole.getItems().get(0));
                } else {
                    cbRole.setValue(null);
                }
                // Optionally, log this unusual situation
                System.out.println("Warning: Staff with ID " + staff.getStaffID() + " has no assigned role.");
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

    private boolean validateFields() {
        if (txtFullName.getText().isEmpty() || txtUsername.getText().isEmpty() ||
                txtEmail.getText().isEmpty() || txtPhone.getText().isEmpty() || cbRole.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled.");
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
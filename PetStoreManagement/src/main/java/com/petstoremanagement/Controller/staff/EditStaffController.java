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
    private PasswordField txtPassword; // Mật khẩu mới
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhone;
    @FXML
    private ChoiceBox<Role> cbRole;
    @FXML
    private Button btnEdit;

    private Staff selectedStaff;
    private ObservableList<Role> roles;
    private final StaffService staffService = new StaffService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbRole.getItems().clear();
        roles = FXCollections.observableArrayList(RoleService.getAllRole());
        cbRole.setItems(roles);

        if (selectedStaff != null) {
            txtFullName.setText(selectedStaff.getFullName());
            txtUsername.setText(selectedStaff.getUsername());
            txtEmail.setText(selectedStaff.getEmail());
            txtPhone.setText(selectedStaff.getPhone());
            cbRole.setValue(selectedStaff.getRole());
        }
        btnEdit.setOnAction(event -> handleEditStaff());
    }

    public void setSelectedStaff(Staff staff) {
        this.selectedStaff = staff;
        if (staff != null) {
            txtFullName.setText(staff.getFullName());
            txtUsername.setText(staff.getUsername());
            txtEmail.setText(staff.getEmail());
            txtPhone.setText(staff.getPhone());
            cbRole.setValue(staff.getRole());
        }
    }

    private void handleEditStaff() {
        if (validateFields()) {
            // Create a new staff object with the edited details
            selectedStaff.setFullName(txtFullName.getText());
            selectedStaff.setUsername(txtUsername.getText());
            selectedStaff.setEmail(txtEmail.getText());
            selectedStaff.setPhone(txtPhone.getText());
            selectedStaff.setRole(cbRole.getValue());

            // Chỉ cập nhật mật khẩu nếu nó không trống
            if (!txtPassword.getText().isEmpty()) {
                selectedStaff.setPassword(txtPassword.getText()); // Bcrypt sẽ được sử dụng trong service để mã hóa
            }

            // Call the edit method from StaffService
            staffService.edit(selectedStaff);
        }
    }

    private boolean validateFields() {
        if (txtFullName.getText().isEmpty() || txtUsername.getText().isEmpty() || txtEmail.getText().isEmpty() || txtPhone.getText().isEmpty()) {
            showAlert("Error", "All fields must be filled.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

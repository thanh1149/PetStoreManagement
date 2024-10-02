package com.petstoremanagement.Controller.staff;

import com.petstoremanagement.Model.Role;
import com.petstoremanagement.Service.RoleService;
import com.petstoremanagement.Service.StaffService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

    private ObservableList<Role> roles;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbRole.setItems(RoleService.getAllRole());

        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleAdd(event);
            }
        });
    }

    private void handleAdd(ActionEvent event) {
        String fullName = txtFullName.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPw.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        Role selectedRole = cbRole.getValue();

        if (password.equals(confirmPassword)) {
            StaffService.addStaff(selectedRole.getId(), username, password, fullName, email, phone);
            System.out.println("Add successful.");
        } else {
            System.out.println("Failed to add: Passwords do not match.");
        }
    }
}

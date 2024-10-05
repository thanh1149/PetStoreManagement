package com.petstoremanagement.Controller.staff;

import com.petstoremanagement.Model.Staff;
import com.petstoremanagement.Service.StaffService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StaffController implements Initializable {
    @FXML
    private Button btnAddStaff;
    @FXML
    private StackPane staffContent;
    @FXML
    private TableColumn<Staff, String > cFullName;
    @FXML
    private TableColumn<Staff, String > cEmail;
    @FXML
    private TableColumn<Staff, String > cUsername;
    @FXML
    private TableColumn<Staff, String > cPhone;
    @FXML
    private TableColumn<Staff, Void > cAction;
    @FXML
    private TextField txtStaffSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private TableView<Staff> tblStaff = new TableView<Staff>();


    private ObservableList<Staff> staffs;
    private final StaffService staffService = new StaffService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        staffs = StaffService.getAllStaff();
//        tableStaff.getColumns().get(0).setVisible(true);
        cFullName.setCellValueFactory(new PropertyValueFactory<Staff, String>("FullName"));
        cEmail.setCellValueFactory(new PropertyValueFactory<Staff, String>("Email"));
        cUsername.setCellValueFactory(new PropertyValueFactory<Staff, String>("Username"));
        cPhone.setCellValueFactory(new PropertyValueFactory<Staff, String>("Phone"));

        tblStaff.setItems(staffs);

        cAction.setCellFactory(param -> new TableCell<Staff, Void>() {
            private final Button btnEdit = new Button("Edit");
            private final Button btnDelete = new Button("Delete");
            {
                btnEdit.setOnAction(event -> {
                    Staff selectedStaff = getTableView().getItems().get(getIndex());
                    handleEditButton(selectedStaff);
                });

                btnDelete.setOnAction(event -> {
                    Staff staff = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Delete");
                    alert.setHeaderText("Are you sure you want to delete this staff?");
                    alert.setContentText("Staff: " + staff.getFullName());

                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            StaffService.remove(staff);
                            getTableView().getItems().remove(staff);
                        }
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox actionButtons = new HBox(btnEdit, btnDelete);
                    actionButtons.setSpacing(10);
                    actionButtons.setAlignment(Pos.CENTER);
                    setGraphic(actionButtons);
                }
            }
        });

        btnAddStaff.setOnAction(this::handleAddStaffButton);
        btnSearch.setOnAction(this::handleSearchButton);

    }


    private void handleAddStaffButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/petstoremanagement/view/staff/add-staff.fxml"));
            Parent staffPage = loader.load();

            staffContent.getChildren().clear();
            staffContent.getChildren().add(staffPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSearchButton(ActionEvent event) {
        String searchText = txtStaffSearch.getText();
        if (searchText != null && !searchText.isEmpty()) {
            ObservableList<Staff> searchResults = StaffService.findStaffName(searchText);
            tblStaff.setItems(searchResults.isEmpty() ? FXCollections.observableArrayList() : searchResults);
        } else {
            tblStaff.setItems(staffs);
        }
        tblStaff.refresh();
    }

    private void handleEditButton(Staff selectedStaff) {
        try {
            Staff fullStaffDetails = staffService.getStaffById(selectedStaff.getStaffID());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/petstoremanagement/view/staff/edit-staff.fxml"));
            Parent editStaffPage = loader.load();

            EditStaffController editStaffController = loader.getController();
            editStaffController.setSelectedStaff(fullStaffDetails);

            staffContent.getChildren().clear();
            staffContent.getChildren().add(editStaffPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

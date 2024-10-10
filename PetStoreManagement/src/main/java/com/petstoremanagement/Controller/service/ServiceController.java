package com.petstoremanagement.Controller.service;

import com.petstoremanagement.Model.Service;
import com.petstoremanagement.Service.ServService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServiceController implements Initializable {
    @FXML
    private Button btnAddService;
    @FXML
    private StackPane serviceContent;
    @FXML
    private TableColumn<Service, String > cName;
    @FXML
    private TableColumn<Service, String > cDescription;
    @FXML
    private TableColumn<Service, String > cPrice;
    @FXML
    private TableColumn<Service, Image > cImage;
    @FXML
    private TableColumn<Service, Void > cAction;
    @FXML
    private TextField txtServiceSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private TableView<Service> tblService = new TableView<Service>();


    private ObservableList<Service> services;
    private final ServService servService = new ServService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        services = ServService.getAllService();
        cName.setCellValueFactory(new PropertyValueFactory<Service, String>("name"));
        cDescription.setCellValueFactory(new PropertyValueFactory<Service, String>("description"));
        cPrice.setCellValueFactory(new PropertyValueFactory<Service, String>("price"));
        cImage.setCellValueFactory(new PropertyValueFactory<>("ServiceImage"));
        cImage.setCellFactory(column -> new TableCell<Service, Image>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Image image, boolean empty) {
                super.updateItem(image, empty);
                if (empty || image == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(image);
                    imageView.setFitHeight(200);
                    imageView.setFitWidth(200);
                    imageView.setPreserveRatio(true);
                    imageView.setSmooth(true);
                    setGraphic(imageView);
                    setAlignment(Pos.CENTER);
                }
            }
        });
        tblService.setItems(services);

        cAction.setCellFactory(param -> new TableCell<Service, Void>() {
            private final Button btnEdit = new Button("Edit");
            private final Button btnDelete = new Button("Delete");
            {
                btnEdit.setOnAction(event -> {
                    Service selectedService = getTableView().getItems().get(getIndex());
                    handleEditButton(selectedService);
                });

                btnDelete.setOnAction(event ->  {
                    Service service = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Delete");
                    alert.setHeaderText("Are you sure you want to delete this service?");
                    alert.setContentText("Service: " + service.getName());

                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            ServService.remove(service);
                            getTableView().getItems().remove(service);
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


        btnAddService.setOnAction(this::handleAddServiceButton);
        btnSearch.setOnAction(this::handleSearchButton);
    }

    private void handleAddServiceButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/petstoremanagement/view/service/add-service.fxml"));
            Parent servicePage = loader.load();

            serviceContent.getChildren().clear();
            serviceContent.getChildren().add(servicePage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSearchButton(ActionEvent event) {
        String searchText = txtServiceSearch.getText();
        if (searchText != null && !searchText.isEmpty()) {
            ObservableList<Service> searchResults = ServService.findServiceName(searchText);
            tblService.setItems(searchResults.isEmpty() ? FXCollections.observableArrayList() : searchResults);
        } else {
            tblService.setItems(services);
        }
        tblService.refresh();
    }

    private void handleEditButton(Service selectedService) {
        try {
            Service fullServiceDetails = servService.getServiceID(selectedService.getId());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/petstoremanagement/view/service/edit-service.fxml"));
            Parent editServicePage = loader.load();

            EditServiceController editServiceController = loader.getController();
            editServiceController.setSelectedService(fullServiceDetails);

            serviceContent.getChildren().clear();
            serviceContent.getChildren().add(editServicePage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

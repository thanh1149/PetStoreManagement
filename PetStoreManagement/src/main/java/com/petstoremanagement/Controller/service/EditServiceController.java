package com.petstoremanagement.Controller.service;
import com.petstoremanagement.Global.ServiceValidate;
import com.petstoremanagement.Model.Category;
import com.petstoremanagement.Model.Service;
import com.petstoremanagement.Service.CategoryService;
import com.petstoremanagement.Service.ServService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class EditServiceController implements Initializable {
    @FXML
    private TextField txtServiceName;
    @FXML
    private TextArea txtDescription;
    @FXML
    private TextField txtPrice;
    @FXML
    private ImageView imgService;
    @FXML
    private Button btnChooseImage;
    @FXML
    private ChoiceBox<Category> cbCategory;
    @FXML
    private Button btnEditService;

    private File selectedImageFile;
    private Service selectedService;
    private final ServService servService = new ServService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbCategory.setItems(CategoryService.getAllCategory());

        btnEditService.setOnAction(event -> handleEditService());

        btnChooseImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectedImageFile = ServService.selectImageFile();
                if (selectedImageFile != null) {
                    Image image = new Image(selectedImageFile.toURI().toString());
                    imgService.setImage(image);
                }
            }
        });
    }

    public void setSelectedService(Service service) {
        this.selectedService = service;
        if (service != null) {
            txtServiceName.setText(service.getName());
            txtDescription.setText(service.getDescription());
            txtPrice.setText(String.valueOf(service.getPrice()));
            imgService.setImage(service.getServiceImage());

            if (service.getCategory() != null) {
                for (Category category : cbCategory.getItems()) {
                    if (category.getId() == service.getCategory().getId()) {
                        cbCategory.setValue(category);
                        break;
                    }
                }
            }
        }
    }

    private void handleEditService() {
        if (validateFields()) {
            selectedService.setName(txtServiceName.getText());
            selectedService.setDescription(txtDescription.getText());
            selectedService.setPrice(Double.parseDouble(txtPrice.getText()));
            selectedService.setCategory(cbCategory.getValue());

            if (selectedImageFile != null) {
                Image image = new Image(selectedImageFile.toURI().toString());
                selectedService.setServiceImage(image);
            }

            boolean success = ServService.edit(selectedService);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Service updated successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update service.");
            }
        }
    }

    private boolean validateFields() {
        String serviceName = txtServiceName.getText();
        String description = txtDescription.getText();
        String price = txtPrice.getText();
        Category selectedCategory = cbCategory.getValue();

        if (!ServiceValidate.isNotEmpty(serviceName) || !ServiceValidate.isNotEmpty(description)
                || !ServiceValidate.isNotEmpty(price) || selectedCategory == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
            return false;
        }

        if (!ServiceValidate.isValidPrice(price)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Price must be a valid number.");
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

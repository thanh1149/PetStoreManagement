package com.petstoremanagement.Controller.service;

import com.petstoremanagement.Global.ServiceValidate;
import com.petstoremanagement.Model.Category;
import com.petstoremanagement.Model.Role;
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
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class AddServiceController implements Initializable {
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
    private Button btnAddService;

    private File selectedImageFile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbCategory.setItems(CategoryService.getAllCategory());

        btnChooseImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File selectedFile = ServService.selectImageFile();
                if (selectedFile != null) {
                    Image image = new Image(selectedFile.toURI().toString());
                    imgService.setImage(image);
                }
            }
        });

        btnAddService.setOnAction(this::handleAddService);
    }

    private void handleAddService(ActionEvent event) {
        String serviceName = txtServiceName.getText();
        String description = txtDescription.getText();
        String priceText = txtPrice.getText();
        Category selectedCategory = cbCategory.getValue();
        Image selectedImage = imgService.getImage();

        if (!validateFields(serviceName, description, priceText, selectedCategory, selectedImage)) {
            return;
        }

        double price = Double.parseDouble(priceText);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        Service service = new Service(0, selectedCategory.getId(), serviceName, description, price, selectedImage, currentTime, currentTime);

        boolean addSuccess = ServService.addService(service, selectedImageFile);
        if (addSuccess) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Service added successfully!");
            resetForm();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add service. Please try again.");
        }
    }

    private boolean validateFields(String serviceName, String description, String priceText, Category selectedCategory, Image selectedImage) {
        if (serviceName.isEmpty() || description.isEmpty() || priceText.isEmpty() || selectedCategory == null || selectedImage == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields must be filled.");
            return false;
        }

        if (!ServiceValidate.isValidPrice(priceText)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Price must be a number.");
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

    private void resetForm() {
        txtServiceName.clear();
        txtDescription.clear();
        txtPrice.clear();
        cbCategory.setValue(null);
        imgService.setImage(null);
        selectedImageFile = null; // Reset lại biến hình ảnh
    }

}

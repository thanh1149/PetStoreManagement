package com.petstoremanagement.Controller.product;

import com.petstoremanagement.Global.ProductValidate;
import com.petstoremanagement.Global.ServiceValidate;
import com.petstoremanagement.Model.Category;
import com.petstoremanagement.Model.Product;
import com.petstoremanagement.Model.Service;
import com.petstoremanagement.Service.CategoryService;
import com.petstoremanagement.Service.ProductService;
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

public class AddProductController implements Initializable {
    @FXML
    private TextField txtProductName;
    @FXML
    private TextArea txtDescription;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtQuantity;
    @FXML
    private ImageView imgProduct;
    @FXML
    private Button btnChooseImage;
    @FXML
    private ChoiceBox<Category> cbCategory;
    @FXML
    private Button btnAddProduct;

    private File selectedImageFile;
    private final ProductService productService = new ProductService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbCategory.setItems(CategoryService.getAllCategory());

        btnChooseImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File selectedFile = ProductService.selectImageFile();
                if (selectedFile != null) {
                    Image image = new Image(selectedFile.toURI().toString());
                    imgProduct.setImage(image);
                }
            }
        });

        btnAddProduct.setOnAction(this::handleAddProduct);
    }

    private void handleAddProduct(ActionEvent event) {
        String productName = txtProductName.getText();
        String description = txtDescription.getText();
        String priceText = txtPrice.getText();
        String quantity = txtQuantity.getText();
        Category selectedCategory = cbCategory.getValue();
        Image selectedImage = imgProduct.getImage();

        if (!validateFields(productName, description, priceText,quantity,selectedCategory, selectedImage)) {
            return;
        }

        double price = Double.parseDouble(priceText);
        int productQuantity = Integer.parseInt(quantity);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        Product product = new Product(0, productName, description, price,productQuantity, selectedCategory.getId(), selectedImage, currentTime, currentTime);

        boolean addSuccess = ProductService.addProduct(product, selectedImageFile);
        if (addSuccess) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Product added successfully!");
            resetForm();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add product. Please try again.");
        }
    }

    private boolean validateFields(String productName, String description, String priceText,String quantity, Category selectedCategory, Image selectedImage) {
        if (productName.isEmpty() || description.isEmpty() || priceText.isEmpty() || quantity.isEmpty() || selectedCategory == null || selectedImage == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields must be filled.");
            return false;
        }

        if (!ProductValidate.isValidPrice(priceText)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Price must be a number.");
            return false;
        }

        if (!ProductValidate.isValidQuantity(quantity)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Quantity must be a number.");
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
        txtProductName.clear();
        txtDescription.clear();
        txtPrice.clear();
        txtQuantity.clear();
        cbCategory.setValue(null);
        imgProduct.setImage(null);
        selectedImageFile = null;
    }
}

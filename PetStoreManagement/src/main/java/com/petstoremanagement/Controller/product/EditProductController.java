package com.petstoremanagement.Controller.product;

import com.petstoremanagement.Global.ProductValidate;
import com.petstoremanagement.Model.Category;
import com.petstoremanagement.Model.Product;
import com.petstoremanagement.Service.CategoryService;
import com.petstoremanagement.Service.ProductService;
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

public class EditProductController implements Initializable {
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
    private Button btnEditProduct;

    private File selectedImageFile;
    private Product selectedProduct;
    private final ProductService productService = new ProductService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbCategory.setItems(CategoryService.getAllCategory());

        btnEditProduct.setOnAction(event -> handleEditProduct());

        btnChooseImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectedImageFile = ProductService.selectImageFile();
                if (selectedImageFile != null) {
                    Image image = new Image(selectedImageFile.toURI().toString());
                    imgProduct.setImage(image);
                }
            }
        });
    }

    public void setSelectedProduct(Product product) {
        this.selectedProduct = product;
        if (product != null) {
            txtProductName.setText(product.getName());
            txtDescription.setText(product.getDescription());
            txtPrice.setText(String.valueOf(product.getPrice()));
            txtQuantity.setText(String.valueOf(product.getStockQuantity()));
            imgProduct.setImage(product.getProductImage());

            if (product.getCategory() != null) {
                for (Category category : cbCategory.getItems()) {
                    if (category.getId() == product.getCategory().getId()) {
                        cbCategory.setValue(category);
                        break;
                    }
                }
            }
        }
    }

    private void handleEditProduct() {
        if (validateFields()) {
            selectedProduct.setName(txtProductName.getText());
            selectedProduct.setDescription(txtDescription.getText());
            selectedProduct.setPrice(Double.parseDouble(txtPrice.getText()));
            selectedProduct.setStockQuantity(Integer.parseInt(txtQuantity.getText()));
            selectedProduct.setCategory(cbCategory.getValue());

            if (selectedImageFile != null) {
                Image image = new Image(selectedImageFile.toURI().toString());
                selectedProduct.setProductImage(image);
            }

            boolean success = ProductService.edit(selectedProduct);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Product updated successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update product.");
            }
        }
    }

    private boolean validateFields() {
        String productName = txtProductName.getText();
        String description = txtDescription.getText();
        String price = txtPrice.getText();
        String quantity = txtQuantity.getText();
        Category selectedCategory = cbCategory.getValue();

        if (!ProductValidate.isNotEmpty(productName) || !ProductValidate.isNotEmpty(description) || !ProductValidate.isNotEmpty(price) || !ProductValidate.isNotEmpty(quantity) || selectedCategory == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
            return false;
        }

        if (!ProductValidate.isValidPrice(price)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Price must be a valid number.");
            return false;
        }
        if (!ProductValidate.isValidQuantity(quantity)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Quantity must be a valid number.");
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

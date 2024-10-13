package com.petstoremanagement.Controller.product;

import com.petstoremanagement.Controller.service.EditServiceController;
import com.petstoremanagement.Model.Product;
import com.petstoremanagement.Model.Service;
import com.petstoremanagement.Service.ProductService;
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

public class ProductController implements Initializable {
    @FXML
    private Button btnAddProduct;
    @FXML
    private StackPane productContent;
    @FXML
    private TableColumn<Product, String > cName;
    @FXML
    private TableColumn<Product, String > cDescription;
    @FXML
    private TableColumn<Product, String > cPrice;
    @FXML
    private TableColumn<Product, String > cQuantity;
    @FXML
    private TableColumn<Product, Image> cImage;
    @FXML
    private TableColumn<Product, Void > cAction;
    @FXML
    private TextField txtProductSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private TableView<Product> tblProduct = new TableView<Product>();

    private ObservableList<Product> products;
    private final ProductService productService = new ProductService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        products = ProductService.getAllProduct();
        cName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        cDescription.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
        cPrice.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        cQuantity.setCellValueFactory(new PropertyValueFactory<Product, String>("StockQuantity"));
        cImage.setCellValueFactory(new PropertyValueFactory<>("ProductImage"));
        cImage.setCellFactory(column -> new TableCell<Product, Image>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Image image, boolean empty) {
                super.updateItem(image, empty);
                if (empty || image == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(image);
                    imageView.setFitHeight(150);
                    imageView.setFitWidth(150);
                    imageView.setPreserveRatio(true);
                    imageView.setSmooth(true);
                    setGraphic(imageView);
                    setAlignment(Pos.CENTER);
                }
            }
        });
        tblProduct.setItems(products);

        cAction.setCellFactory(param -> new TableCell<Product, Void>() {
            private final Button btnEdit = new Button("Edit");
            private final Button btnDelete = new Button("Delete");
            {
                btnEdit.setOnAction(event -> {
                    Product selectedProduct = getTableView().getItems().get(getIndex());
                    handleEditButton(selectedProduct);
                });

                btnDelete.setOnAction(event ->  {
                    Product product = getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Delete");
                    alert.setHeaderText("Are you sure you want to delete this product?");
                    alert.setContentText("Product: " + product.getName());

                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            ProductService.remove(product);
                            getTableView().getItems().remove(product);
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


        btnAddProduct.setOnAction(this::handleAddProductButton);
        btnSearch.setOnAction(this::handleSearchButton);
    }

    private void handleAddProductButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/petstoremanagement/view/product/add-product.fxml"));
            Parent productPage = loader.load();

            productContent.getChildren().clear();
            productContent.getChildren().add(productPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSearchButton(ActionEvent event) {
        String searchText = txtProductSearch.getText();
        if (searchText != null && !searchText.isEmpty()) {
            ObservableList<Product> searchResults = ProductService.findProductName(searchText);
            tblProduct.setItems(searchResults.isEmpty() ? FXCollections.observableArrayList() : searchResults);
        } else {
            tblProduct.setItems(products);
        }
        tblProduct.refresh();
    }

    private void handleEditButton(Product selectedProduct) {
        try {
            Product fullProductDetails = productService.getProductID(selectedProduct.getId());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/petstoremanagement/view/product/edit-product.fxml"));
            Parent editProductPage = loader.load();

            EditProductController editProductController = loader.getController();
            editProductController.setSelectedProduct(fullProductDetails);

            productContent.getChildren().clear();
            productContent.getChildren().add(editProductPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.petstoremanagement.Controller.home;

import com.petstoremanagement.Controller.MasterController;
import com.petstoremanagement.Global.AppProperties;
import com.petstoremanagement.Model.Product;
import com.petstoremanagement.Service.ProductService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private Button btnProducts;
    @FXML
    private Button btnStaff;
    @FXML
    private Button btnCustomers;
    @FXML
    private Button btnService;
    @FXML
    private Button btnOrder;
    @FXML
    private Button btnBooking;
    @FXML
    private PieChart pieChart1;
    @FXML
    private PieChart pieChart2;
    @FXML
    private PieChart pieChart3;
    @FXML
    private BarChart<String, Number> salesBarChart; // Updated to BarChart
    private ObservableList<Product> products;
    private MasterController masterController = new MasterController();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPieChart1();
        loadSalesBarChart();
        btnStaff.setOnAction(this::handleStaffButton);
        btnService.setOnAction(this::handleServiceButton);
        btnProducts.setOnAction(this::handleProductButton);
        btnCustomers.setOnAction(this::handleCustomerButton);
        btnOrder.setOnAction(this::handleOrderButton);
        btnBooking.setOnAction(this::handleBookingButton);
    }

    private void loadPieChart1() {
        products = ProductService.getAllProduct();
        pieChart1.getData().clear();
        products.forEach(product -> {
            PieChart.Data data = new PieChart.Data(product.getName(), product.getStockQuantity());
            pieChart1.getData().add(data);
        });
    }

    private void loadSalesBarChart() {
        products = ProductService.getAllProduct();
        salesBarChart.getData().clear();
        XYChart.Series<String, Number> productSeries = new XYChart.Series<>();
        productSeries.setName("Product Stock");
        products.forEach(product -> {
            productSeries.getData().add(new XYChart.Data<>(product.getName(), product.getStockQuantity()));
        });
        salesBarChart.getData().add(productSeries);
    }

    public void handleStaffButton(ActionEvent event) {
        loadContent("/com/petstoremanagement/view/staff/staff.fxml");
    }

    public void handleServiceButton(ActionEvent event) {
        loadContent("/com/petstoremanagement/view/service/service.fxml");
    }

    public void handleProductButton(ActionEvent event) {
        loadContent("/com/petstoremanagement/view/product/product.fxml");
    }

    public void handleCustomerButton(ActionEvent event) {
        loadContent("/com/petstoremanagement/view/customer/customer.fxml");
    }

    public void handleOrderButton(ActionEvent event) {
        loadContent("/com/petstoremanagement/view/order/order.fxml");
    }

    public void handleBookingButton(ActionEvent event) {
        loadContent("/com/petstoremanagement/view/booking/booking.fxml");
    }

    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent content = loader.load();

            masterController.dashContent.getChildren().clear();
            masterController.dashContent.getChildren().add(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Add a method to set the MasterController reference
    public void setMasterController(MasterController masterController) {
        this.masterController = masterController;
    }
}
package com.petstoremanagement.Controller.order;

import com.petstoremanagement.Model.Order;
import com.petstoremanagement.Model.Staff;
import com.petstoremanagement.Service.OrderService;
import com.petstoremanagement.Service.StaffService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderController implements Initializable {
    @FXML
    private Button btnFilter;
    @FXML
    private MenuButton mnSort;
    @FXML
    private MenuItem sortByDateAsc;
    @FXML
    private MenuItem sortByDateDesc;
    @FXML
    private MenuItem sortByAmountAsc;
    @FXML
    private MenuItem sortByAmountDesc;
    @FXML
    private StackPane orderContent;
    @FXML
    private TableColumn<Order, String > cOrderID;
    @FXML
    private TableColumn<Order, String > cCustomer;
    @FXML
    private TableColumn<Order, String > cOrderDate;
    @FXML
    private TableColumn<Order, String > cTotal;
    @FXML
    private TableColumn<Order, String > cStatus;
    @FXML
    private TextField txtOrderSearch;
    @FXML
    private Button btnSearchOrder;
    @FXML
    private TableView<Order> tblOrder = new TableView<Order>();


    private ObservableList<Order> orders;
    private final OrderService orderService = new OrderService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orders = OrderService.getAllOrders();
        cOrderID.setCellValueFactory(new PropertyValueFactory<Order, String>("id"));
        cCustomer.setCellValueFactory(cellData -> cellData.getValue().getCustomer().nameProperty());
        cOrderDate.setCellValueFactory(new PropertyValueFactory<Order, String>("OrderDate"));
        cTotal.setCellValueFactory(new PropertyValueFactory<Order, String>("TotalAmount"));
        cStatus.setCellValueFactory(cellData -> cellData.getValue().getStatus().titleProperty());

        tblOrder.setItems(orders);

        btnFilter.setOnAction(event -> showFilterDialog());
        btnSearchOrder.setOnAction(this::handleSearchButton);
        sortByDateAsc.setOnAction(event -> handleSortByDate(false));
        sortByDateDesc.setOnAction(event -> handleSortByDate(true));
        sortByAmountAsc.setOnAction(event -> handleSortByTotalAmount(true));
        sortByAmountDesc.setOnAction(event -> handleSortByTotalAmount(false));
    }

    private void showFilterDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/petstoremanagement/view/order/filter.fxml"));
            DialogPane dialogPane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Filter Orders");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(dialogPane));

            FilterController filterController = loader.getController();
            dialogStage.showAndWait();

            Double minPrice = filterController.getMinPrice();
            Double maxPrice = filterController.getMaxPrice();
            boolean done = filterController.isDoneSelected();
            boolean pending = filterController.isPendingSelected();
            boolean cancel = filterController.isCancelSelected();

            ObservableList<Order> filteredOrders = OrderService.filterOrders(minPrice, maxPrice, done, pending, cancel);

            tblOrder.setItems(filteredOrders);
            tblOrder.refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSearchButton(ActionEvent event) {
        String searchText = txtOrderSearch.getText();
        if (searchText != null && !searchText.isEmpty()) {
            ObservableList<Order> searchResults;
            if (searchText.matches("\\d+")) {
                searchResults = OrderService.getOrderByID(Integer.parseInt(searchText));
            } else {
                searchResults = OrderService.getOrderByCustomerName(searchText);
            }
            tblOrder.setItems(searchResults.isEmpty() ? FXCollections.observableArrayList() : searchResults);
        } else {
            tblOrder.setItems(orders);
        }
        tblOrder.refresh();
    }

    private void handleSortByDate(boolean ascending) {
        ObservableList<Order> sortedOrders = OrderService.sortOrdersByDate(ascending);
        tblOrder.setItems(sortedOrders);
        tblOrder.refresh();
    }

    private void handleSortByTotalAmount(boolean ascending) {
        ObservableList<Order> sortedOrders = OrderService.sortOrdersByTotalAmount(ascending);
        tblOrder.setItems(sortedOrders);
        tblOrder.refresh();
    }




}

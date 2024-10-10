package com.petstoremanagement.Controller.customer;

import com.petstoremanagement.Model.Customer;
import com.petstoremanagement.Model.Order;
import com.petstoremanagement.Model.OrderItem;
import com.petstoremanagement.Model.ServiceBooking;
import com.petstoremanagement.Service.OrderItemService;
import com.petstoremanagement.Service.OrderService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class OrderController implements Initializable {
    @FXML
    private StackPane orderContent;
    @FXML
    private TableView<Order> tblOrder = new TableView<Order>();
    @FXML
    private TableColumn<Order,String> cOrderDate;
    @FXML
    private TableColumn<Order,String> cTotalAmount;
    @FXML
    private TableColumn<Order,String> cStatus;
    @FXML
    private TableColumn<Order, Void > cAction;
    @FXML
    private Label lblCustomerName;

    private ObservableList<Order> orders;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cOrderDate.setCellValueFactory(new PropertyValueFactory<Order, String>("OrderDate"));
        cTotalAmount.setCellValueFactory(new PropertyValueFactory<Order, String>("TotalAmount"));
        cStatus.setCellValueFactory(cellData -> cellData.getValue().getStatus().titleProperty());
        tblOrder.setItems(orders);
        cAction.setCellFactory(param -> new TableCell<Order, Void>() {
            private final Button btnOrderItems = new Button("View Order Items");
            {
                btnOrderItems.setOnAction(event -> {
                    Order selectedOrder = getTableView().getItems().get(getIndex());
                    handleOrderItems(selectedOrder);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox actionButtons = new HBox(btnOrderItems);
                    actionButtons.setSpacing(10);
                    actionButtons.setAlignment(Pos.CENTER);
                    setGraphic(actionButtons);
                }
            }
        });
    }
    public void setCustomerName(String customerName) {
        lblCustomerName.setText(customerName + " order ");
    }
    private void handleOrderItems(Order selectedOrder) {
        try {
            ObservableList<OrderItem> orderItems = OrderItemService.getOrderItems(selectedOrder.getId());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/petstoremanagement/view/customer/order-items.fxml"));
            Parent orderPage = loader.load();

            OrderItemController orderItemController = loader.getController();
            orderItemController.setSelectedCustomerOrder(orderItems);

            // Cập nhật nội dung hiển thị
            orderContent.getChildren().clear();
            orderContent.getChildren().add(orderPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSelectedCustomerOrder(ObservableList<Order> customerOrder) {
        tblOrder.setItems(customerOrder);
    }
}


package com.petstoremanagement.Controller.customer;

import com.petstoremanagement.Model.Order;
import com.petstoremanagement.Model.OrderItem;
import com.petstoremanagement.Model.ServiceBooking;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderItemController implements Initializable {
    @FXML
    private TableView<OrderItem> tblOrderItem = new TableView<OrderItem>();
    @FXML
    private TableColumn<OrderItem,String> cProduct;
    @FXML
    private TableColumn<OrderItem,String> cQuantity;
    @FXML
    private TableColumn<OrderItem,String> cPrice;

    private ObservableList<OrderItem> orderItems;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cPrice.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("UnitPrice"));
        cQuantity.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("Quantity"));
        cProduct.setCellValueFactory(cellData -> cellData.getValue().getProduct().nameProperty());

        tblOrderItem.setItems(orderItems);
    }

    public void setSelectedCustomerOrder(ObservableList<OrderItem> customerOrderItem) {
        tblOrderItem.setItems(customerOrderItem);
    }
}

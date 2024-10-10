package com.petstoremanagement.Controller.customer;

import com.petstoremanagement.Controller.staff.EditStaffController;
import com.petstoremanagement.Model.Customer;
import com.petstoremanagement.Model.Order;
import com.petstoremanagement.Model.ServiceBooking;
import com.petstoremanagement.Service.BookingService;
import com.petstoremanagement.Service.CustomerService;
import com.petstoremanagement.Service.OrderService;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    @FXML
    private StackPane customerContent;
    @FXML
    private TableColumn<Customer, String > cName;
    @FXML
    private TableColumn<Customer, String > cEmail;
    @FXML
    private TableColumn<Customer, String > cAddress;
    @FXML
    private TableColumn<Customer, String > cPhone;
    @FXML
    private TableColumn<Customer, Void > cAction;
    @FXML
    private TextField txtCustomerSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private TableView<Customer> tblCustomer = new TableView<Customer>();


    private ObservableList<Customer> customers;
    private final CustomerService customerService = new CustomerService();
    private final BookingService bookingService = new BookingService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customers = CustomerService.getAllCustomer();
        cName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        cEmail.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
        cAddress.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        cPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));

        tblCustomer.setItems(customers);

        cAction.setCellFactory(param -> new TableCell<Customer, Void>() {
            private final Button btnViewOrder = new Button("Order");
            private final Button btnViewServiceBooking = new Button("Service Booking");
            {
                btnViewOrder.setOnAction(event -> {
                    Customer selectedCustomer = getTableView().getItems().get(getIndex());
                    handelViewCustomerOrder(selectedCustomer);
                });

                btnViewServiceBooking.setOnAction(event -> {
                    Customer selectedCustomer = getTableView().getItems().get(getIndex());
                    handelViewCustomerServiceBooking(selectedCustomer);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox actionButtons = new HBox(btnViewOrder, btnViewServiceBooking);
                    actionButtons.setSpacing(10);
                    actionButtons.setAlignment(Pos.CENTER);
                    setGraphic(actionButtons);
                }
            }
        });

        btnSearch.setOnAction(this::handleSearchButton);
    }

    private void handleSearchButton(ActionEvent event) {
        String searchText = txtCustomerSearch.getText();
        if (searchText != null && !searchText.isEmpty()) {
            ObservableList<Customer> searchResults = CustomerService.findCustomerByPhone(searchText);
            tblCustomer.setItems(searchResults.isEmpty() ? FXCollections.observableArrayList() : searchResults);
        } else {
            tblCustomer.setItems(customers);
        }
        tblCustomer.refresh();
    }

    private void handelViewCustomerServiceBooking(Customer selectedCustomer) {
        try {
            ObservableList<ServiceBooking> customerBooking = BookingService.getBookingsByCustomer(selectedCustomer.getId());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/petstoremanagement/view/customer/service-booking.fxml"));
            Parent serviceBookingPage = loader.load();

            ServiceBookingController serviceBookingController = loader.getController();
            serviceBookingController.setCustomerName(selectedCustomer.getName());
            serviceBookingController.setSelectedCustomerBooking(customerBooking);

            // Cập nhật nội dung hiển thị
            customerContent.getChildren().clear();
            customerContent.getChildren().add(serviceBookingPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handelViewCustomerOrder(Customer selectedCustomer) {
        try {
            ObservableList<Order> customerOrder = OrderService.getOrderByCustomer(selectedCustomer.getId());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/petstoremanagement/view/customer/order.fxml"));
            Parent orderPage = loader.load();

            OrderController orderController = loader.getController();
            orderController.setCustomerName(selectedCustomer.getName());
            orderController.setSelectedCustomerOrder(customerOrder);

            // Cập nhật nội dung hiển thị
            customerContent.getChildren().clear();
            customerContent.getChildren().add(orderPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

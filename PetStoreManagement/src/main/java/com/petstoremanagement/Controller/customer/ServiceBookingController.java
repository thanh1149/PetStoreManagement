package com.petstoremanagement.Controller.customer;

import com.petstoremanagement.Model.ServiceBooking;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.util.ResourceBundle;

public class ServiceBookingController implements Initializable {
    @FXML
    private TableView<ServiceBooking> tblBooking = new TableView<ServiceBooking>();
    @FXML
    private TableColumn<ServiceBooking,String> cServiceName;
    @FXML
    private TableColumn<ServiceBooking,String> cBookingDate;
    @FXML
    private TableColumn<ServiceBooking,String> cStatus;
    @FXML
    private Label lblCustomerName;
    private ObservableList<ServiceBooking> serviceBookings;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cServiceName.setCellValueFactory(cellData -> cellData.getValue().getService().nameProperty());  // Hiển thị tên dịch vụ
        cBookingDate.setCellValueFactory(new PropertyValueFactory<ServiceBooking, String>("BookingDate"));
        cStatus.setCellValueFactory(cellData -> cellData.getValue().getStatus().titleProperty());  // Hiển thị tên status
        
        tblBooking.setItems(serviceBookings);
    }

    public void setCustomerName(String customerName) {
        lblCustomerName.setText(customerName + " service booking list ");
    }
    public void setSelectedCustomerBooking(ObservableList<ServiceBooking> customerBooking) {
        tblBooking.setItems(customerBooking);
    }
}

package com.petstoremanagement.Controller.booking;

import com.petstoremanagement.Model.Order;
import com.petstoremanagement.Model.ServiceBooking;
import com.petstoremanagement.Service.BookingService;
import com.petstoremanagement.Service.OrderService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookingController implements Initializable {
    @FXML
    private Button btnFilter;
    @FXML
    private MenuButton mnSort;
    @FXML
    private MenuItem sortByDateAsc;
    @FXML
    private MenuItem sortByDateDesc;
    @FXML
    private StackPane bookingContent;
    @FXML
    private TableColumn<ServiceBooking, String > cBookingID;
    @FXML
    private TableColumn<ServiceBooking, String > cCustomer;
    @FXML
    private TableColumn<ServiceBooking, String > cService;
    @FXML
    private TableColumn<ServiceBooking, String > cDate;
    @FXML
    private TableColumn<ServiceBooking, String > cStatus;
    @FXML
    private TextField txtBookingSearch;
    @FXML
    private Button btnSearchBooking;
    @FXML
    private TableView<ServiceBooking> tblBooking = new TableView<ServiceBooking>();

    private ObservableList<ServiceBooking> serviceBookings;
    private final BookingService bookingService = new BookingService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceBookings = BookingService.getAllBookings();
        cBookingID.setCellValueFactory(new PropertyValueFactory<ServiceBooking, String>("id"));
        cCustomer.setCellValueFactory(cellData -> cellData.getValue().getCustomer().nameProperty());
        cService.setCellValueFactory(cellData -> cellData.getValue().getService().nameProperty());
        cDate.setCellValueFactory(new PropertyValueFactory<ServiceBooking, String>("BookingDate"));
        cStatus.setCellValueFactory(cellData -> cellData.getValue().getStatus().titleProperty());

        tblBooking.setItems(serviceBookings);
        btnFilter.setOnAction(event -> showFilterDialog());
        btnSearchBooking.setOnAction(this::handleSearchButton);
        sortByDateAsc.setOnAction(event -> handleSortByDate(false));
        sortByDateDesc.setOnAction(event -> handleSortByDate(true));
    }

    private void showFilterDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/petstoremanagement/view/booking/filter.fxml"));
            DialogPane dialogPane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Filter Bookings");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(dialogPane));

            FilterController filterController = loader.getController();
            dialogStage.showAndWait();

            boolean done = filterController.isDoneSelected();
            boolean pending = filterController.isPendingSelected();
            boolean cancel = filterController.isCancelSelected();

            ObservableList<ServiceBooking> filteredBookings = BookingService.filterBookings( done, pending, cancel);

            tblBooking.setItems(filteredBookings);
            tblBooking.refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSearchButton(ActionEvent event) {
        String searchText = txtBookingSearch.getText();
        if (searchText != null && !searchText.isEmpty()) {
            ObservableList<ServiceBooking> searchResults;
            if (searchText.matches("\\d+")) {
                searchResults = BookingService.getBookingByID(Integer.parseInt(searchText));
            } else {
                searchResults = BookingService.getBookingByCustomerName(searchText);
            }
            tblBooking.setItems(searchResults.isEmpty() ? FXCollections.observableArrayList() : searchResults);
        } else {
            tblBooking.setItems(serviceBookings);
        }
        tblBooking.refresh();
    }

    private void handleSortByDate(boolean ascending) {
        ObservableList<ServiceBooking> sortedBooking = BookingService.sortByBookingDate(ascending);
        tblBooking.setItems(sortedBooking);
        tblBooking.refresh();
    }

}

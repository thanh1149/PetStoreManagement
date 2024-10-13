package com.petstoremanagement.Controller.booking;

import com.petstoremanagement.Service.BookingService;
import com.petstoremanagement.Service.OrderService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FilterController implements Initializable {
    @FXML
    private CheckBox cbDone;
    @FXML private CheckBox cbPending;
    @FXML private CheckBox cbCancel;
    @FXML private Button btnReset;
    @FXML private Button btnFilter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnFilter.setOnAction(event -> applyFilter());
        btnReset.setOnAction(event -> resetFilters());
    }

    private void applyFilter() {
        boolean done = cbDone.isSelected();
        boolean pending = cbPending.isSelected();
        boolean cancel = cbCancel.isSelected();

        BookingService.filterBookings(done, pending, cancel);
        Stage stage = (Stage) btnFilter.getScene().getWindow();
        stage.close();
    }

    private void resetFilters() {
        cbDone.setSelected(false);
        cbPending.setSelected(false);
        cbCancel.setSelected(false);
    }


    public boolean isDoneSelected() {
        return cbDone.isSelected();
    }

    public boolean isPendingSelected() {
        return cbPending.isSelected();
    }

    public boolean isCancelSelected() {
        return cbCancel.isSelected();
    }

}

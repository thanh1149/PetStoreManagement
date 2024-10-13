package com.petstoremanagement.Controller.order;

import com.petstoremanagement.Model.Order;
import com.petstoremanagement.Service.OrderService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FilterController implements Initializable {
    @FXML private TextField txtMinPrice;
    @FXML private TextField txtMaxPrice;
    @FXML private CheckBox cbDone;
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
        String minPriceText = txtMinPrice.getText();
        String maxPriceText = txtMaxPrice.getText();

        Double minPrice = minPriceText.isEmpty() ? null : Double.parseDouble(minPriceText);
        Double maxPrice = maxPriceText.isEmpty() ? null : Double.parseDouble(maxPriceText);

        boolean done = cbDone.isSelected();
        boolean pending = cbPending.isSelected();
        boolean cancel = cbCancel.isSelected();

        OrderService.filterOrders(minPrice, maxPrice, done, pending, cancel);
        Stage stage = (Stage) btnFilter.getScene().getWindow();
        stage.close();
    }

    private void resetFilters() {
        txtMinPrice.clear();
        txtMaxPrice.clear();
        cbDone.setSelected(false);
        cbPending.setSelected(false);
        cbCancel.setSelected(false);
    }

    public Double getMinPrice() {
        String minPriceText = txtMinPrice.getText();
        return minPriceText.isEmpty() ? null : Double.parseDouble(minPriceText);
    }

    public Double getMaxPrice() {
        String maxPriceText = txtMaxPrice.getText();
        return maxPriceText.isEmpty() ? null : Double.parseDouble(maxPriceText);
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

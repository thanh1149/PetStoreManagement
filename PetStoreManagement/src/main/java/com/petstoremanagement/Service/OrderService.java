package com.petstoremanagement.Service;

import com.petstoremanagement.Global.MySQLConnection;
import com.petstoremanagement.Model.Order;
import com.petstoremanagement.Model.Service;
import com.petstoremanagement.Model.ServiceBooking;
import com.petstoremanagement.Model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderService {
    private static final Connection con = MySQLConnection.getConnection();
    private static final ObservableList<Order> orders = FXCollections.observableArrayList();

    public static ObservableList<Order> getOrderByCustomer(int customerId) {
        orders.clear();
        String sql = "SELECT o.*, st.title as status_name " +
                "FROM `order` o " +
                "JOIN status st ON o.StatusID = st.id " +
                "WHERE o.CustomerID = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomerID(rs.getInt("CustomerID"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setTotalAmount(rs.getDouble("TotalAmount"));
                order.setStatusID(rs.getInt("StatusID"));

                Status status = new Status();
                status.setTitle(rs.getString("status_name"));
                order.setStatus(status);

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
}

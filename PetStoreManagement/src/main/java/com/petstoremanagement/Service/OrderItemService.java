package com.petstoremanagement.Service;

import com.petstoremanagement.Global.MySQLConnection;
import com.petstoremanagement.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemService {
    private static final Connection con = MySQLConnection.getConnection();
    private static final ObservableList<OrderItem> orderItems = FXCollections.observableArrayList();

    public static ObservableList<OrderItem> getOrderItems(int orderID) {
        orderItems.clear();
        String sql = "SELECT oi.*, p.name as product_name " +
                "FROM `orderitem` oi " +
                "JOIN product p ON oi.ProductID = p.id " +
                "WHERE oi.OrderID = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, orderID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setId(rs.getInt("id"));
                orderItem.setOrderID(rs.getInt("OrderID"));
                orderItem.setProductID(rs.getInt("ProductID"));
                orderItem.setQuantity(rs.getInt("Quantity"));
                orderItem.setUnitPrice(rs.getDouble("UnitPrice"));

                Product product = new Product();
                product.setName(rs.getString("product_name"));
                orderItem.setProduct(product);



                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }
}

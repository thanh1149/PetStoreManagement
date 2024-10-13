package com.petstoremanagement.Service;

import com.petstoremanagement.Global.MySQLConnection;
import com.petstoremanagement.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

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

    public static ObservableList<Order> getAllOrders() {
        ObservableList<Order> orderObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT o.*, c.id AS customerId, c.name AS customerName, " +
                    "s.id AS statusId, s.title AS statusTitle " +
                    "FROM `order` o " +
                    "JOIN customer c ON o.CustomerID = c.id " +
                    "JOIN status s ON o.StatusID = s.id";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomerID(rs.getInt("customerId"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setTotalAmount(rs.getDouble("TotalAmount"));
                order.setStatusID(rs.getInt("statusId"));

                // Tạo đối tượng Customer từ kết quả truy vấn
                Customer customer = new Customer();
                customer.setId(rs.getInt("customerId"));
                customer.setName(rs.getString("customerName"));

                // Gán đối tượng Customer vào Order
                order.setCustomer(customer);

                // Tạo đối tượng Status từ kết quả truy vấn
                Status status = new Status();
                status.setId(rs.getInt("statusId"));
                status.setTitle(rs.getString("statusTitle"));

                // Gán đối tượng Status vào Order
                order.setStatus(status);

                orderObservableList.add(order);
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderObservableList;
    }

    public static ObservableList<Order> getOrderByID(int id) {
        ObservableList<Order> searchResult = FXCollections.observableArrayList();
        try {
            String sql = "SELECT o.*, c.id AS customerId, c.name AS customerName, " +
                    "s.id AS statusId, s.title AS statusTitle " +
                    "FROM `order` o " +
                    "JOIN customer c ON o.CustomerID = c.id " +
                    "JOIN status s ON o.StatusID = s.id " +
                    "WHERE o.id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomerID(rs.getInt("customerId"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setTotalAmount(rs.getDouble("TotalAmount"));
                order.setStatusID(rs.getInt("statusId"));

                Customer customer = new Customer();
                customer.setId(rs.getInt("customerId"));
                customer.setName(rs.getString("customerName"));
                order.setCustomer(customer);

                Status status = new Status();
                status.setId(rs.getInt("statusId"));
                status.setTitle(rs.getString("statusTitle"));
                order.setStatus(status);

                searchResult.add(order);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return searchResult;
    }

    public static ObservableList<Order> getOrderByCustomerName(String name) {
        ObservableList<Order> searchResult = FXCollections.observableArrayList();
        try {
            String sql = "SELECT o.*, c.id AS customerId, c.name AS customerName, " +
                    "s.id AS statusId, s.title AS statusTitle " +
                    "FROM `order` o " +
                    "JOIN customer c ON o.CustomerID = c.id " +
                    "JOIN status s ON o.StatusID = s.id " +
                    "WHERE c.name LIKE ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + name + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomerID(rs.getInt("customerId"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setTotalAmount(rs.getDouble("TotalAmount"));
                order.setStatusID(rs.getInt("statusId"));

                Customer customer = new Customer();
                customer.setId(rs.getInt("customerId"));
                customer.setName(rs.getString("customerName"));
                order.setCustomer(customer);

                Status status = new Status();
                status.setId(rs.getInt("statusId"));
                status.setTitle(rs.getString("statusTitle"));
                order.setStatus(status);

                searchResult.add(order);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResult;
    }

    public static ObservableList<Order> sortOrdersByDate(boolean ascending) {
        ObservableList<Order> orderObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT o.*, c.id AS customerId, c.name AS customerName, " +
                    "s.id AS statusId, s.title AS statusTitle " +
                    "FROM `order` o " +
                    "JOIN customer c ON o.CustomerID = c.id " +
                    "JOIN status s ON o.StatusID = s.id " +
                    "ORDER BY o.OrderDate " + (ascending ? "ASC" : "DESC");
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomerID(rs.getInt("customerId"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setTotalAmount(rs.getDouble("TotalAmount"));
                order.setStatusID(rs.getInt("statusId"));

                Customer customer = new Customer();
                customer.setId(rs.getInt("customerId"));
                customer.setName(rs.getString("customerName"));
                order.setCustomer(customer);

                Status status = new Status();
                status.setId(rs.getInt("statusId"));
                status.setTitle(rs.getString("statusTitle"));
                order.setStatus(status);

                orderObservableList.add(order);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderObservableList;
    }

    public static ObservableList<Order> sortOrdersByTotalAmount(boolean ascending) {
        ObservableList<Order> orderObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT o.*, c.id AS customerId, c.name AS customerName, " +
                    "s.id AS statusId, s.title AS statusTitle " +
                    "FROM `order` o " +
                    "JOIN customer c ON o.CustomerID = c.id " +
                    "JOIN status s ON o.StatusID = s.id " +
                    "ORDER BY o.TotalAmount " + (ascending ? "ASC" : "DESC");
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomerID(rs.getInt("customerId"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setTotalAmount(rs.getDouble("TotalAmount"));
                order.setStatusID(rs.getInt("statusId"));

                Customer customer = new Customer();
                customer.setId(rs.getInt("customerId"));
                customer.setName(rs.getString("customerName"));
                order.setCustomer(customer);

                Status status = new Status();
                status.setId(rs.getInt("statusId"));
                status.setTitle(rs.getString("statusTitle"));
                order.setStatus(status);

                orderObservableList.add(order);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderObservableList;
    }

    public static ObservableList<Order> filterOrders(Double minPrice, Double maxPrice, boolean done, boolean pending, boolean cancel) {
        ObservableList<Order> filteredOrders = FXCollections.observableArrayList();
        try {
            StringBuilder sql = new StringBuilder("SELECT o.*, c.id AS customerId, c.name AS customerName, " +
                    "s.id AS statusId, s.title AS statusTitle " +
                    "FROM `order` o " +
                    "JOIN customer c ON o.CustomerID = c.id " +
                    "JOIN status s ON o.StatusID = s.id WHERE 1=1");

            if (minPrice != null) {
                sql.append(" AND o.TotalAmount >= ?");
            }
            if (maxPrice != null) {
                sql.append(" AND o.TotalAmount <= ?");
            }

            if (done || pending || cancel) {
                sql.append(" AND o.StatusID IN (");
                if (done) sql.append("1,");
                if (pending) sql.append("2,");
                if (cancel) sql.append("3,");
                sql.deleteCharAt(sql.length() - 1);
                sql.append(")");
            }

            PreparedStatement pst = con.prepareStatement(sql.toString());
            int paramIndex = 1;
            if (minPrice != null) {
                pst.setDouble(paramIndex++, minPrice);
            }
            if (maxPrice != null) {
                pst.setDouble(paramIndex++, maxPrice);
            }

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomerID(rs.getInt("customerId"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setTotalAmount(rs.getDouble("TotalAmount"));
                order.setStatusID(rs.getInt("statusId"));

                Customer customer = new Customer();
                customer.setId(rs.getInt("customerId"));
                customer.setName(rs.getString("customerName"));
                order.setCustomer(customer);

                Status status = new Status();
                status.setId(rs.getInt("statusId"));
                status.setTitle(rs.getString("statusTitle"));
                order.setStatus(status);

                filteredOrders.add(order);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filteredOrders;
    }


}

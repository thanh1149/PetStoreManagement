package com.petstoremanagement.Service;

import com.petstoremanagement.Global.MySQLConnection;
import com.petstoremanagement.Model.Category;
import com.petstoremanagement.Model.Customer;
import com.petstoremanagement.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;

public class CustomerService {
    private static final Connection con = MySQLConnection.getConnection();
    private static final ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();


    public static ObservableList<Customer> getAllCustomer() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM customer";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setPhone(rs.getString("phone"));
                customer.setEmail(rs.getString("email"));
                customer.setAddress(rs.getString("address"));
                customer.setCreated_at(rs.getTimestamp("created_at"));
                customer.setUpdated_at(rs.getTimestamp("updated_at"));

                customers.add(customer);
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }


    public static ObservableList<Customer> findCustomerByPhone(String customerPhone){
        ObservableList<Customer> searchResult = FXCollections.observableArrayList();
        try {
            String sql = "SELECT id,name,phone,email,address,created_at,updated_at FROM customer WHERE phone LIKE ? ";
            PreparedStatement pst =  con.prepareStatement(sql);
            pst.setString(1,"%" + customerPhone + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                Timestamp created_at = rs.getTimestamp("created_at");
                Timestamp updated_at = rs.getTimestamp("updated_at");

                Customer customer = new Customer(id,name,phone,email,address,created_at,updated_at);
                searchResult.add(customer);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return searchResult;
    }

   public Customer getCustomerById(int id) {
        Customer customer = null;
        String sql = "SELECT * FROM customer WHERE id = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    customer = new Customer();
                    customer.setId(rs.getInt("id"));
                    customer.setName(rs.getString("name"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setEmail(rs.getString("email"));
                    customer.setAddress(rs.getString("address"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

}

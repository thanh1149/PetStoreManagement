package com.petstoremanagement.Service;

import com.petstoremanagement.Global.MySQLConnection;
import com.petstoremanagement.Model.Category;
import com.petstoremanagement.Model.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryService {
    private static final Connection conn = MySQLConnection.getConnection();
    private static final ObservableList<Category> roleObservableList = FXCollections.observableArrayList();
    private static boolean isInitialized = false;

    public static ObservableList<Category> getAllCategory() {
        if (!isInitialized) {
            initializeCategory();
        }
        return roleObservableList;
    }

    private static void initializeCategory() {
        roleObservableList.clear();
        try {
            String sql = "SELECT * FROM category";
            try (PreparedStatement pst = conn.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    Category category = new Category();
                    category.setId(rs.getInt("id"));
                    category.setName(rs.getString("name"));
                    category.setCreated_at(rs.getTimestamp("created_at"));
                    category.setUpdated_at(rs.getTimestamp("update_at"));

                    roleObservableList.add(category);
                }
            }
            isInitialized = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

package com.petstoremanagement.Service;

import com.petstoremanagement.Global.MySQLConnection;
import com.petstoremanagement.Model.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.sql.*;

public class RoleService {
    private static final Connection conn = MySQLConnection.getConnection();
    private static final ObservableList<Role> roleObservableList = FXCollections.observableArrayList();
    private static boolean isInitialized = false;

    public static ObservableList<Role> getAllRole() {
        if (!isInitialized) {
            initializeRoles();
        }
        return roleObservableList;
    }

    private static void initializeRoles() {
        roleObservableList.clear();
        try {
            String sql = "SELECT * FROM role";
            try (PreparedStatement pst = conn.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    Role role = new Role();
                    role.setId(rs.getString("id"));
                    role.setTitle(rs.getString("title"));
                    role.setCreated_at(rs.getTimestamp("created_at"));
                    role.setUpdated_at(rs.getTimestamp("updated_at"));

                    roleObservableList.add(role);
                }
            }
            isInitialized = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

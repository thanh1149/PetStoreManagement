package com.petstoremanagement.Service;

import com.petstoremanagement.Global.MySQLConnection;
import com.petstoremanagement.Model.Role;
import com.petstoremanagement.Model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusService {
    private static final Connection conn = MySQLConnection.getConnection();
    private static final ObservableList<Status> statusObservableList = FXCollections.observableArrayList();
    private static boolean isInitialized = false;

    public static ObservableList<Status> getAllStatus() {
        if (!isInitialized) {
            initializeRoles();
        }
        return statusObservableList;
    }

    private static void initializeRoles() {
        statusObservableList.clear();
        try {
            String sql = "SELECT * FROM status";
            try (PreparedStatement pst = conn.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    Status status = new Status();
                    status.setId(rs.getInt("id"));
                    status.setTitle(rs.getString("title"));
                    status.setCreated_at(rs.getTimestamp("created_at"));
                    status.setUpdated_at(rs.getTimestamp("updated_at"));

                    statusObservableList.add(status);
                }
            }
            isInitialized = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

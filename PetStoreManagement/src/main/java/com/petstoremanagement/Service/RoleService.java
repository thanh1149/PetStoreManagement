package com.petstoremanagement.Service;

import com.petstoremanagement.Global.MySQLConnection;
import com.petstoremanagement.Model.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RoleService {
    private static final Connection conn = MySQLConnection.getConnection();
    private static final ObservableList<Role> roleObservableList = FXCollections.observableArrayList();

    public static ObservableList<Role> getAllRole() {
        try {
            String sql = "SELECT * FROM role";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (((ResultSet) rs).next()){
                Role role = new Role();
                role.setId(rs.getString("id"));
                role.setTitle(rs.getString("title"));
                role.setCreated_at(rs.getTimestamp("created_at"));
                role.setUpdated_at(rs.getTimestamp("updated_at"));

                roleObservableList.add(role);
            }
            rs.close();
            pst.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return roleObservableList;
    }
}

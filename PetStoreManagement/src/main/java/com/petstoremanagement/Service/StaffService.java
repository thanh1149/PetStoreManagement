package com.petstoremanagement.Service;

import com.petstoremanagement.Global.MySQLConnection;
import com.petstoremanagement.Model.Role;
import com.petstoremanagement.Model.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.mindrot.jbcrypt.BCrypt;

import java.io.InputStream;
import java.net.URL;
import java.sql.*;

public class StaffService {
    private static final Connection con = MySQLConnection.getConnection();
    private static final ObservableList<Staff> staffObservableList = FXCollections.observableArrayList();

    public static Staff authenticate(String username) {
        String sql = "SELECT s.Password, s.roleID, r.title " +
                "FROM staff s " +
                "JOIN role r ON s.roleID = r.id " +
                "WHERE s.Username = ?";
        Staff staff = new Staff();
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                staff.setUsername(username);
                staff.setPassword(rs.getString("Password"));
                staff.setRoleID(rs.getString("roleID"));

                Role role = new Role();
                role.setId(rs.getString("roleID"));
                role.setTitle(rs.getString("title"));
                staff.setRole(role);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return staff;
    }


    public static boolean addStaff(String roleID, String username,String password,String fullName,String email,String phone ){
        String hashpw = BCrypt.hashpw(password,BCrypt.gensalt());
        try {
            String sql = "INSERT INTO staff(roleID,Username,Password,FullName,Email,Phone) VALUES(?,?,?,?,?,?)";
            PreparedStatement pts = MySQLConnection.getConnection().prepareStatement(sql);
            pts.setString(1,roleID);
            pts.setString(2,username);
            pts.setString(3,hashpw);
            pts.setString(4,fullName);
            pts.setString(5,email);
            pts.setString(6,phone);
            pts.executeUpdate();
            return true;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static ObservableList<Staff> getAllStaff() {
        ObservableList<Staff> staffObservableList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM staff";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffID(rs.getInt("StaffID"));
                staff.setRoleID(rs.getString("roleID"));
                staff.setUsername(rs.getString("Username"));
                staff.setPassword(rs.getString("Password"));
                staff.setFullName(rs.getString("FullName"));
                staff.setEmail(rs.getString("Email"));
                staff.setPhone(rs.getString("Phone"));

                staffObservableList.add(staff);
            }

            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return staffObservableList;
    }


    public static ObservableList<Staff> findStaffName(String name){
        ObservableList<Staff> searchResult = FXCollections.observableArrayList();
        try {
            String sql = "SELECT StaffID,roleID,Username,Password,FullName,Email,Phone FROM staff WHERE FullName LIKE ? ";
            PreparedStatement pst =  con.prepareStatement(sql);
            pst.setString(1,"%" + name + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                int StaffID = rs.getInt("StaffID");
                String roleID = rs.getString("roleID");
                String Username = rs.getString("Username");
                String Password = rs.getString("Password");
                String FullName = rs.getString("FullName");
                String Email = rs.getString("Email");
                String Phone = rs.getString("Phone");

                Staff staff = new Staff(StaffID,roleID,Username,Password,FullName,Email,Phone);
                searchResult.add(staff);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return searchResult;
    }

    public static void edit(Staff staff) {
        try {
            String sql = "UPDATE staff SET roleID = ?, Username = ?, Password = ?, FullName = ?, Email = ?, Phone = ? WHERE StaffID = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, staff.getRoleID());
            pst.setString(2, staff.getUsername());
            if (!staff.getPassword().isEmpty()) {
                String hashedPassword = BCrypt.hashpw(staff.getPassword(), BCrypt.gensalt());
                pst.setString(3, hashedPassword);
            } else {
                pst.setString(3, staff.getPassword());
            }

            pst.setString(4, staff.getFullName());
            pst.setString(5, staff.getEmail());
            pst.setString(6, staff.getPhone());
            pst.setInt(7,staff.getStaffID());
            pst.executeUpdate();

            staffObservableList.stream()
                    .filter(s -> s.getStaffID() == staff.getStaffID()).findFirst()
                    .ifPresent(s ->{
                        s.setRoleID(staff.getRoleID());
                        s.setUsername(staff.getUsername());
                        s.setPassword(staff.getPassword());
                        s.setFullName(staff.getFullName());
                        s.setEmail(staff.getEmail());
                        s.setPhone(staff.getPhone());
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void remove(Staff staff) {
        try {
            String sql = "DELETE FROM staff WHERE StaffID = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1,staff.getStaffID());
            pst.executeUpdate();
            staffObservableList.removeIf(s -> s.getStaffID() == staff.getStaffID());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }



}

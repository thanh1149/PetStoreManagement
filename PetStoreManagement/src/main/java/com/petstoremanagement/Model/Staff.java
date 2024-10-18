package com.petstoremanagement.Model;

import java.time.LocalDateTime;

public class Staff {
    private int staffID;
    private String roleID;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private Role role;
    private String reset_token;
    private LocalDateTime reset_token_expiry;
    public Staff(){}

    public Staff(int staffID, String roleID, String username, String password, String fullName, String email, String phone) {
        this.staffID = staffID;
        this.roleID = roleID;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getReset_token() {
        return reset_token;
    }

    public void setReset_token(String reset_token) {
        this.reset_token = reset_token;
    }

    public LocalDateTime getReset_token_expiry() {
        return reset_token_expiry;
    }

    public void setReset_token_expiry(LocalDateTime reset_token_expiry) {
        this.reset_token_expiry = reset_token_expiry;
    }
}

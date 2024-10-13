package com.petstoremanagement.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;

public class Customer {
    private int id;
    private StringProperty name;
    private String phone;
    private String email;
    private String address;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Customer(){
        this.name = new SimpleStringProperty();
    }

    public Customer(int id, String name, String phone, String email,String address, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}

package com.petstoremanagement.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import java.sql.Timestamp;

public class Service {
    private int id;
    private int CategoryID;
    private StringProperty name;  // Sử dụng StringProperty cho tên dịch vụ
    private String description;
    private double price;
    private Image image;
    private Timestamp created_at;
    private Timestamp updated_at;
    private Category category;

    public Service() {
        this.name = new SimpleStringProperty();  // Khởi tạo StringProperty
    }

    public Service(int id, int categoryID, String name, String description, double price, Image image, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        CategoryID = categoryID;
        this.name = new SimpleStringProperty(name);  // Khởi tạo StringProperty
        this.description = description;
        this.price = price;
        this.image = image;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public String getName() {
        return name.get();  // Lấy giá trị từ StringProperty
    }

    public void setName(String name) {
        this.name.set(name);  // Đặt giá trị cho StringProperty
    }

    public StringProperty nameProperty() {
        return name;  // Trả về StringProperty để sử dụng với TableView
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Image getServiceImage() {
        return image;
    }

    public void setServiceImage(Image image) {
        this.image = image;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

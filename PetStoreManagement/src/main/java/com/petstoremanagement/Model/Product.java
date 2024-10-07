package com.petstoremanagement.Model;

import javafx.scene.image.Image;

import java.sql.Timestamp;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int StockQuantity;
    private int CategoryID;
    private Image image;
    private Timestamp created_at;
    private Timestamp updated_at;
    private Category category;

    public Product(){}
    public Product(int id, String name, String description, double price, int stockQuantity, int categoryID, Image image, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        StockQuantity = stockQuantity;
        CategoryID = categoryID;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getStockQuantity() {
        return StockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        StockQuantity = stockQuantity;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public Image getProductImage() {
        return image;
    }

    public void setProductImage(Image image) {
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

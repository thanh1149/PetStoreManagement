package com.petstoremanagement.Service;

import com.petstoremanagement.Global.MySQLConnection;
import com.petstoremanagement.Model.Category;
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

public class ProductService {
    private static final Connection con = MySQLConnection.getConnection();
    private static final ObservableList<Product> productObservableList = FXCollections.observableArrayList();
    private static File selectedImageFile;


    public static boolean addProduct(Product product, File selectedImageFile) {
        if (ProductService.selectedImageFile != null) {
            try (FileInputStream fis = new FileInputStream(ProductService.selectedImageFile)) {
                String sql = "INSERT INTO product(name,description,price,StockQuantity,CategoryID,ProductImage,created_at,update_at) VALUES(?,?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, product.getName());
                pst.setString(2, product.getDescription());
                pst.setDouble(3, product.getPrice());
                pst.setInt(4, product.getStockQuantity());
                pst.setInt(5, product.getCategoryID());
                pst.setBinaryStream(6, fis, (int) ProductService.selectedImageFile.length());
                pst.setTimestamp(7, product.getCreated_at());
                pst.setTimestamp(8, product.getUpdated_at());
                pst.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No picture choose.");
        }
        return true;
    }

    public static ObservableList<Product> getAllProduct() {
        ObservableList<Product> productObservableList1 = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM product";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setStockQuantity(rs.getInt("StockQuantity"));
                product.setCategoryID(rs.getInt("CategoryID"));
                Blob blob = rs.getBlob("ProductImage");
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    Image image = new Image(inputStream);
                    product.setProductImage(image);
                }
                product.setCreated_at(rs.getTimestamp("created_at"));
                product.setUpdated_at(rs.getTimestamp("update_at"));

                productObservableList1.add(product);
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productObservableList1;
    }


    public static ObservableList<Product> findProductName(String productName){
        ObservableList<Product> searchResult = FXCollections.observableArrayList();
        try {
            String sql = "SELECT id,name,description,price,StockQuantity,CategoryID,ProductImage,created_at,update_at FROM product WHERE name LIKE ? ";
            PreparedStatement pst =  con.prepareStatement(sql);
            pst.setString(1,"%" + productName + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int StockQuantity = rs.getInt("StockQuantity");
                int CategoryID = rs.getInt("CategoryID");
                Blob blob = rs.getBlob("ProductImage");
                Image ProductImage = null;
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ProductImage = new Image(inputStream);
                }
                Timestamp created_at = rs.getTimestamp("created_at");
                Timestamp update_at = rs.getTimestamp("update_at");

                Product product = new Product(id,name,description,price,StockQuantity,CategoryID,ProductImage,created_at,update_at);
                searchResult.add(product);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return searchResult;
    }

    public static boolean edit(Product product) {
        try {
            String sql = "UPDATE product SET name = ?, description = ?, price = ?,StockQuantity = ?, CategoryID = ?, ProductImage = ?, created_at = ?, update_at = ? WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, product.getName());
            pst.setString(2, product.getDescription());
            pst.setDouble(3, product.getPrice());
            pst.setInt(4,product.getStockQuantity());
            pst.setInt(5, product.getCategory().getId());

            if (product.getProductImage() != null && product.getProductImage().getUrl() != null) {
                String url = product.getProductImage().getUrl();
                InputStream inputStream = new URL(url).openStream();
                pst.setBinaryStream(6, inputStream);
            } else {
                String selectSql = "SELECT ProductImage FROM product WHERE id = ?";
                PreparedStatement selectPst = con.prepareStatement(selectSql);
                selectPst.setInt(1, product.getId());
                ResultSet rs = selectPst.executeQuery();
                if (rs.next()) {
                    InputStream oldImage = rs.getBinaryStream("ProductImage");
                    pst.setBinaryStream(6, oldImage);
                } else {
                    pst.setNull(6, Types.BLOB);
                }
            }

            pst.setTimestamp(7, product.getCreated_at());
            pst.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            pst.setInt(9, product.getId());
            pst.executeUpdate();

            productObservableList.stream()
                    .filter(s -> s.getId() == product.getId()).findFirst()
                    .ifPresent(s -> {
                        s.setName(product.getName());
                        s.setDescription(product.getDescription());
                        s.setPrice(product.getPrice());
                        s.setStockQuantity(product.getStockQuantity());
                        s.setCategoryID(product.getCategoryID());
                        s.setProductImage(product.getProductImage());
                        s.setCreated_at(product.getCreated_at());
                        s.setUpdated_at(new Timestamp(System.currentTimeMillis()));
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    public static void remove(Product product) {
        try {
            String sql = "DELETE FROM product WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1,product.getId());
            pst.executeUpdate();
            productObservableList.removeIf(p -> p.getId() == product.getId());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Product getProductID(int id) {
        Product product = null;
        String sql = "SELECT p.*, c.id as category_id, c.name as category_name FROM product p " +
                "LEFT JOIN category c ON p.CategoryID = c.id WHERE p.id = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setPrice(rs.getDouble("price"));
                    product.setStockQuantity(rs.getInt("StockQuantity"));
                    Blob blob = rs.getBlob("ProductImage");
                    if (blob != null) {
                        try (InputStream is = blob.getBinaryStream()) {
                            Image image = new Image(is);
                            product.setProductImage(image);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    product.setCreated_at(rs.getTimestamp("created_at"));
                    product.setUpdated_at(rs.getTimestamp("update_at"));

                    // Create and set Role object
                    Category category = new Category();
                    category.setId(rs.getInt("category_id"));
                    category.setName(rs.getString("category_name"));
                    product.setCategory(category);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public static File selectImageFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedImageFile = file;
        }
        return file;
    }
}

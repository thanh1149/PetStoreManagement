package com.petstoremanagement.Service;

import com.petstoremanagement.Global.MySQLConnection;
import com.petstoremanagement.Model.Category;
import com.petstoremanagement.Model.Service;
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

public class ServService {
    private static final Connection con = MySQLConnection.getConnection();
    private static final ObservableList<Service> serviceObservableList = FXCollections.observableArrayList();
    private static File selectedImageFile;


    public static boolean addService(Service service, File selectedImageFile) {
        if (ServService.selectedImageFile != null) {
            try (FileInputStream fis = new FileInputStream(ServService.selectedImageFile)) {
                String sql = "INSERT INTO service(name,description,price,CategoryID,ServiceImage,created_at,update_at) VALUES(?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, service.getName());
                pst.setString(2, service.getDescription());
                pst.setDouble(3, service.getPrice());
                pst.setInt(4, service.getCategoryID());
                pst.setBinaryStream(5, fis, (int) ServService.selectedImageFile.length());
                pst.setTimestamp(6, service.getCreated_at());
                pst.setTimestamp(7, service.getUpdated_at());
                pst.executeUpdate();

//                serviceObservableList.add(0, service);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No picture choose.");
        }
        return true;
    }

    public static ObservableList<Service> getAllService() {
        ObservableList<Service> serviceObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM service";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Service service = new Service();
                service.setId(rs.getInt("id"));
                service.setName(rs.getString("name"));
                service.setDescription(rs.getString("description"));
                service.setPrice(rs.getDouble("price"));
                service.setCategoryID(rs.getInt("CategoryID"));
                Blob blob = rs.getBlob("ServiceImage");
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    Image image = new Image(inputStream);
                    service.setServiceImage(image);
                }
                service.setCreated_at(rs.getTimestamp("created_at"));
                service.setUpdated_at(rs.getTimestamp("update_at"));

                serviceObservableList.add(service);
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceObservableList;
    }


    public static ObservableList<Service> findServiceName(String serviceName){
        ObservableList<Service> searchResult = FXCollections.observableArrayList();
        try {
            String sql = "SELECT id,name,description,price,CategoryID,ServiceImage,created_at,update_at FROM service WHERE name LIKE ? ";
            PreparedStatement pst =  con.prepareStatement(sql);
            pst.setString(1,"%" + serviceName + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int CategoryID = rs.getInt("CategoryID");
                Blob blob = rs.getBlob("ServiceImage");
                Image ServiceImage = null;
                if (blob != null) {
                    InputStream inputStream = blob.getBinaryStream();
                    ServiceImage = new Image(inputStream);
                }
                Timestamp created_at = rs.getTimestamp("created_at");
                Timestamp update_at = rs.getTimestamp("update_at");

                Service service = new Service(id,CategoryID,name,description,price,ServiceImage,created_at,update_at);
                searchResult.add(service);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return searchResult;
    }

//    public static boolean edit(Service service) {
//        try {
//            String sql = "UPDATE service SET name = ?, description = ?, price = ?, CategoryID = ?, ServiceImage = ?, created_at = ?, update_at = ? WHERE id = ?";
//            PreparedStatement pst = con.prepareStatement(sql);
//            pst.setString(1, service.getName());
//            pst.setString(2, service.getDescription());
//            pst.setDouble(3, service.getPrice());
//            pst.setInt(4, service.getCategory().getId());
//            if (service.getServiceImage() != null) {
//                String url = service.getServiceImage().getUrl();
//                InputStream inputStream = new URL(url).openStream();
//                pst.setBinaryStream(5, inputStream);
//            } else {
//                pst.setNull(5, Types.BLOB);
//            }
//            pst.setTimestamp(6,service.getCreated_at());
//            pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
//            pst.setInt(8,service.getId());
//            pst.executeUpdate();
//
//            serviceObservableList.stream()
//                    .filter(s -> s.getId() == service.getId()).findFirst()
//                    .ifPresent(s ->{
//                        s.setName(service.getName());
//                        s.setDescription(service.getDescription());
//                        s.setPrice(service.getPrice());
//                        s.setCategoryID(service.getCategoryID());
//                        s.setServiceImage(service.getServiceImage());
//                        s.setCreated_at(service.getCreated_at());
//                        s.setUpdated_at(new Timestamp(System.currentTimeMillis()));
//
//                    });
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return true;
//    }

    public static boolean edit(Service service) {
        try {
            String sql = "UPDATE service SET name = ?, description = ?, price = ?, CategoryID = ?, ServiceImage = ?, created_at = ?, update_at = ? WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, service.getName());
            pst.setString(2, service.getDescription());
            pst.setDouble(3, service.getPrice());
            pst.setInt(4, service.getCategory().getId());

            if (service.getServiceImage() != null && service.getServiceImage().getUrl() != null) {
                String url = service.getServiceImage().getUrl();
                InputStream inputStream = new URL(url).openStream();
                pst.setBinaryStream(5, inputStream);
            } else {
                String selectSql = "SELECT ServiceImage FROM service WHERE id = ?";
                PreparedStatement selectPst = con.prepareStatement(selectSql);
                selectPst.setInt(1, service.getId());
                ResultSet rs = selectPst.executeQuery();
                if (rs.next()) {
                    InputStream oldImage = rs.getBinaryStream("ServiceImage");
                    pst.setBinaryStream(5, oldImage);
                } else {
                    pst.setNull(5, Types.BLOB);
                }
            }

            pst.setTimestamp(6, service.getCreated_at());
            pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            pst.setInt(8, service.getId());
            pst.executeUpdate();

            serviceObservableList.stream()
                    .filter(s -> s.getId() == service.getId()).findFirst()
                    .ifPresent(s -> {
                        s.setName(service.getName());
                        s.setDescription(service.getDescription());
                        s.setPrice(service.getPrice());
                        s.setCategoryID(service.getCategoryID());
                        s.setServiceImage(service.getServiceImage());
                        s.setCreated_at(service.getCreated_at());
                        s.setUpdated_at(new Timestamp(System.currentTimeMillis()));
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    public static void remove(Service service) {
        try {
            String sql = "DELETE FROM service WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1,service.getId());
            pst.executeUpdate();
            serviceObservableList.removeIf(s -> s.getId() == service.getId());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Service getServiceID(int id) {
        Service service = null;
        String sql = "SELECT s.*, c.id as category_id, c.name as category_name FROM service s " +
                "LEFT JOIN category c ON s.CategoryID = c.id WHERE s.id = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    service = new Service();
                    service.setId(rs.getInt("id"));
                    service.setName(rs.getString("name"));
                    service.setDescription(rs.getString("description"));
                    service.setPrice(rs.getDouble("price"));
                    Blob blob = rs.getBlob("ServiceImage");
                    if (blob != null) {
                        try (InputStream is = blob.getBinaryStream()) {
                            Image image = new Image(is);
                            service.setServiceImage(image);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    service.setCreated_at(rs.getTimestamp("created_at"));
                    service.setUpdated_at(rs.getTimestamp("update_at"));

                    // Create and set Role object
                    Category category = new Category();
                    category.setId(rs.getInt("category_id"));
                    category.setName(rs.getString("category_name"));
                    service.setCategory(category);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return service;
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

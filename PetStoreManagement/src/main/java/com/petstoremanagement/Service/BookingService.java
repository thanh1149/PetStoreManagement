package com.petstoremanagement.Service;

import com.petstoremanagement.Global.MySQLConnection;
import com.petstoremanagement.Model.Customer;
import com.petstoremanagement.Model.Service;
import com.petstoremanagement.Model.ServiceBooking;
import com.petstoremanagement.Model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingService {
    private static final Connection con = MySQLConnection.getConnection();
    private static final ObservableList<ServiceBooking> serviceBookings = FXCollections.observableArrayList();

    public static ObservableList<ServiceBooking> getBookingsByCustomer(int customerId) {
        serviceBookings.clear();
        String sql = "SELECT sb.*, s.name as service_name, st.title as status_name " +
                "FROM servicebooking sb " +
                "JOIN service s ON sb.ServiceID = s.id " +
                "JOIN status st ON sb.StatusID = st.id " +
                "WHERE sb.CustomerID = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ServiceBooking booking = new ServiceBooking();
                booking.setId(rs.getInt("id"));
                booking.setCustomerID(rs.getInt("CustomerID"));
                booking.setServiceID(rs.getInt("ServiceID"));
                booking.setBookingDate(rs.getTimestamp("BookingDate"));
                booking.setStatusID(rs.getInt("StatusID"));

                Service service = new Service();
                service.setName(rs.getString("service_name"));
                booking.setService(service);

                Status status = new Status();
                status.setTitle(rs.getString("status_name"));
                booking.setStatus(status);

                serviceBookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return serviceBookings;
    }

}

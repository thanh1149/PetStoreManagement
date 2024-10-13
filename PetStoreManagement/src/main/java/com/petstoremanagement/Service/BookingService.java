package com.petstoremanagement.Service;

import com.petstoremanagement.Global.MySQLConnection;
import com.petstoremanagement.Model.*;
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


    public static ObservableList<ServiceBooking> getAllBookings() {
        serviceBookings.clear();
        String sql = "SELECT sb.*, c.name as customer_name, s.name as service_name, st.title as status_name " +
                "FROM servicebooking sb " +
                "JOIN customer c ON sb.CustomerID = c.id " +
                "JOIN service s ON sb.ServiceID = s.id " +
                "JOIN status st ON sb.StatusID = st.id";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ServiceBooking booking = new ServiceBooking();
                booking.setId(rs.getInt("id"));
                booking.setCustomerID(rs.getInt("CustomerID"));
                booking.setServiceID(rs.getInt("ServiceID"));
                booking.setBookingDate(rs.getTimestamp("BookingDate"));
                booking.setStatusID(rs.getInt("StatusID"));

                Customer customer = new Customer();
                customer.setName(rs.getString("customer_name"));
                booking.setCustomer(customer);

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

    public static ObservableList<ServiceBooking> getBookingByID(int id) {
        ObservableList<ServiceBooking> searchResult = FXCollections.observableArrayList();
        try {
            String sql = "SELECT sb.*, c.name as customer_name, s.name as service_name, st.title as status_name " +
                    "FROM servicebooking sb " +
                    "JOIN customer c ON sb.CustomerID = c.id " +
                    "JOIN service s ON sb.ServiceID = s.id " +
                    "JOIN status st ON sb.StatusID = st.id " +
                    "WHERE sb.id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ServiceBooking booking = new ServiceBooking();
                booking.setId(rs.getInt("id"));
                booking.setCustomerID(rs.getInt("CustomerID"));
                booking.setServiceID(rs.getInt("ServiceID"));
                booking.setBookingDate(rs.getTimestamp("BookingDate"));
                booking.setStatusID(rs.getInt("StatusID"));

                Customer customer = new Customer();
                customer.setName(rs.getString("customer_name"));
                booking.setCustomer(customer);

                Service service = new Service();
                service.setName(rs.getString("service_name"));
                booking.setService(service);

                Status status = new Status();
                status.setTitle(rs.getString("status_name"));
                booking.setStatus(status);

                searchResult.add(booking);
            }
            rs.close();
            pst.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return searchResult;
    }

    public static ObservableList<ServiceBooking> getBookingByCustomerName(String name) {
        ObservableList<ServiceBooking> searchResult = FXCollections.observableArrayList();
        try {
            String sql = "SELECT sb.*, c.name as customer_name, s.name as service_name, st.title as status_name " +
                    "FROM servicebooking sb " +
                    "JOIN customer c ON sb.CustomerID = c.id " +
                    "JOIN service s ON sb.ServiceID = s.id " +
                    "JOIN status st ON sb.StatusID = st.id " +
                    "WHERE c.name LIKE ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + name + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ServiceBooking booking = new ServiceBooking();
                booking.setId(rs.getInt("id"));
                booking.setCustomerID(rs.getInt("CustomerID"));
                booking.setServiceID(rs.getInt("ServiceID"));
                booking.setBookingDate(rs.getTimestamp("BookingDate"));
                booking.setStatusID(rs.getInt("StatusID"));

                Customer customer = new Customer();
                customer.setName(rs.getString("customer_name"));
                booking.setCustomer(customer);

                Service service = new Service();
                service.setName(rs.getString("service_name"));
                booking.setService(service);

                Status status = new Status();
                status.setTitle(rs.getString("status_name"));
                booking.setStatus(status);

                searchResult.add(booking);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResult;
    }


    public static ObservableList<ServiceBooking> sortByBookingDate(boolean ascending) {
        serviceBookings.clear();
        String sql = "SELECT sb.*, c.name as customer_name, s.name as service_name, st.title as status_name " +
                "FROM servicebooking sb " +
                "JOIN customer c ON sb.CustomerID = c.id " +
                "JOIN service s ON sb.ServiceID = s.id " +
                "JOIN status st ON sb.StatusID = st.id " +
                "ORDER BY sb.BookingDate " + (ascending ? "ASC" : "DESC");

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ServiceBooking booking = new ServiceBooking();
                booking.setId(rs.getInt("id"));
                booking.setCustomerID(rs.getInt("CustomerID"));
                booking.setServiceID(rs.getInt("ServiceID"));
                booking.setBookingDate(rs.getTimestamp("BookingDate"));
                booking.setStatusID(rs.getInt("StatusID"));

                Customer customer = new Customer();
                customer.setName(rs.getString("customer_name"));
                booking.setCustomer(customer);

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

    public static ObservableList<ServiceBooking> filterBookings(boolean done, boolean pending, boolean cancel) {
        ObservableList<ServiceBooking> filteredBookings = FXCollections.observableArrayList();
        try {
            StringBuilder sql = new StringBuilder("SELECT sb.*, c.id AS customerId, c.name AS customerName, " +
                    "s.id AS serviceId, s.name AS serviceName, st.id AS statusId, st.title AS statusTitle " +
                    "FROM servicebooking sb " +
                    "JOIN customer c ON sb.CustomerID = c.id " +
                    "JOIN service s ON sb.ServiceID = s.id " +
                    "JOIN status st ON sb.StatusID = st.id WHERE 1=1");

            if (done || pending || cancel) {
                sql.append(" AND sb.StatusID IN (");
                if (done) sql.append("1,");
                if (pending) sql.append("2,");
                if (cancel) sql.append("3,");
                sql.deleteCharAt(sql.length() - 1);
                sql.append(")");
            }

            PreparedStatement pst = con.prepareStatement(sql.toString());

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ServiceBooking booking = new ServiceBooking();
                booking.setId(rs.getInt("id"));
                booking.setCustomerID(rs.getInt("customerId"));
                booking.setServiceID(rs.getInt("serviceId"));
                booking.setBookingDate(rs.getTimestamp("BookingDate"));
                booking.setStatusID(rs.getInt("statusId"));

                Customer customer = new Customer();
                customer.setId(rs.getInt("customerId"));
                customer.setName(rs.getString("customerName"));
                booking.setCustomer(customer);

                Service service = new Service();
                service.setId(rs.getInt("serviceId"));
                service.setName(rs.getString("serviceName"));
                booking.setService(service);

                Status status = new Status();
                status.setId(rs.getInt("statusId"));
                status.setTitle(rs.getString("statusTitle"));
                booking.setStatus(status);

                filteredBookings.add(booking);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filteredBookings;
    }




}

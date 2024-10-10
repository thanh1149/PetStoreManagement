package com.petstoremanagement.Model;

import java.sql.Timestamp;

public class ServiceBooking {
    private int id;
    private int customerID;
    private int serviceID;
    private Timestamp bookingDate;
    private int statusID;

    private Customer customer;
    private Service service;
    private Status status;

    public ServiceBooking(){}

    public ServiceBooking(int id, int customerID, int serviceID, Timestamp bookingDate, int statusID) {
        this.id = id;
        this.customerID = customerID;
        this.serviceID = serviceID;
        this.bookingDate = bookingDate;
        this.statusID = statusID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

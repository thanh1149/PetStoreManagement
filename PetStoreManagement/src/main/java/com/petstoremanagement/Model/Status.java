package com.petstoremanagement.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.sql.Timestamp;

public class Status {
    private int id;
    private StringProperty title;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Status() {
        this.title = new SimpleStringProperty();
    }

    public Status(int id, String title, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.title = new SimpleStringProperty(title);
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
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

    @Override
    public String toString() {
        return getTitle();
    }
}

package com.petstoremanagement.Model;

import java.sql.Timestamp;

public class Role {
    private String id;
    private String title;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Role(){}
    public Role(String id, String title, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.title = title;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return this.title;
    }
}

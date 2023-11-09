package com.example.duangiaohang.Models;

import java.io.Serializable;

public class Customer implements Serializable {
    private String id;
    private String address;
    private String name;
    private String imageUser;

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", imageUser='" + imageUser + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUser() {
        return imageUser;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
    }

    public Customer(String id, String address, String name, String imageUser) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.imageUser = imageUser;
    }

    public Customer() {
        this.id = null;
        this.address = null;
        this.name = null;
        this.imageUser = null;
    }
}

package com.example.taquy.finalproject.Entities;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String avatar;
    private String email;
    private String name;
    private String phone;
    private String brand;
    private String plate;
    private String password;

    public User() {
        id = -1;
        email = "N/A";
        name = "N/A";
        phone = "N/A";
        brand = "N/A";
        plate = "N/A";
    }

    public String getBikeInfo() {
        return brand + " (" + plate + ")";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

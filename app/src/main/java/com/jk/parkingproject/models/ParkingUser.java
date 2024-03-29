package com.jk.parkingproject.models;

import java.io.Serializable;
import java.lang.ref.SoftReference;
/**
 * Gerin Puig - 101343659
 * Rajdeep Dodiya - 101320088
 */

public class ParkingUser implements Serializable {
    private String id;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String email;
    private String password;
    private String plate_number;
    private boolean isActive;

    public ParkingUser(String first_name, String last_name, String phone_number, String email, String password,
                       String plate_number, boolean isActive) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.email = email;
        this.password = password;
        this.plate_number = plate_number;
        this.isActive = isActive;
    }

    public ParkingUser(){

    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlate_number() {
        return plate_number;
    }

    public void setPlate_number(String plate_number) {
        this.plate_number = plate_number;
    }

    @Override
    public String toString() {
        return "ParkingUser{" +
                "id='" + id + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", plate_number='" + plate_number + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}

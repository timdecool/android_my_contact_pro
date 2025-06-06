package com.ipi.mycontactpro.pojo;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.io.Serializable;

public class Contact implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "lastName")
    private String lastName;

    @ColumnInfo(name = "firstName")
    private String firstName;

    @ColumnInfo(name = "company")
    private String company;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "sector")
    private String sector;

    @ColumnInfo(name = "favorite")
    private int favorite;

    public Contact() {}

    public Contact(String lastName, String firstName, String company, String address, String phone, String email, String sector) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.company = company;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.sector = sector;
        this.favorite = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}

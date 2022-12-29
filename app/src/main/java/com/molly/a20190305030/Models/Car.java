package com.molly.a20190305030.Models;

public class Car {
    private int id;
    private String ownerEmail;
    private String model;
    private String year;
    private byte[] image;
    public Car(){

    }

    public Car(int id, String ownerEmail, String model, String year, byte[] image) {
        this.id = id;
        this.ownerEmail = ownerEmail;
        this.model = model;
        this.year = year;
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}

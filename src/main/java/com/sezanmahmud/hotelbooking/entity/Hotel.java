package com.sezanmahmud.hotelbooking.entity;

import jakarta.persistence.*;

@Entity
@Table(name="hotels")

public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private String rating;
    private double maximumprice;
    private double minimumprice;
    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="locationId")
    private Location location;

    public Hotel() {

    }

    public Hotel(int id, String name, String address, String rating, double maximumprice, double minimumprice, String image, Location location) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.maximumprice = maximumprice;
        this.minimumprice = minimumprice;
        this.image = image;
        this.location = location;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public double getMaximumprice() {
        return maximumprice;
    }

    public void setMaximumprice(double maximumprice) {
        this.maximumprice = maximumprice;
    }

    public double getMinimumprice() {
        return minimumprice;
    }

    public void setMinimumprice(double minimumprice) {
        this.minimumprice = minimumprice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


}

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


}

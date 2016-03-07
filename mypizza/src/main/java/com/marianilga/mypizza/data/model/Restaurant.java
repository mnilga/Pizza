package com.marianilga.mypizza.data.model;

import com.orm.SugarRecord;



public class Restaurant extends SugarRecord {

    private String name;
    private double distance;
    private String venueId;
    private String phone;
    private String address;
    private String url;
    private Double rating;

    public Restaurant(){
    }

    public String getVenueId() {
        return venueId;
    }


    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getName() {
        return name;
    }

    public double getDistance() {
        return distance;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}

package com.example.finalproject_androidstudio.activities;

import com.google.firebase.firestore.PropertyName;

import java.util.List;

public class User {
    @PropertyName("full_name")
    private String fullName;
    @PropertyName("email")
    private String email;
    @PropertyName("phone_number")
    private String phoneNumber;
    @PropertyName("location")
    private String location;
    @PropertyName("calendar")
    private List<String> calendar;
    @PropertyName("active")
    private boolean active;
    @PropertyName("rating")
    private double rating;
    @PropertyName("who_am_i")
    private WhoAmI whoAmI; // Declare whoAmI field

    public enum WhoAmI {
        USER,
        BABYSITTER,
        ADMIN
    }

    public User() {
        // Default constructor required for Firestore
    }

    public User(String fullName, String email, String phoneNumber, String location, List<String> calendar, WhoAmI whoAmI) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.calendar = calendar;
        this.whoAmI = whoAmI; // Initialize whoAmI field
    }

    @PropertyName("full_name")
    public String getFullName() {
        return fullName;
    }

    @PropertyName("full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @PropertyName("email")
    public String getEmail() {
        return email;
    }

    @PropertyName("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @PropertyName("phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @PropertyName("phone_number")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @PropertyName("location")
    public String getLocation() {
        return location;
    }

    @PropertyName("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @PropertyName("calendar")
    public List<String> getCalendar() {
        return calendar;
    }

    @PropertyName("calendar")
    public void setCalendar(List<String> calendar) {
        this.calendar = calendar;
    }

    @PropertyName("active")
    public boolean isActive() {
        return active;
    }

    @PropertyName("active")
    public void setActive(boolean active) {
        this.active = active;
    }

    @PropertyName("rating")
    public double getRating() {
        return rating;
    }

    @PropertyName("rating")
    public void setRating(double rating) {
        this.rating = rating;
    }

    @PropertyName("who_am_i")
    public WhoAmI getWhoAmI() {
        return whoAmI;
    }

    @PropertyName("who_am_i")
    public void setWhoAmI(WhoAmI whoAmI) {
        this.whoAmI = whoAmI;
    }
}

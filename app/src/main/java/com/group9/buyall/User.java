package com.group9.buyall;

public class User {
    private String userId;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;

    // Constructor
    public User(String userId, String username, String email, String phoneNumber, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    // Getters và Setters (nếu cần)
    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}

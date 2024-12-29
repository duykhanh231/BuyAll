package com.group9.buyall;

public class User {
    private String userId;
    private String fullname;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;

    // Constructor
    public User(String userId, String fullname, String username, String email, String phoneNumber, String password) {
        this.userId = userId;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    // Getters (nếu cần)
    public String getUserId() {
        return userId;
    }

    public String getFullname() {
        return fullname;
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

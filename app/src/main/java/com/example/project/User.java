package com.example.project;

public class User {
    private String phoneNumber;
    private String username;
    private String password;

    public User() { } // Default constructor required for calls to DataSnapshot.getValue(User.class)

    public User(String phoneNumber, String username, String password) {
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
    }

    public String getPhoneNumber() { return phoneNumber; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}

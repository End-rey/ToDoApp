package com.example.todoapp.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

//    @SerializedName("id")
//    @Expose
    private Long id;

//    @SerializedName("username")
//    @Expose
    private String username;

//    @SerializedName("firstName")
//    @Expose
    private String firstName;

//    @SerializedName("lastName")
//    @Expose
    private String lastName;

//    @SerializedName("password")
//    @Expose
    private String password;

//    public User(Long id, String username, String firstName, String lastName, String password) {
//        this.id = id;
//        this.username = username;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.password = password;
//    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

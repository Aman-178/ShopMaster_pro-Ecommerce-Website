/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.servlet;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author lenovo
 */


@Entity
public class Authentication implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_Id")
    private Long id;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="Firstname")
    private String Firstname;
    @Column (name="Lastname")
    private String Lastname;
    @Column(name="Mobileno")
    private String Mobileno;
    @Column(name="address")
    private String address;
    
    
      // Default constructor
    public Authentication() {
    }

    // Parameterized constructor
    public Authentication(String username, String password, String Firstname, String Lastname, String Mobileno, String address) {
        this.username = username;
        this.password = password;
        this.Firstname = Firstname;
        this.Lastname = Lastname;
        this.Mobileno = Mobileno;
        this.address = address;
    }

    // Getters and Setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String Firstname) {
        this.Firstname = Firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String Lastname) {
        this.Lastname = Lastname;
    }

    public String getMobileno() {
        return Mobileno;
    }

    public void setMobileno(String Mobileno) {
        this.Mobileno = Mobileno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

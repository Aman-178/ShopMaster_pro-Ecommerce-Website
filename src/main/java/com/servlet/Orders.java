package com.servlet;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Orders")
public class Orders implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order_id")
    private Long id;

    @Column(name = "Product_Name")
    private String productName;

    @Column(name = "Product_Price")
    private String productPrice;

    @Column(name = "username")
    private String username;

    @Column(name = "Status")
    private String status = "Processing"; // Set default value here

    // Constructors
    public Orders() {}

    public Orders(String productName, String productPrice, String username) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.username = username;
        this.status = "Processing"; // Set default value in constructor
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

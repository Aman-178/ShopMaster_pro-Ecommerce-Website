package com.servlet;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Lob;
import javax.persistence.Column;

/**
 * Represents a product entity.
 */
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    
    @Column(name="product_name")
    private String name;
    
    @Column(name="product_price")
    private double price;
    
    @Column(name="product_category")
    private String category;
    
    @Column(name="product_description")
    private String description;
    
    @Lob
    @Column(columnDefinition = "BLOB", name="product_image") 
    private byte[] image; 

    @Column(name="product_company")
    private String company;

    public Product() {
    }

    public Product(String name, double price, String category, String description, byte[] image, String company) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.image = image;
        this.company = company;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}

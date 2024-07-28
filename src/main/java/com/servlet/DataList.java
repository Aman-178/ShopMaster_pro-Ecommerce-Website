/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.servlet;

/**
 *
 * @author ACCESS
 */
public class DataList {   
  private String ProductName;
  private String ProductPrice;
  private String ProductCategory;
  private String ProductDescription;
  private String ProductImage;
  
  public void SetProductName(String ProductName){
          this.ProductName=ProductName;
   }
   public String getProductName(){
          return ProductName;
   }
   
   public void SetProductPrice(String ProductPrice){
          this.ProductPrice=ProductPrice;
   }
    public String getProductPrice(){
          return ProductPrice;
   }
   
   public void SetProductCategory(String ProductCategory){
          this.ProductCategory=ProductCategory;
   }
   public String getProductCategory(){
          return ProductCategory;
   }
   public void SetProductDescription(String ProductDescription){
          this.ProductDescription=ProductDescription;
   }

    public String getProductDescription(){
          return ProductDescription;
   }
  public void SetProductImage(String ProductImage){
          this.ProductImage=ProductImage;
   }
  public String getProductImage(){
          return ProductImage;
   }
    
}

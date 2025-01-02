package com.group9.buyall.ProductList;

import java.util.ArrayList;
import java.util.List;

public class Product_List {
    private int productId;
    private String name;
    private double price;
    private double rating;
    private String shippingMethod;
    private String imageUrl;
    private String productType;
    private String productGroup;
    private int stock;

    private static final List<Product_List> productListnow = new ArrayList<>();

    public Product_List(int productId,String productType,String productGroup, String name, double price, double rating, String shippingMethod, String imageUrl, int stock) {
        this.productId = productId;
        this.productType = productType;
        this.productGroup = productGroup;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.shippingMethod = shippingMethod;
        this.imageUrl = imageUrl;
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}

package com.example.productdetail;

public class Product_Detail {
    private String productId;
    private String productName;
    private double price;
    private String shippingMethod;
    private String location;
    private float averageRating;
    private int imageUrl;
    private String productDescription; // Thuộc tính mô tả sản phẩm

    public Product_Detail(String productId, String productName, double price,
                          String shippingMethod, String location, float averageRating, int imageUrl, String productDescription) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.shippingMethod = shippingMethod;
        this.location = location;
        this.averageRating = averageRating;
        this.imageUrl = imageUrl; // Khởi tạo thuộc tính URL
        this.productDescription = productDescription; // Khởi tạo thuộc tính mô tả sản phẩm
    }

    // Getters và Setters cho từng thuộc tính
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}

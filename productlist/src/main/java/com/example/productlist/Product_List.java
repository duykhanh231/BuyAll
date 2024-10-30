package com.example.productlist;

public class Product_List {
    private String productId;
    private String name;
    private double price;
    private double rating;
    private String shippingMethod;
    private int imageUrl;
    private String location;


    public Product_List(String productId, String name, double price, double rating, String shippingMethod, int imageUrl, String location) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.shippingMethod = shippingMethod;
        this.imageUrl = imageUrl;
        this.location = location; // Gán giá trị cho location
    }

    public String getProductId() {
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

    public int getImageUrl() {
        return imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setProductId(String productId) {
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

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

package com.group9.buyall.ProductList;

public class Ram {
    private int productId;
    private String capacity;
    private String ramType;
    private String brand;
    private String support;
    private double price;
    private double rate;

    public Ram(int productId, String capacity, String ramType, String brand, String support, double price, double rate) {
        this.productId = productId;
        this.capacity = capacity;
        this.ramType = ramType;
        this.brand = brand;
        this.support = support;
        this.price = price;
        this.rate = rate;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getRamType() {
        return ramType;
    }

    public void setRamType(String ramType) {
        this.ramType = ramType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }


}

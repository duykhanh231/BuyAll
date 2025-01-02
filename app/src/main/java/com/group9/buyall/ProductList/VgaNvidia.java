package com.group9.buyall.ProductList;

public class VgaNvidia {
    private int productID;
    private String capacity;
    private String memoryType;
    private String productLine;
    private double price;
    private double rate;

    public VgaNvidia(int productID, String capacity, String memoryType, String productLine, double price, double rate) {
        this.productID = productID;
        this.capacity = capacity;
        this.memoryType = memoryType;
        this.productLine = productLine;
        this.price = price;
        this.rate = rate;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
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


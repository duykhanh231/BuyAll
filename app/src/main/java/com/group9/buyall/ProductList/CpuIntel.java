package com.group9.buyall.ProductList;

public class CpuIntel {
    private int productID;
    private String productLine;
    private String generation;
    private String socket;
    private double price;
    private double rate;

    public CpuIntel(int productID, String productLine, String generation, String socket, double price, double rate) {
        this.productID = productID;
        this.productLine = productLine;
        this.generation = generation;
        this.socket = socket;
        this.price = price;
        this.rate = rate;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
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


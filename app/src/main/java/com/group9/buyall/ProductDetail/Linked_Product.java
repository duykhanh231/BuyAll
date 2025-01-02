package com.group9.buyall.ProductDetail;

public class Linked_Product {
    private String linkedID;
    private int productID;
    private String productFEWINFO;
    private double price;
    public Linked_Product(String linkedID, int productID, String productFEWINFO, double price) {

        this.linkedID = linkedID;
        this.productID = productID;
        this.productFEWINFO = productFEWINFO;
        this.price = price;
    }

    public String getLinkedID() {
        return linkedID;
    }

    public void setLinkedID(String linkedID) {
        this.linkedID = linkedID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductFEWINFO() {
        return productFEWINFO;
    }

    public void setProductFEWINFO(String productFEWINFO) {
        this.productFEWINFO = productFEWINFO;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

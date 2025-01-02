package com.group9.buyall.ProductList;

public class Disk {
    public int productId;
    public String capacity;
    public String disktype;
    public double price;
    public double rate;

    public Disk(int productId, String capacity, String disktype, double price, double rate) {
        this.productId = productId;
        this.capacity = capacity;
        this.disktype = disktype;
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

    public String getDisktype() {
        return disktype;
    }

    public void setDisktype(String disktype) {
        this.disktype = disktype;
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


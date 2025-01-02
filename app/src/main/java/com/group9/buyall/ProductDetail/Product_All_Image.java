package com.group9.buyall.ProductDetail;

public class Product_All_Image {
    private int imageID;
    private int productID;
    private String imageUrl;
    private boolean isMain;
    public Product_All_Image(int imageID, int productID, String imageUrl, boolean isMain) {
        this.imageID = imageID;
        this.productID = productID;
        this.imageUrl = imageUrl;
        this.isMain = isMain;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }
}

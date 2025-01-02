package com.group9.buyall.ProductDetail;

public class Product_Description {
    private int productId;
    private String productDescription;

    public Product_Description(int productId, String productDescription){
        this.productId = productId;
        this.productDescription = productDescription;

    }
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}

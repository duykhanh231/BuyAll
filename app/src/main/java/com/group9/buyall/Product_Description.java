package com.group9.buyall;

public class Product_Description {
    private String productId;
    private String productDescription;

    public Product_Description(String productId, String productDescription){
        this.productId = productId;
        this.productDescription = productDescription;

    }
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}

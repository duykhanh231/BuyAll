package com.group9.buyall;

public class CartItemModel {

    public static final int CART_ITEM = 0;
    public static final int TOTAL_AMOUNT = 1;

    private int type;

    private int id;

    ///cart item
    private int productImage;
    private String productTitle;
    private int freeCoupons;
    private String productPrice;
    private String cuttedPrice;
    private int productQuantity;
    private int offersApplied;
    private int couponsApplied;

    public CartItemModel(int type, int productImage, String productTitle, int freeCoupons, String productPrice, String cuttedPrice, int productQuantity, int offersApplied, int couponsApplied) {
        this.type = type;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.freeCoupons = freeCoupons;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.productQuantity = productQuantity;
        this.offersApplied = offersApplied;
        this.couponsApplied = couponsApplied;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public int getFreeCoupons() {
        return freeCoupons;
    }

    public void setFreeCoupons(int freeCoupons) {
        this.freeCoupons = freeCoupons;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getCouponsApplied() {
        return couponsApplied;
    }

    public void setCouponsApplied(int couponsApplied) {
        this.couponsApplied = couponsApplied;
    }

    public int getOffersApplied() {
        return offersApplied;
    }

    public void setOffersApplied(int offersApplied) {
        this.offersApplied = offersApplied;
    }


    private String totalItems;
    private String totalItemPrice;
    private String deliveryPrice;
    private String savedAmount;
    private String totalAmount;

    public CartItemModel(int type, String totalItems, String totalItemPrice, String deliveryPrice,String totalAmount, String savedAmount) {
        this.totalAmount = totalAmount;
        this.type = type;
        this.totalItems = totalItems;
        this.totalItemPrice = totalItemPrice;
        this.deliveryPrice = deliveryPrice;
        this.savedAmount = savedAmount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(String totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(String savedAmount) {
        this.savedAmount = savedAmount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


}

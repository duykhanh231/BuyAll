package com.group9.buyall.ProductDetail;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailComment {
    private int productId;
    private int commentId;
    private int userId;
    private String userName;
    private String userImageUrl;
    private String userComment;
    private String productImage1;
    private String productImage2;
    private float rating;


    // Constructor
    public ProductDetailComment(int productId, int commentId, int userId, String userName,
                                String userImageUrl, String userComment, String productImage1,
                                String productImage2, float rating) {
        this.productId = productId;
        this.commentId = commentId;
        this.userId = userId;
        this.userName = userName;
        this.userImageUrl = userImageUrl;
        this.userComment = userComment;
        this.productImage1 = productImage1;
        this.productImage2 = productImage2;
        this.rating = rating;
    }

    // Getters và Setters cho từng thuộc tính
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getProductImage1() {
        return productImage1;
    }

    public void setProductImage1(String productImage1) {
        this.productImage1 = productImage1;
    }

    public String getProductImage2() {
        return productImage2;
    }

    public void setProductImage2(String productImage2) {
        this.productImage2 = productImage2;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}

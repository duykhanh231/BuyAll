package com.group9.buyall;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailComment {
    private String productId;
    private String commentId;
    private String userId;
    private String userName;
    private int userImageUrl;
    private String userComment;
    private int productImage1;
    private int productImage2;
    private float rating;

    public static List<ProductDetailComment> commentsList = new ArrayList<>();

    // Constructor
    public ProductDetailComment(String productId, String commentId, String userId, String userName,
                                int userImageUrl, String userComment, int productImage1,
                                int productImage2, float rating) {
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
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(int userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public int getProductImage1() {
        return productImage1;
    }

    public void setProductImage1(int productImage1) {
        this.productImage1 = productImage1;
    }

    public int getProductImage2() {
        return productImage2;
    }

    public void setProductImage2(int productImage2) {
        this.productImage2 = productImage2;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}

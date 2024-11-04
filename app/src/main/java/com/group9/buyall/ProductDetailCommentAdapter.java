package com.group9.buyall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductDetailCommentAdapter extends RecyclerView.Adapter<ProductDetailCommentAdapter.ProductDetailCommentViewHolder> {
    private List<ProductDetailComment> ProductDetailCommentList;

    public ProductDetailCommentAdapter(List<ProductDetailComment> productDetailCommentList) {
        ProductDetailCommentList = productDetailCommentList;
    }

    @NonNull
    @Override
    public ProductDetailCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment,parent,false);
        return new ProductDetailCommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDetailCommentViewHolder holder, int position) {
         ProductDetailComment productDetailComment = ProductDetailCommentList.get(position);
         if (productDetailComment == null){
             return;
         }

         holder.tvUserName.setText(productDetailComment.getUserName());
         holder.tvUserComment.setText(productDetailComment.getUserComment());
         holder.ivUserImage.setImageResource(productDetailComment.getUserImageUrl());
         holder.ivCommentImage1.setImageResource(productDetailComment.getProductImage1());
         holder.ivCommentImage2.setImageResource(productDetailComment.getProductImage2());
         holder.rbUser.setRating(productDetailComment.getRating());
    }

    @Override
    public int getItemCount() {
        if (ProductDetailCommentList != null){
            return ProductDetailCommentList.size();
        }
        return 0;
    }

    class ProductDetailCommentViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivUserImage,ivCommentImage1,ivCommentImage2;
        private TextView tvUserName, tvUserComment;
        private RatingBar rbUser;
        public ProductDetailCommentViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserImage = itemView.findViewById(R.id.ivUSERIMAGE);
            ivCommentImage1 = itemView.findViewById(R.id.ivCOMMENTIMAGE1);
            ivCommentImage2 = itemView.findViewById(R.id.ivCOMMENTIMAGE2);

            tvUserName = itemView.findViewById(R.id.tvUSERNAME);
            tvUserComment = itemView.findViewById(R.id.tvUSERCOMMENT);

            rbUser = itemView.findViewById(R.id.rbUSER);
        }
    }
}

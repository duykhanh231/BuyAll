package com.group9.buyall.ProductDetail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.group9.buyall.R;

import java.util.List;

public class ProductDetailCommentAdapter extends RecyclerView.Adapter<ProductDetailCommentAdapter.ProductDetailCommentViewHolder> {
    private List<ProductDetailComment> ProductDetailCommentList;
    private Context context;

    public ProductDetailCommentAdapter(List<ProductDetailComment> productDetailCommentList,Context context) {
        ProductDetailCommentList = productDetailCommentList;
        this.context =  context;

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
        loadImage(context, productDetailComment.getUserImageUrl(), holder.ivUserImage, holder.itemView);
        loadImage(context, productDetailComment.getProductImage1(), holder.ivCommentImage1, holder.itemView);
        loadImage(context, productDetailComment.getProductImage2(), holder.ivCommentImage2, holder.itemView);
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

    private void loadImage(Context context, String imageUrl, ImageView imageView, View itemView) {
        Glide.with(context)
                .load(imageUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // Handle the error case
                        return false; // Allow Glide to handle the error as well
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        itemView.setVisibility(View.VISIBLE); // Show the item when image loading is successful
                        return false; // Allow Glide to display the image
                    }
                })
                .into(imageView);
    }
}

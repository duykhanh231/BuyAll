package com.group9.buyall.ProductDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.group9.buyall.R;

import java.util.List;

public class ProductDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_PRODUCT_DETAIL = 0;  // Thông tin sản phẩm
    private static final int VIEW_TYPE_COMMENT = 1;         // Bình luận
    private static final int VIEW_TYPE_LISTENER_TEXT = 2;   // TextView với listener
    private static final int VIEW_TYPE_DESCRIPTION = 3;     // Mô tả sản phẩm

    private List<Product_Detail> productDetails;
    private List<ProductDetailComment> commentsList;
    private List<Product_Description> productDescriptions;
    private Context context;

    public ProductDetailAdapter(List<Product_Detail> productDetails, List<ProductDetailComment> commentsList,
                                List<Product_Description> productDescriptions, Context context) {
        this.productDetails = productDetails;
        this.commentsList = commentsList;
        this.productDescriptions = productDescriptions;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_PRODUCT_DETAIL; // Thông tin sản phẩm
        } else if (position <= commentsList.size()) {
            return VIEW_TYPE_COMMENT; // Bình luận
        } else if (!commentsList.isEmpty() && position == commentsList.size() + 1) {
            return VIEW_TYPE_LISTENER_TEXT; // TextView với listener (chỉ khi có bình luận)
        } else {
            return VIEW_TYPE_DESCRIPTION; // Mô tả sản phẩm
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_PRODUCT_DETAIL) {
            View view = inflater.inflate(R.layout.product_detail, parent, false);
            return new ProductDetailViewHolder(view);
        } else if (viewType == VIEW_TYPE_COMMENT) {
            View view = inflater.inflate(R.layout.comment, parent, false);
            return new CommentViewHolder(view);
        } else if (viewType == VIEW_TYPE_LISTENER_TEXT) {
            View view = inflater.inflate(R.layout.more_comment, parent, false);
            return new ListenerTextViewHolder(view);
        } else { // VIEW_TYPE_DESCRIPTION
            View view = inflater.inflate(R.layout.product_description, parent, false);
            return new ProductDescriptionViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductDetailViewHolder) {
            bindProductDetail((ProductDetailViewHolder) holder);
        } else if (holder instanceof CommentViewHolder) {
            bindComment((CommentViewHolder) holder, position - 1); // -1 để điều chỉnh cho bình luận
        } else if (holder instanceof ListenerTextViewHolder) {
            bindListenerText((ListenerTextViewHolder) holder);
        } else if (holder instanceof ProductDescriptionViewHolder) {
            bindProductDescription((ProductDescriptionViewHolder) holder);
        }
    }

    private void bindProductDetail(ProductDetailViewHolder holder) {
        Product_Detail productDetail = productDetails.get(0);
        holder.tvProductName.setText(productDetail.getProductName());

        double price = productDetail.getPrice();
        String formattedPrice = String.format("đ %,.0f", price);

        holder.tvProductPrice.setText(formattedPrice);
        holder.ivProductImage.setImageResource(productDetail.getImageUrl());
        holder.tvRATE.setText(String.valueOf(productDetail.getAverageRating()));
        holder.tvSHIPINGANDLOCATION.setText(String.format("%s %s | %s %s",
                "\uD83D\uDE97", productDetail.getShippingMethod(),
                "\uD83D\uDEA9", productDetail.getLocation()));
        holder.rbAVERAGE.setRating(productDetail.getAverageRating());
    }

    private void bindComment(CommentViewHolder holder, int position) {
        ProductDetailComment productDetailComment = commentsList.get(position);
        holder.tvUserName.setText(productDetailComment.getUserName());
        holder.tvUserComment.setText(productDetailComment.getUserComment());
        holder.ivUserImage.setImageResource(productDetailComment.getUserImageUrl());
        holder.ivCommentImage1.setImageResource(productDetailComment.getProductImage1());
        holder.ivCommentImage2.setImageResource(productDetailComment.getProductImage2());
        holder.rbUser.setRating(productDetailComment.getRating());
    }

    private void bindListenerText(ListenerTextViewHolder holder) {
        if (commentsList.isEmpty()) {
            holder.tvListenerText.setVisibility(View.GONE); // Ẩn TextView nếu không có bình luận
        } else {
            holder.tvListenerText.setVisibility(View.VISIBLE); // Hiện TextView nếu có bình luận
            holder.tvListenerText.setOnClickListener(v -> {
                if (!productDetails.isEmpty()) {
                    String productId = productDetails.get(0).getProductId();

                    // Replace fragment instead of starting a new activity
                    FullCommentFragment fullCommentFragment = new FullCommentFragment();
                    Bundle args = new Bundle();
                    args.putString("PRODUCT_ID", productId);
                    fullCommentFragment.setArguments(args);
                    FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fullcomment_container, fullCommentFragment); // Replace your fragment container ID
                    transaction.addToBackStack(null); // Add to back stack so that user can go back
                    transaction.commit();
                    ((AppCompatActivity) context).findViewById(R.id.fullcomment_container).setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void bindProductDescription(ProductDescriptionViewHolder holder) {
        Product_Description productDescription = productDescriptions.get(0); // Lấy mô tả sản phẩm đầu tiên trong danh sách
        holder.tvProductDescription.setText(productDescription.getProductDescription());
    }

    @Override
    public int getItemCount() {
        int listenerTextCount = commentsList.isEmpty() ? 0 : 1; // 0 nếu không có bình luận, 1 nếu có
        return 1 + commentsList.size() + listenerTextCount + 1; // 1 cho chi tiết sản phẩm, số bình luận, TextView (nếu có), và mô tả sản phẩm
    }

    // ViewHolder cho thông tin sản phẩm
    static class ProductDetailViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductPrice, tvSHIPINGANDLOCATION, tvRATE;
        ImageView ivProductImage;
        RatingBar rbAVERAGE;

        public ProductDetailViewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPRICE);
            ivProductImage = itemView.findViewById(R.id.ivPRODUCTIMAGE);
            tvSHIPINGANDLOCATION = itemView.findViewById(R.id.tvShippingMethodandLocation);
            tvRATE = itemView.findViewById(R.id.tvRATE);
            rbAVERAGE = itemView.findViewById(R.id.rbAverageComment);
        }
    }

    // ViewHolder cho bình luận
    static class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView ivUserImage, ivCommentImage1, ivCommentImage2;
        TextView tvUserName, tvUserComment;
        RatingBar rbUser;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ivUserImage = itemView.findViewById(R.id.ivUSERIMAGE);
            ivCommentImage1 = itemView.findViewById(R.id.ivCOMMENTIMAGE1);
            ivCommentImage2 = itemView.findViewById(R.id.ivCOMMENTIMAGE2);
            tvUserName = itemView.findViewById(R.id.tvUSERNAME);
            tvUserComment = itemView.findViewById(R.id.tvUSERCOMMENT);
            rbUser = itemView.findViewById(R.id.rbUSER);
        }
    }

    // ViewHolder cho TextView có listener
    static class ListenerTextViewHolder extends RecyclerView.ViewHolder {
        TextView tvListenerText;

        public ListenerTextViewHolder(View itemView) {
            super(itemView);
            tvListenerText = itemView.findViewById(R.id.tvXEMTATCA);
        }
    }

    // ViewHolder cho mô tả sản phẩm
    static class ProductDescriptionViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductDescription;
        public ProductDescriptionViewHolder(View itemView) {
            super(itemView);
            tvProductDescription = itemView.findViewById(R.id.tvProductDESCRIPTION);
        }
    }
}


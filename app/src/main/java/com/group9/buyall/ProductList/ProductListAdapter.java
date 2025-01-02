package com.group9.buyall.ProductList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;

import com.group9.buyall.ProductDetail.ProductDetail;
import com.group9.buyall.R;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    private List<Product_List> productList;
    private Context context;

    public ProductListAdapter(Context context, List<Product_List> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product_List productList = this.productList.get(position);
        if (productList == null) {
            return;
        }

        double price = productList.getPrice();
        String formattedPrice = String.format("đ %,.0f", price);

        double rate = productList.getRating();
        String formattedRate = String.format("★ %.1f", rate);
        SpannableString spannable = new SpannableString(formattedRate);
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FFD700")), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        String formattedSHIPPINGMETHODANDLOCATION = String.format("%s %s", "\uD83D\uDE97", productList.getShippingMethod());

        holder.itemView.setVisibility(View.INVISIBLE);

        Glide.with(context)
                .load(productList.getImageUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // Handle the error case
                        return false; // Allow Glide to handle the error as well
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.itemView.setVisibility(View.VISIBLE); // Show the item when image loading is successful
                        return false; // Allow Glide to display the image
                    }
                })
                .into(holder.ivproduct);

        holder.tvproductNAME.setText(productList.getName());
        holder.tvproductRATE.setText(spannable);
        holder.tvproductPRICE.setText(formattedPrice);
        holder.tvproductSHIPPINGMETHODANDLOCATION.setText(formattedSHIPPINGMETHODANDLOCATION);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetail.class);
            intent.putExtra("PRODUCT_ID", productList.getProductId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();
        }
        return 0;
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivproduct;
        private TextView tvproductNAME, tvproductPRICE, tvproductRATE, tvproductSHIPPINGMETHODANDLOCATION;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ivproduct = itemView.findViewById(R.id.product_image);
            tvproductNAME = itemView.findViewById(R.id.product_name);
            tvproductPRICE = itemView.findViewById(R.id.product_price);
            tvproductRATE = itemView.findViewById(R.id.product_rate);
            tvproductSHIPPINGMETHODANDLOCATION = itemView.findViewById(R.id.product_shippingmethodandlocation);
        }
    }
}

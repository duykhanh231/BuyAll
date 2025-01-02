package com.group9.buyall.ProductDetail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

public class All_Image_Adapter extends RecyclerView.Adapter<All_Image_Adapter.AllImageViewHolder> {
    private List<Product_All_Image> MyProductAllImage;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public All_Image_Adapter(List<Product_All_Image> myProductAllImage, Context context) {
        MyProductAllImage = myProductAllImage;
        this.context = context;
    }

    @NonNull
    @Override
    public AllImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_all_image,parent,false);
        return new AllImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllImageViewHolder holder, int position) {
        Product_All_Image productAllImage = MyProductAllImage.get(position);
        if (productAllImage == null) {return;}

        holder.imageView.setVisibility(View.INVISIBLE);

        Glide.with(context)
                .load(productAllImage.getImageUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.imageView.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(holder.imageView);

        if (holder.getAdapterPosition() == selectedPosition) {
            holder.imageView.setBackgroundResource(R.drawable.border_red_radius); }
        else {
            holder.imageView.setBackgroundResource(R.drawable.border_black); }

        holder.imageView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition == RecyclerView.NO_POSITION) {
                return; }
            selectedPosition = adapterPosition;
            notifyDataSetChanged();
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (MyProductAllImage != null) {
            return MyProductAllImage.size();
        }
        return 0;
    }

    class AllImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public AllImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivALLPRODUCTIMAGE);
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}

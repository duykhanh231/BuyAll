package com.group9.buyall.ProductDetail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.group9.buyall.ProductList.Product_List;
import com.group9.buyall.R;
import java.util.List;

public class ProductDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_PRODUCT_MAIN_IMAGE = 0;
    private static final int VIEW_TYPE_PRODUCT_ALL_IMAGE = 1;
    private static final int VIEW_TYPE_TEXT_CONFIG = 2;
    private static final int VIEW_TYPE_PRODUCT_ALL_LINKED = 3;
    private static final int VIEW_TYPE_PRODUCT_DETAIL = 4;  // Thông tin sản phẩm
    private static final int VIEW_TYPE_COMMENT = 5;         // Bình luận
    private static final int VIEW_TYPE_LISTENER_TEXT = 6;   // TextView với listener
    private static final int VIEW_TYPE_DESCRIPTION = 7;     // Mô tả sản phẩm


    private All_Image_Adapter allImageAdapter;
    private ProductMainImageViewHolder mainImageViewHolder;

    private Product_Linked_Adapter productLinkedAdapter;

    private List<Linked_Product> linkedProducts;
    private List<Product_All_Image> productAllImages;
    private List<Product_List> productLists;
    private List<ProductDetailComment> commentsList;
    private List<Product_Description> productDescriptions;
    private Context context;



    public ProductDetailAdapter(List<Product_All_Image> productAllImages,List<Linked_Product> linkedProducts,List<Product_List> productLists, List<ProductDetailComment> commentsList,
                                List<Product_Description> productDescriptions, Context context) {
        this.productAllImages = productAllImages;
        this.linkedProducts = linkedProducts;
        this.productLists = productLists;
        this.commentsList = commentsList;
        this.productDescriptions = productDescriptions;
        this.context = context;
    }

    @Override public int getItemViewType(int position) {
        if (position == 0) { return VIEW_TYPE_PRODUCT_MAIN_IMAGE; }
        else if (position <= productAllImages.size()) {
             return VIEW_TYPE_PRODUCT_ALL_IMAGE; }
        else if (position == productAllImages.size() + 1) {
            return VIEW_TYPE_TEXT_CONFIG; }
        else if (position <= productAllImages.size() + linkedProducts.size() + 1) {
            return VIEW_TYPE_PRODUCT_ALL_LINKED; }
        else if (position == productAllImages.size() + linkedProducts.size() + 2) {
            return VIEW_TYPE_PRODUCT_DETAIL; }
        else if (position <= productAllImages.size() + linkedProducts.size() + 2 + commentsList.size()) {
            return VIEW_TYPE_COMMENT; }
        else if (!commentsList.isEmpty() && position == commentsList.size() + productAllImages.size() + linkedProducts.size() + 1 + 1 + 1) {
            return VIEW_TYPE_LISTENER_TEXT; }
        else { return VIEW_TYPE_DESCRIPTION; } }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_PRODUCT_MAIN_IMAGE) {
            View view = inflater.inflate(R.layout.product_main_image, parent, false);
            return new ProductMainImageViewHolder(view);
        } else if (viewType == VIEW_TYPE_PRODUCT_ALL_IMAGE) {
            View view = inflater.inflate(R.layout.rcv_ngang, parent, false);
            return new ProductAllImageViewHolder(view);
        } else if (viewType == VIEW_TYPE_TEXT_CONFIG) {
            View view = inflater.inflate(R.layout.configuration, parent, false);
            return new TextConfigViewHolder(view);
        } else if (viewType == VIEW_TYPE_PRODUCT_ALL_LINKED) {
            View view = inflater.inflate(R.layout.rcv_ngang1, parent, false);
            return new ProductAllLinkedViewHolder(view);
        } else if (viewType == VIEW_TYPE_PRODUCT_DETAIL) {
            View view = inflater.inflate(R.layout.product_detail, parent, false);
            return new ProductDetailViewHolder(view);
        } else if (viewType == VIEW_TYPE_COMMENT) {
            View view = inflater.inflate(R.layout.comment, parent, false);
            return new CommentViewHolder(view);
        } else if (viewType == VIEW_TYPE_LISTENER_TEXT) {
            View view = inflater.inflate(R.layout.more_comment, parent, false);
            return new ListenerTextViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.product_description, parent, false);
            return new ProductDescriptionViewHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductMainImageViewHolder) {

            mainImageViewHolder = (ProductMainImageViewHolder) holder;
            bindProductMainImage((ProductMainImageViewHolder) holder,position);
        } else if (holder instanceof ProductAllImageViewHolder) {
            bindProductAllImage((ProductAllImageViewHolder) holder, position - 1); // -1 để điều chỉnh vị trí
        } else if (holder instanceof TextConfigViewHolder) {
            bindTextConfig((TextConfigViewHolder) holder);
        } else if (holder instanceof ProductAllLinkedViewHolder) {
            bindProductAllLinked((ProductAllLinkedViewHolder) holder, position - 3 - productAllImages.size()); // -2 để điều chỉnh vị trí
        } else if (holder instanceof ProductDetailViewHolder) {
            bindProductDetail((ProductDetailViewHolder) holder);
        } else if (holder instanceof CommentViewHolder) {
            bindComment((CommentViewHolder) holder, position - 3 - productAllImages.size() - linkedProducts.size()); // Điều chỉnh vị trí cho bình luận
        } else if (holder instanceof ListenerTextViewHolder) {
            bindListenerText((ListenerTextViewHolder) holder);
        } else if (holder instanceof ProductDescriptionViewHolder) {
            bindProductDescription((ProductDescriptionViewHolder) holder);
        }
    }

    private void bindProductMainImage(ProductMainImageViewHolder holder, int position) {
        Product_All_Image productAllImage = productAllImages.get(position);
        Glide.with(context)
                .load(productAllImage.getImageUrl())
                .into(holder.ivProductMainImage);
    }

    private void bindProductAllImage(ProductAllImageViewHolder holder, int position) {
        if (allImageAdapter == null) {
            allImageAdapter = new All_Image_Adapter(productAllImages, context);
            holder.recyclerView.setAdapter(allImageAdapter);
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        }
        allImageAdapter.setOnItemClickListener(new All_Image_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int clickedPosition) {
                bindProductMainImage(mainImageViewHolder, clickedPosition);
            }
        });
    }

    private void  bindTextConfig(TextConfigViewHolder holder) {

    }

    private void bindProductAllLinked(ProductAllLinkedViewHolder holder, int position) {
        if (productLinkedAdapter == null) {
            productLinkedAdapter = new Product_Linked_Adapter(linkedProducts, context);
            holder.recyclerView.setAdapter(productLinkedAdapter);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false);
            holder.recyclerView.setLayoutManager(gridLayoutManager);
        }
        }

    private void bindProductDetail(ProductDetailViewHolder holder) {
        Product_List productList = productLists.get(0);
        holder.tvProductName.setText(productList.getName());

        double price = productList.getPrice();
        String formattedPrice = String.format("đ %,.0f", price);

        holder.tvProductPrice.setText(formattedPrice);
        holder.tvRATE.setText(String.valueOf(productList.getRating()));
        holder.tvSHIPINGANDLOCATION.setText(String.format("%s %s",
                "\uD83D\uDE97", productList.getShippingMethod()));
        holder.rbAVERAGE.setRating((float) productList.getRating());
    }

    private void bindComment(CommentViewHolder holder, int position) {
        if (position >= 0 && position <  commentsList.size() ) {
            ProductDetailComment productDetailComment = commentsList.get(position);
            holder.tvUserName.setText(productDetailComment.getUserName());
            holder.tvUserComment.setText(productDetailComment.getUserComment());

            loadImage(context, productDetailComment.getUserImageUrl(), holder.ivUserImage, holder.itemView);
            loadImage(context, productDetailComment.getProductImage1(), holder.ivCommentImage1, holder.itemView);
            loadImage(context, productDetailComment.getProductImage2(), holder.ivCommentImage2, holder.itemView);

            holder.rbUser.setRating(productDetailComment.getRating());
        }
    }

    private void bindListenerText(ListenerTextViewHolder holder) {
        if (commentsList.isEmpty()) {
            holder.tvListenerText.setVisibility(View.GONE); // Ẩn TextView nếu không có bình luận
        } else {
            holder.tvListenerText.setVisibility(View.VISIBLE); // Hiện TextView nếu có bình luận
            holder.tvListenerText.setOnClickListener(v -> {
                if (!productLists.isEmpty()) {
                    int productId = productLists.get(0).getProductId();
                    FullCommentFragment fullCommentFragment = new FullCommentFragment();
                    Bundle args = new Bundle();
                    args.putInt("PRODUCT_ID", productId);
                    fullCommentFragment.setArguments(args);
                    FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fullcomment_container, fullCommentFragment);
                    transaction.addToBackStack(null);
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

    @Override public int getItemCount() {
        return 1 + productAllImages.size() + 1 + linkedProducts.size() + 1 + commentsList.size() +
                (commentsList.isEmpty() ? 0 : 1) + 1; }

    // ViewHolder cho hình ảnh chính sản phẩm
    static class ProductMainImageViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductMainImage;
        public ProductMainImageViewHolder(View itemView) {
            super(itemView);
            ivProductMainImage = itemView.findViewById(R.id.ivPRODUCTMAINTIMAGE);
        }
    }

    static class ProductAllImageViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        public ProductAllImageViewHolder (View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.rcvNGANG);
        }
    }

    static class TextConfigViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public TextConfigViewHolder (View itemview) {
            super(itemview);
            textView = itemview.findViewById(R.id.tvCONFIG);
        }
    }

    static class ProductAllLinkedViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        public ProductAllLinkedViewHolder (View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.rcvNGANG1);
        }
    }

    // ViewHolder cho thông tin sản phẩm
    static class ProductDetailViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductPrice, tvSHIPINGANDLOCATION, tvRATE;
        RatingBar rbAVERAGE;

        public ProductDetailViewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPRICE);
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


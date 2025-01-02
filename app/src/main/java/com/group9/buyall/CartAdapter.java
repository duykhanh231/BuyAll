package com.group9.buyall;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group9.buyall.Database.CartItemEntity;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CartItemModel> cartItemModelList;

    public CartAdapter(List<CartItemModel> cartItemModelList) {
        this.cartItemModelList = cartItemModelList;
    }

    @Override
    public int getItemViewType(int position) {
        return cartItemModelList.get(position).getType();
    }

    public interface CartItemClickListener {
        void onQuantityChange(CartItemEntity item, int newQuantity);
        void onRemoveItem(CartItemEntity item);
        void onCouponRedeem(CartItemEntity item);
    }

    private CartItemClickListener listener;

    public CartAdapter(List<CartItemModel> cartItemModelList, CartItemClickListener listener) {
        this.cartItemModelList = cartItemModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case CartItemModel.CART_ITEM:
                View cartItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_layout, viewGroup, false);
                return new CartItemViewholder(cartItemView);
            case CartItemModel.TOTAL_AMOUNT:
                View cartTotalView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_total_amount_layout, viewGroup, false);
                return new CartTotalAmountViewholder(cartTotalView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CartItemModel item = cartItemModelList.get(position);
        if (holder instanceof CartItemViewholder) {
            ((CartItemViewholder) holder).setItemDetails(
                    item.getProductImage(),
                    item.getProductTitle(),
                    item.getFreeCoupons(),
                    item.getProductPrice(),
                    item.getCuttedPrice(),
                    item.getOffersApplied()
            );
        } else if (holder instanceof CartTotalAmountViewholder) {
            ((CartTotalAmountViewholder) holder).setTotalAmount(
                    item.getTotalItems(),
                    item.getTotalItemPrice(),
                    item.getDeliveryPrice(),
                    item.getTotalAmount(),
                    item.getSavedAmount()
            );
        }
    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    class CartItemViewholder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private ImageView freeCouponIcon;
        private TextView productTitle;
        private TextView freeCoupons;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView offersApplied;
        private TextView couponsApplied;
        private TextView productQuantity;

        public CartItemViewholder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            freeCouponIcon = itemView.findViewById(R.id.free_coupon_icon);
            freeCoupons = itemView.findViewById(R.id.tv_free_coupon);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            offersApplied = itemView.findViewById(R.id.offers_applied);
            couponsApplied = itemView.findViewById(R.id.coupons_applied);
            productQuantity = itemView.findViewById(R.id.product_quantity);

            productQuantity.setOnClickListener(v -> showQuantityDialog());

            // Setup remove button
            itemView.findViewById(R.id.remove_item_btn).setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    CartItemModel item = cartItemModelList.get(position);
                    listener.onRemoveItem(convertToEntity(item));
                }
            });

            itemView.findViewById(R.id.coupon_redemption_btn).setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    CartItemModel item = cartItemModelList.get(position);
                    listener.onCouponRedeem(convertToEntity(item));
                }
            });
        }

        private void showQuantityDialog() {
            Context context = itemView.getContext();
            String[] quantities = new String[]{"1", "2", "3", "4", "5"};

            new AlertDialog.Builder(context)
                    .setTitle("Select Quantity")
                    .setItems(quantities, (dialog, which) -> {
                        int newQuantity = Integer.parseInt(quantities[which]);
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            CartItemModel item = cartItemModelList.get(position);
                            listener.onQuantityChange(convertToEntity(item), newQuantity);
                        }
                    })
                    .show();
        }

        private CartItemEntity convertToEntity(CartItemModel model) {
            CartItemEntity entity = new CartItemEntity();
            entity.setProductImage(model.getProductImage());
            entity.setProductTitle(model.getProductTitle());
            entity.setProductPrice(model.getProductPrice());
            entity.setCuttedPrice(model.getCuttedPrice());
            entity.setProductQuantity(model.getProductQuantity());
            entity.setFreeCoupons(model.getFreeCoupons());
            entity.setOffersApplied(model.getOffersApplied());
            entity.setCouponsApplied(model.getCouponsApplied());
            return entity;
        }

        private void setItemDetails(int resource, String title, int freeCouponsNo, String productPriceText, String cuttedPriceText, int offersAppliedNo) {
            productImage.setImageResource(resource);
            productTitle.setText(title);
            if (freeCouponsNo > 0) {
                freeCouponIcon.setVisibility(View.VISIBLE);
                freeCoupons.setVisibility(View.VISIBLE);
                freeCoupons.setText("free " + freeCouponsNo + (freeCouponsNo == 1 ? " Coupon" : " Coupons"));
            } else {
                freeCouponIcon.setVisibility(View.INVISIBLE);
                freeCoupons.setVisibility(View.INVISIBLE);
            }
            productPrice.setText(productPriceText);
            cuttedPrice.setText(cuttedPriceText);
            if (offersAppliedNo > 0) {
                offersApplied.setVisibility(View.VISIBLE);
                offersApplied.setText(offersAppliedNo + " Offers applied");
            } else {
                offersApplied.setVisibility(View.INVISIBLE);
            }
        }
    }

    class CartTotalAmountViewholder extends RecyclerView.ViewHolder {

        private TextView totalItems;
        private TextView totalItemPrice;
        private TextView deliveryPrice;
        private TextView totalAmount;
        private TextView savedAmount;

        public CartTotalAmountViewholder(@NonNull View itemView) {
            super(itemView);
            totalItems = itemView.findViewById(R.id.total_items);
            totalItemPrice = itemView.findViewById(R.id.total_items_price);
            deliveryPrice = itemView.findViewById(R.id.delivery_price);
            totalAmount = itemView.findViewById(R.id.total_price);
            savedAmount = itemView.findViewById(R.id.saved_amount);
        }

        private void setTotalAmount(String totalItemText, String totalItemPriceText, String deliveryPriceText, String totalAmountText, String savedAmountText) {
            totalItems.setText(totalItemText);
            totalItemPrice.setText(totalItemPriceText);
            deliveryPrice.setText(deliveryPriceText);
            totalAmount.setText(totalAmountText);
            savedAmount.setText(savedAmountText);
        }
    }
}

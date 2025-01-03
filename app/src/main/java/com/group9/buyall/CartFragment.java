package com.group9.buyall;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.group9.buyall.Database.CartItemEntity;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private static final String TAG = "CartFragment";
    private RecyclerView cartItemRecyclerView;
    private CartAdapter cartAdapter;
    private List<CartItemModel> cartItemModelList;
    private CartViewModel cartViewModel;
    private TextView totalAmountView;

    public CartFragment() {
        // Required empty public constructor
    }

    private void addItemToCart() {
        CartItemEntity newItem = new CartItemEntity();
        newItem.setProductImage(R.drawable.applewatch);
        newItem.setProductTitle("New Product");
        newItem.setFreeCoupons(1);
        newItem.setProductPrice("100.00");
        newItem.setCuttedPrice("150.00");
        newItem.setProductQuantity(1);
        newItem.setOffersApplied(2);
        newItem.setCouponsApplied(1);

        cartViewModel.insert(newItem);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cartItemRecyclerView = view.findViewById(R.id.cart_items_recyclerview);
        cartItemRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        totalAmountView = view.findViewById(R.id.total_cart_amount);

        cartItemModelList = new ArrayList<>();

        cartAdapter = new CartAdapter(cartItemModelList, new CartAdapter.CartItemClickListener() {
            @Override
            public void onQuantityChange(CartItemEntity item, int newQuantity) {
                Log.d(TAG, "Updating quantity for item ID: " + item.getId());
                item.setProductQuantity(newQuantity);
                cartViewModel.update(item);
            }

            @Override
            public void onRemoveItem(CartItemEntity item) {
                Log.d(TAG, "Removing item with ID: " + item.getId());
                cartViewModel.delete(item);
                Toast.makeText(getContext(), "Item removed from cart", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCouponRedeem(CartItemEntity item) {
                showCouponRedemptionDialog(item);
            }
        });

        cartItemRecyclerView.setAdapter(cartAdapter);

        // Setup ViewModel and observe cart items
        cartViewModel.getAllCartItems().observe(getViewLifecycleOwner(), this::updateCartItems);

        Button checkoutButton = view.findViewById(R.id.cart_checkout_btn);
        checkoutButton.setOnClickListener(v -> checkout());

        return view;
    }

    private void updateCartItems(List<CartItemEntity> cartItems) {
        Log.d(TAG, "Updating cart with " + cartItems.size() + " items");
        cartItemModelList.clear();
        double totalAmount = 0;

        for (CartItemEntity item : cartItems) {
            CartItemModel cartItemModel = new CartItemModel(
                    CartItemModel.CART_ITEM,
                    item.getProductImage(),
                    item.getProductTitle(),
                    item.getFreeCoupons(),
                    item.getProductPrice(),
                    item.getCuttedPrice(),
                    item.getProductQuantity(),
                    item.getOffersApplied(),
                    item.getCouponsApplied()
            );
            cartItemModel.setId(item.getId());
            cartItemModelList.add(cartItemModel);

            try {
                String priceStr = item.getProductPrice().replaceAll("[^\\d.]", "");
                double price = Double.parseDouble(priceStr);
                totalAmount += price * item.getProductQuantity();
            } catch (NumberFormatException e) {
                Log.e(TAG, "Error parsing price: " + item.getProductPrice(), e);
            }
        }

        if (!cartItems.isEmpty()) {
            cartItemModelList.add(new CartItemModel(
                    CartItemModel.TOTAL_AMOUNT,
                    cartItems.size() + " items",
                    String.format("%,.0f VND", totalAmount),
                    "Free",
                    String.format("%,.0f VND", totalAmount),
                    "0 VND"
            ));
        }

        cartAdapter.notifyDataSetChanged();

        if (totalAmountView != null) {
            totalAmountView.setText(String.format("%,.0f VND", totalAmount));
        }
    }

    private void checkout() {
        if (cartItemModelList.isEmpty()) {
            Toast.makeText(getContext(), "Your cart is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        final double[] totalAmount = {0.0};
        for (CartItemModel item : cartItemModelList) {
            if (item.getType() == CartItemModel.CART_ITEM) {
                try {
                    String priceStr = item.getProductPrice().replaceAll("[^\\d.]", "");
                    double price = Double.parseDouble(priceStr);
                    totalAmount[0] += price * item.getProductQuantity();
                } catch (NumberFormatException e) {
                    Log.e(TAG, "Error parsing price during checkout", e);
                }
            }
        }

        new AlertDialog.Builder(requireContext())
                .setTitle("Confirm Purchase")
                .setMessage("Total amount: " + formatPrice(totalAmount[0]))
                .setPositiveButton("Proceed to Payment", (dialog, which) -> {
                    processPayment(totalAmount[0]);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void processPayment(double amount) {
        // Show payment processing message
        Toast.makeText(getContext(), "Processing payment: " + formatPrice(amount), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), OrderHistoryActivity.class);
        startActivity(intent);
    }

    private void showCouponRedemptionDialog(CartItemEntity item) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Available Coupons")
                .setItems(new String[]{"10% OFF", "20% OFF", "Free Shipping"}, (dialog, which) -> {
                    applyCoupon(item, which);
                })
                .show();
    }

    private void applyCoupon(CartItemEntity item, int couponIndex) {
        try {
            String priceStr = item.getProductPrice().replaceAll("[^\\d.]", "");
            double price = Double.parseDouble(priceStr);
            double discount = 0;

            switch (couponIndex) {
                case 0: // 10% OFF
                    discount = price * 0.1;
                    break;
                case 1: // 20% OFF
                    discount = price * 0.2;
                    break;
                case 2: // Free Shipping
                    // Apply free shipping logic
                    break;
            }

            double newPrice = price - discount;
            item.setProductPrice(String.format("%,.0f VND", newPrice));
            item.setCouponsApplied(item.getCouponsApplied() + 1);
            cartViewModel.update(item);

            Toast.makeText(getContext(), "Coupon applied successfully", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Log.e(TAG, "Error applying coupon", e);
            Toast.makeText(getContext(), "Error applying coupon", Toast.LENGTH_SHORT).show();
        }
    }

    private String formatPrice(double price) {
        return String.format("%,.0f VND", price);
    }
}
package com.group9.buyall;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.group9.buyall.Database.CartItemEntity;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private RecyclerView cartItemRecyclerView;
    private CartAdapter cartAdapter;
    private List<CartItemModel> cartItemModelList;
    private CartViewModel cartViewModel;

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

    private void removeItem(CartItemEntity item) {
        cartViewModel.delete(item);
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

        cartItemModelList = new ArrayList<>();
        cartAdapter = new CartAdapter(cartItemModelList);
        cartItemRecyclerView.setAdapter(cartAdapter);

        cartViewModel.getAllCartItems().observe(getViewLifecycleOwner(), cartItems -> {
            cartItemModelList.clear();
            for (CartItemEntity item : cartItems) {
                cartItemModelList.add(new CartItemModel(
                        CartItemModel.CART_ITEM,
                        item.getProductImage(),
                        item.getProductTitle(),
                        item.getFreeCoupons(),
                        item.getProductPrice(),
                        item.getCuttedPrice(),
                        item.getProductQuantity(),
                        item.getOffersApplied(),
                        item.getCouponsApplied()
                ));
            }
            // Add total amount item if needed
            // cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT, ...));
            cartAdapter.notifyDataSetChanged();
        });

        Button checkoutButton = view.findViewById(R.id.cart_checkout_btn);
        checkoutButton.setOnClickListener(v -> checkout());

        return view;
    }

    // CartFragment.java - Implement checkout
    private void checkout() {
        if (cartItemModelList.isEmpty()) {
            Toast.makeText(getContext(), "Your cart is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Calculate total amount
        double totalAmount = 0;
        for (CartItemModel item : cartItemModelList) {
            if (item.getType() == CartItemModel.CART_ITEM) {
                double price = Double.parseDouble(item.getProductPrice().replace(" VND", "").replace(",", ""));
                totalAmount += price * item.getProductQuantity();
            }
        }

        // Show confirmation dialog
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirm Purchase")
                .setMessage("Total amount: " + formatPrice(totalAmount))
                .setPositiveButton("Proceed to Payment", (dialog, which) -> {
                    // Start payment activity or fragment
                    // For example:
                    // startActivity(new Intent(getContext(), PaymentActivity.class));
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // CartFragment.java - Add these methods
    private void setupCartItemFunctionality() {
        cartAdapter = new CartAdapter(cartItemModelList, new CartAdapter.CartItemClickListener() {
            @Override
            public void onQuantityChange(CartItemEntity item, int newQuantity) {
                item.setProductQuantity(newQuantity);
                cartViewModel.update(item);
                updateTotalAmount();
            }

            @Override
            public void onRemoveItem(CartItemEntity item) {
                cartViewModel.delete(item);
                updateTotalAmount();
            }

            @Override
            public void onCouponRedeem(CartItemEntity item) {
                // Implement coupon redemption logic
                // For example, show a dialog with available coupons
                showCouponRedemptionDialog(item);
            }
        });
    }

    // CartFragment.java - Add helper methods
    private void showCouponRedemptionDialog(CartItemEntity item) {
        // Example implementation
        new AlertDialog.Builder(requireContext())
                .setTitle("Available Coupons")
                .setItems(new String[]{"10% OFF", "20% OFF", "Free Shipping"}, (dialog, which) -> {
                    // Apply selected coupon
                    applyCoupon(item, which);
                })
                .show();
    }

    private void applyCoupon(CartItemEntity item, int couponIndex) {
        // Example coupon application logic
        double price = Double.parseDouble(item.getProductPrice().replace(" VND", "").replace(",", ""));
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
        item.setProductPrice(formatPrice(newPrice));
        item.setCouponsApplied(item.getCouponsApplied() + 1);
        cartViewModel.update(item);
    }

    private void updateTotalAmount() {
        double total = 0;
        int itemCount = 0;
        double savedAmount = 0;

        for (CartItemModel item : cartItemModelList) {
            if (item.getType() == CartItemModel.CART_ITEM) {
                double price = Double.parseDouble(item.getProductPrice().replace(" VND", "").replace(",", ""));
                double originalPrice = Double.parseDouble(item.getCuttedPrice().replace(" VND", "").replace(",", ""));
                total += price * item.getProductQuantity();
                savedAmount += (originalPrice - price) * item.getProductQuantity();
                itemCount += item.getProductQuantity();
            }
        }

        // Add total amount item at the end of the list
        CartItemModel totalItem = new CartItemModel(
                CartItemModel.TOTAL_AMOUNT,
                itemCount + " items",
                formatPrice(total),
                "Free",
                formatPrice(total),
                formatPrice(savedAmount)
        );

        // Update the list
        if (!cartItemModelList.isEmpty() &&
                cartItemModelList.get(cartItemModelList.size() - 1).getType() == CartItemModel.TOTAL_AMOUNT) {
            cartItemModelList.set(cartItemModelList.size() - 1, totalItem);
        } else {
            cartItemModelList.add(totalItem);
        }

        cartAdapter.notifyDataSetChanged();
    }

    private String formatPrice(double price) {
        return String.format("%,.0f VND", price);
    }
}
package com.group9.buyall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group9.buyall.ProductList.ProductList;
import com.group9.buyall.ProductList.ProductListAdapter;
import com.group9.buyall.ProductList.Product_List;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    private ImageView cartIcon;
    private TextView cartText;
    private ImageView profileIcon;
    private TextView profileText;
    private RecyclerView rcvPRODUCTLIST;
    private List<Product_List> productLists;
    private ProductListAdapter productListAdapter;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        cartIcon = findViewById(R.id.imageView11);
        cartText = findViewById(R.id.textView14);

        profileIcon = findViewById(R.id.imageView12);
        profileText = findViewById(R.id.textView15);

        editText = findViewById(R.id.editTextText2);

        rcvPRODUCTLIST = findViewById(R.id.view1);
        productLists = new ArrayList<>();
        productListAdapter = new ProductListAdapter(this,productLists);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvPRODUCTLIST.setLayoutManager(gridLayoutManager);
        rcvPRODUCTLIST.setAdapter(productListAdapter);

        // Set up OnClickListener for cartIcon and cartText
        View.OnClickListener cartClickListener = v -> showCartFragment();
        cartIcon.setOnClickListener(cartClickListener);
        cartText.setOnClickListener(cartClickListener);

        View.OnClickListener profileClickListener = v -> showProfile();
        profileText.setOnClickListener(profileClickListener);
        profileIcon.setOnClickListener(profileClickListener);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("ProductList");

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    List<Product_List> productList = new ArrayList<>();

                    for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {

                        int productID = productSnapshot.child("ProductID").getValue(int.class);
                        String productName = productSnapshot.child("ProductName").getValue(String.class);
                        String productGroup = productSnapshot.child("ProductGroup").getValue(String.class);
                        String productImageURL = productSnapshot.child("ProductImageURL").getValue(String.class);
                        String productType = productSnapshot.child("ProductType").getValue(String.class);
                        Double productPrice = productSnapshot.child("ProductPrice").getValue(Double.class);
                        Double productRating = productSnapshot.child("ProductRating").getValue(Double.class);
                        int productStock = productSnapshot.child("Stock").getValue(int.class);

                        String productShippingMethod = productSnapshot.child("ProductShippingMethod").getValue(String.class);

                        Product_List product = new Product_List(
                                productID, productType, productGroup, productName, productPrice, productRating, productShippingMethod, productImageURL,productStock);
                        productList.add(product);
                    }
                    productLists.addAll(productList);
                    productListAdapter.notifyDataSetChanged();
                } else {
                    Log.d("Firebase", "No products available.");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Error retrieving data", databaseError.toException());
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Kiểm tra nếu người dùng nhấn Enter
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Lấy giá trị đã nhập từ EditText
                    String searchQuery = editText.getText().toString();

                    // Tạo Intent để chuyển hướng tới Activity mới
                    Intent intent = new Intent(HomePageActivity.this, ProductList.class);
                    intent.putExtra("search_query", searchQuery); // Chuyển giá trị tìm kiếm qua Activity mới
                    startActivity(intent);


                    // Đóng bàn phím
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });
    }

    private void showCartFragment() {
        CartFragment cartFragment = new CartFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.cart_fragment_container, cartFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        findViewById(R.id.cart_fragment_container).setVisibility(View.VISIBLE);
    }

    private void showProfile() {
        Intent intent = new Intent(HomePageActivity.this, ProfileActivity.class);
        startActivity(intent);
    }


}

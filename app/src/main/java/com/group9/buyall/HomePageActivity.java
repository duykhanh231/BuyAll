package com.group9.buyall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

        Product_List productList1 = new Product_List("1","RAM LAPTOP DDR4 8GB", 400000, 4.5, "Standard", R.drawable.ddr4, "HCMC" );
        Product_List productList2 = new Product_List("2", "RAM LAPTOP DDR5 8GB", 560000, 4, "Instant", R.drawable.ddr5,"HCMC" );
        Product_List productList3 = new Product_List("3", "RAM LAPTOP DDR3 8GB", 150000, 3, "Instant", R.drawable.ddr3,"Da Nang" );

        productLists.add(productList1);
        productLists.add(productList2);
        productLists.add(productList3);

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

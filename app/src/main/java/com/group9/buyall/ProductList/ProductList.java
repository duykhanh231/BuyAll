package com.group9.buyall.ProductList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.group9.buyall.R;

import java.util.ArrayList;
import java.util.List;

public class ProductList extends AppCompatActivity {
    private RecyclerView rcvProduct;
    private List<Product_List> listProductList;
    private ProductListAdapter productListAdapter;
    private FrameLayout rightFrame,dimOverlay;
    private ViewFlipper vfADS;
    private TextView tvLienQuan, tvMoiNhat, tvBanChay, tvGia;
    private TextInputEditText textInputEditText;
    private ImageButton turnbackarrow;
    private int tvGiaClickCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_list);

        rcvProduct = findViewById(R.id.rcvproduct);
        listProductList = new ArrayList<>();
        productListAdapter = new ProductListAdapter(this,listProductList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvProduct.setLayoutManager(gridLayoutManager);
        rcvProduct.setAdapter(productListAdapter);

        textInputEditText = findViewById(R.id.Search);

        Product_List productList1 = new Product_List("1","RAM LAPTOP DDR4 8GB", 400000, 4.5, "Nhanh", R.drawable.ddr4, "TP.HCM" );
        Product_List productList2 = new Product_List("2", "RAM LAPTOP DDR5 8GB", 560000, 4, "Hỏa Tốc", R.drawable.ddr5,"TP.HCM" );
        Product_List productList3 = new Product_List("3", "RAM LAPTOP DDR3 8GB", 150000, 3, "Hỏa Tốc", R.drawable.ddr3,"Đà Nẵng" );

        listProductList.add(productList1);
        listProductList.add(productList2);
        listProductList.add(productList3);


        dimOverlay = findViewById(R.id.dim_overlay);
        rightFrame = findViewById(R.id.right_frame);

        ImageButton filterButton = findViewById(R.id.filter);

        tvLienQuan = findViewById(R.id.tvLienQuan);
        tvMoiNhat = findViewById(R.id.tvMoiNhat);
        tvBanChay = findViewById(R.id.tvBanChay);
        tvGia = findViewById(R.id.tvGia);

        setTextViewClickListener(tvLienQuan);
        setTextViewClickListener(tvMoiNhat);
        setTextViewClickListener(tvBanChay);
        setTextViewClickListener(tvGia);


        vfADS = findViewById(R.id.vfads);
        vfADS.setFlipInterval(3500);
        vfADS.startFlipping();

        Intent intent = getIntent();
        String searchQuery = intent.getStringExtra("search_query");
        textInputEditText.setText(searchQuery);

        turnbackarrow = findViewById(R.id.arrow);
        turnbackarrow.setOnClickListener(v -> {

            finish();
        });

        dimOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightFrame.setVisibility(View.GONE);
                dimOverlay.setVisibility(View.GONE); // Ẩn lớp phủ khi nhấn ra ngoài
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFilterButtonClick();
            }
        });
    }

    private void setTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView == tvGia) {
                tvGiaClickCount++;
                if (tvGiaClickCount == 1) {
                    textView.setText("Price ↑");
                } else if (tvGiaClickCount == 2) {
                    textView.setText("Price ↓");
                } else {
                    tvGiaClickCount = 0;
                    textView.setText("Price ↕");
                }
                resetTextViewColors();
                textView.setTextColor(Color.RED);
            } else {
                tvGiaClickCount = 0;
                tvGia.setText("Price ↕");
                resetTextViewColors();
                textView.setTextColor(Color.RED);
            }
        });
    }

    private void resetTextViewColors() {
        tvLienQuan.setTextColor(Color.BLACK);
        tvMoiNhat.setTextColor(Color.BLACK);
        tvBanChay.setTextColor(Color.BLACK);
        tvGia.setTextColor(Color.BLACK);
    }

    private void onFilterButtonClick() {
        FilterFragment filterFragment = new FilterFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.right_frame, filterFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        findViewById(R.id.right_frame).setVisibility(View.VISIBLE);
        updateDimOverlayVisibility();
    }
    void updateDimOverlayVisibility() {
        if (rightFrame.getVisibility() == View.VISIBLE) {
            dimOverlay.setVisibility(View.VISIBLE);
        } else {
            dimOverlay.setVisibility(View.GONE);
        }
    }
}

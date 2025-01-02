package com.group9.buyall.ProductList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.group9.buyall.R;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductList extends AppCompatActivity {
    private RecyclerView rcvProduct;
    private List<Product_List> listProductList,sortedlistProductList,normallistProductList;
    private ProductListAdapter productListAdapter;
    private FrameLayout rightFrame,dimOverlay;
    private ViewFlipper vfADS;
    private TextView tvLienQuan, tvGia,khongsp;
    private EditText textInputEditText;
    private ImageButton turnbackarrow;
    private int tvGiaClickCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_list);

        khongsp = findViewById(R.id.tvKHONGSP);
        rcvProduct = findViewById(R.id.rcvproduct);
        listProductList = new ArrayList<>();
        sortedlistProductList = new ArrayList<>();
        normallistProductList = new ArrayList<>();
        productListAdapter = new ProductListAdapter(this,listProductList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvProduct.setLayoutManager(gridLayoutManager);
        rcvProduct.setAdapter(productListAdapter);
        textInputEditText = findViewById(R.id.Search);
        Intent intent = getIntent();
        String searchQuery = intent.getStringExtra("search_query");
        textInputEditText.setText(searchQuery);
        khongsp.setVisibility(View.GONE);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("ProductList");

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    List<Product_List> productList = new ArrayList<>();
                    for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                        String productName = productSnapshot.child("ProductName").getValue(String.class);
                        if (!productName.toLowerCase().contains(searchQuery.toLowerCase())||searchQuery.isEmpty()){continue;}
                        int productID = productSnapshot.child("ProductID").getValue(int.class);
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
                    listProductList.addAll(productList);
                    sortedlistProductList.addAll(productList);
                    normallistProductList.addAll(productList);
                    productListAdapter.notifyDataSetChanged();
                    if (listProductList.isEmpty()){khongsp.setVisibility(View.VISIBLE);}
                    else {khongsp.setVisibility(View.GONE);}
                } else {
                    Log.d("Firebase", "No products available.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Error retrieving data", databaseError.toException());
            }
        });
        dimOverlay = findViewById(R.id.dim_overlay);
        rightFrame = findViewById(R.id.right_frame);

        ImageButton filterButton = findViewById(R.id.filter);

        tvLienQuan = findViewById(R.id.tvLienQuan);
        tvGia = findViewById(R.id.tvGia);

        setTextViewClickListener(tvLienQuan);
        setTextViewClickListener(tvGia);


        vfADS = findViewById(R.id.vfads);
        vfADS.setFlipInterval(3500);
        vfADS.startFlipping();

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

        filterAndDisplayProducts(searchQuery);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.right_frame).setVisibility(View.VISIBLE);
                updateDimOverlayVisibility();
            }
        });

        textInputEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchQueryy = textInputEditText.getText().toString();
                    Intent intent = new Intent(ProductList.this, ProductList.class);
                    intent.putExtra("search_query", searchQueryy);
                    finish();
                    startActivity(intent);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null && getCurrentFocus() != null) {
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }

                    return true;
                }
                return false;
            }
        });

    }

    private void setTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView == tvGia) {
                tvGiaClickCount++;
                if (tvGiaClickCount == 1) {
                    textView.setText("Price ↑");
                    PriceMINTOMAX(sortedlistProductList);
                    listProductList.clear();
                    listProductList.addAll(sortedlistProductList);
                    productListAdapter.notifyDataSetChanged();
                } else {
                    tvGiaClickCount = 0;
                    textView.setText("Price ↓");
                    PriceMAXTOMIN(sortedlistProductList);
                    listProductList.clear();
                    listProductList.addAll(sortedlistProductList);
                    productListAdapter.notifyDataSetChanged();
                }
                resetTextViewColors();
                textView.setTextColor(Color.RED);
            } else {
                if (textView.getCurrentTextColor() == Color.RED) {
                    tvGiaClickCount = 0;
                    resetTextViewColors();
                    tvGia.setText("Price ↕");
                    return;
                }
                tvGiaClickCount = 0;
                tvGia.setText("Price ↕");
                resetTextViewColors();
                textView.setTextColor(Color.RED);
                if (normallistProductList.isEmpty()){return;}
                listProductList.clear();
                listProductList.addAll(normallistProductList);
                productListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void resetTextViewColors() {
        tvLienQuan.setTextColor(Color.BLACK);
        tvGia.setTextColor(Color.BLACK);
    }

    public void onRamFilterButtonClick() {
        RamFilterFragment ramFilterFragment = new RamFilterFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.right_frame, ramFilterFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        findViewById(R.id.right_frame).setVisibility(View.VISIBLE);
        updateDimOverlayVisibility();
    }

    public void onHDDSDDFilterButtonClick() {
        HDDSDDFilterFragment hddsddFilterFragment = new HDDSDDFilterFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.right_frame, hddsddFilterFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        findViewById(R.id.right_frame).setVisibility(View.VISIBLE);
        updateDimOverlayVisibility();
    }

    public void onCpuIntelFilterButtonClick() {
        CpuIntelFilterFragment cpuIntelFilterFragment = new CpuIntelFilterFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.right_frame, cpuIntelFilterFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        findViewById(R.id.right_frame).setVisibility(View.VISIBLE);
        updateDimOverlayVisibility();
    }

    public void onCpuAMDFilterButtonClick() {
        CpuAMDFilterFragment cpuAMDFilterFragment = new CpuAMDFilterFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.right_frame, cpuAMDFilterFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        findViewById(R.id.right_frame).setVisibility(View.VISIBLE);
        updateDimOverlayVisibility();
    }

    public void onVgaNvidiaFilterButtonClick() {
        VgaNvidiaFilterFragment vgaNvidiaFilterFragment = new VgaNvidiaFilterFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.right_frame, vgaNvidiaFilterFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        findViewById(R.id.right_frame).setVisibility(View.VISIBLE);
        updateDimOverlayVisibility();
    }

    public void onVgaAMDFilterButtonClick() {
        VgaAMDFilterFragment vgaAMDFilterFragment = new VgaAMDFilterFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.right_frame, vgaAMDFilterFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        findViewById(R.id.right_frame).setVisibility(View.VISIBLE);
        updateDimOverlayVisibility();
    }

    public void onAllFilterButtonClick() {
        AllFilterFragment allFilterFragment = new AllFilterFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.right_frame, allFilterFragment);
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

    private void filterAndDisplayProducts(String searchQuery) {
        if (searchQuery.toLowerCase().contains("ram")||searchQuery.toLowerCase().contains("bo nho")||searchQuery.toLowerCase().contains("bộ nhớ")){
            RamFilterFragment ramFilterFragment = new RamFilterFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.right_frame, ramFilterFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (searchQuery.toLowerCase().contains("o cung")||searchQuery.toLowerCase().contains("ổ cứng")||searchQuery.toLowerCase().contains("ssd")||searchQuery.toLowerCase().contains("hdd")) {
            HDDSDDFilterFragment hddsddFilterFragment = new HDDSDDFilterFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.right_frame, hddsddFilterFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (searchQuery.toLowerCase().contains("cpu intel")){
            CpuIntelFilterFragment cpuIntelFilterFragment = new CpuIntelFilterFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.right_frame, cpuIntelFilterFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (searchQuery.toLowerCase().contains("cpu amd")){
            CpuAMDFilterFragment cpuAMDFilterFragment = new CpuAMDFilterFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.right_frame, cpuAMDFilterFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (searchQuery.toLowerCase().contains("vga nvidia")||searchQuery.toLowerCase().contains("card do hoa nvidia")||searchQuery.toLowerCase().contains("card đồ họa nvidia")){
            VgaNvidiaFilterFragment vgaNvidiaFilterFragment = new VgaNvidiaFilterFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.right_frame, vgaNvidiaFilterFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (searchQuery.toLowerCase().contains("vga amd")||searchQuery.toLowerCase().contains("card do hoa amd")||searchQuery.toLowerCase().contains("card đồ họa amd")){
            VgaAMDFilterFragment vgaAMDFilterFragment = new VgaAMDFilterFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.right_frame, vgaAMDFilterFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {AllFilterFragment allFilterFragment = new AllFilterFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.right_frame, allFilterFragment);
            transaction.addToBackStack(null);
            transaction.commit();}
    }
    public void UpdateListProductAdapter(ArrayList<Integer> arrayList) {
        listProductList.clear();
        sortedlistProductList.clear();
        normallistProductList.clear();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("ProductList");

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    List<Product_List> productList = new ArrayList<>();
                    for (int a : arrayList) {
                        for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                            int productID = productSnapshot.child("ProductID").getValue(int.class);
                            if (productID != a) { continue; }
                            String productName = productSnapshot.child("ProductName").getValue(String.class);
                            String productGroup = productSnapshot.child("ProductGroup").getValue(String.class);
                            String productImageURL = productSnapshot.child("ProductImageURL").getValue(String.class);
                            String productType = productSnapshot.child("ProductType").getValue(String.class);
                            Double productPrice = productSnapshot.child("ProductPrice").getValue(Double.class);
                            Double productRating = productSnapshot.child("ProductRating").getValue(Double.class);
                            int productStock = productSnapshot.child("Stock").getValue(int.class);
                            String productShippingMethod = productSnapshot.child("ProductShippingMethod").getValue(String.class);
                            Product_List product = new Product_List(
                                    productID, productType, productGroup, productName, productPrice, productRating,
                                    productShippingMethod, productImageURL, productStock);
                            productList.add(product);
                        }
                    }
                    Collections.sort(productList, new Comparator<Product_List>() {
                        @Override
                        public int compare(Product_List p1, Product_List p2) {
                            return Double.compare(p1.getPrice(), p2.getPrice());
                        }
                    });
                    sortedlistProductList.addAll(productList);
                    listProductList.addAll(productList);
                    normallistProductList.addAll(productList);
                    productListAdapter.notifyDataSetChanged();
                    if (listProductList.isEmpty()){khongsp.setVisibility(View.VISIBLE);}
                    else {khongsp.setVisibility(View.GONE);}
                } else {
                    Log.d("Firebase", "No products available.");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Error retrieving data", databaseError.toException());
            }
        });
    }

    private void PriceMINTOMAX(List<Product_List> list){
        if (list == null || list.isEmpty()) {
            return;
        }
        Collections.sort(list, new Comparator<Product_List>() {
            @Override
            public int compare(Product_List p1, Product_List p2) {
                return Double.compare(p1.getPrice(), p2.getPrice());
            }
        });
    }
    private void PriceMAXTOMIN(List<Product_List> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Collections.reverse(list);
    }



}

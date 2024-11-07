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
    private TextView tvscrollTPHCM, tvscrollHANOI, tvscrollHUE, tvscrollDANANG;
    private TextView tvscrollshippingHOATOC, tvscrollshippingNHANH, tvscrollshippingTIETKIEM;
    private TextView tvscrollprice0to100k, tvscrollprice100kto200k;
    private TextView tvscrollrate1STAR, tvscrollrate2STAR, tvscrollrate3STAR, tvscrollrate4STAR, tvscrollrate5STAR;
    private TextView tvscrollpromotionVOUCHERXTRA, tvscrollpromotionDANGGIAMGIA, tvscrollpromotionGICUNGRE, tvscrollpromotionHANGCOSAN;
    private  TextView tvscrollshoptypeSHOPXUHUONG, tvscrollshoptypeSHOPYEUTHICH, tvscrollshoptypeSHOPKYCUU, tvscrollshoptypeCHOICE;
    private  EditText scrollpricerangeMAX, scrollpricerangeMIN;
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

        Button Xacnhanfilterbtn = findViewById(R.id.btnscrollXACNHAN);
        Button Xaclaplaifilterbtn = findViewById(R.id.btnscrollXACLAPLAI);

        scrollpricerangeMAX = findViewById(R.id.scrollpricerangeMAX);
        scrollpricerangeMIN = findViewById(R.id.scrollpricerangeMIN);

        tvLienQuan = findViewById(R.id.tvLienQuan);
        tvMoiNhat = findViewById(R.id.tvMoiNhat);
        tvBanChay = findViewById(R.id.tvBanChay);
        tvGia = findViewById(R.id.tvGia);

        tvscrollHANOI = findViewById(R.id.tvscrollHANOI);
        tvscrollDANANG = findViewById(R.id.tvscrollDANANG);
        tvscrollHUE = findViewById(R.id.tvscrollHUE);
        tvscrollTPHCM = findViewById(R.id.tvscrollTPHCM);

        tvscrollshippingHOATOC = findViewById(R.id.tvscrollshippingHOATOC);
        tvscrollshippingNHANH = findViewById(R.id.tvscrollshippingNHANH);
        tvscrollshippingTIETKIEM = findViewById(R.id.tvscrollshippingTIETKIEM);

        tvscrollprice0to100k = findViewById(R.id.tvscrollprice0to100k);
        tvscrollprice100kto200k = findViewById(R.id.tvscrollprice100kto200k);

        tvscrollrate1STAR = findViewById(R.id.tvscrollrate1STAR);
        tvscrollrate2STAR = findViewById(R.id.tvscrollrate2STAR);
        tvscrollrate3STAR = findViewById(R.id.tvscrollrate3STAR);
        tvscrollrate4STAR = findViewById(R.id.tvscrollrate4STAR);
        tvscrollrate5STAR = findViewById(R.id.tvscrollrate5STAR);

        tvscrollpromotionVOUCHERXTRA = findViewById(R.id.tvscrollpromotionVOUCHERXTRA);
        tvscrollpromotionDANGGIAMGIA = findViewById(R.id.tvscrollpromotionDANGGIAMGIA);
        tvscrollpromotionGICUNGRE = findViewById(R.id.tvscrollpromotionGICUNGRE);
        tvscrollpromotionHANGCOSAN = findViewById(R.id.tvscrollpromotionHANGCOSAN);

        tvscrollshoptypeSHOPYEUTHICH = findViewById(R.id.tvscrollshoptypeSHOPYEUTHICH);
        tvscrollshoptypeSHOPXUHUONG = findViewById(R.id.tvscrollshoptypeSHOPXUHUONG);
        tvscrollshoptypeSHOPKYCUU = findViewById(R.id.tvscrollshoptypeSHOPKYCUU);
        tvscrollshoptypeCHOICE = findViewById(R.id.tvscrollshoptypeCHOICE);

        setTextViewClickListener(tvLienQuan);
        setTextViewClickListener(tvMoiNhat);
        setTextViewClickListener(tvBanChay);
        setTextViewClickListener(tvGia);

        setLocationScrollTextViewClickListener(tvscrollHANOI);
        setLocationScrollTextViewClickListener(tvscrollDANANG);
        setLocationScrollTextViewClickListener(tvscrollHUE);
        setLocationScrollTextViewClickListener(tvscrollTPHCM);

        setShippingUnitScrollTextViewClickListener(tvscrollshippingHOATOC);
        setShippingUnitScrollTextViewClickListener(tvscrollshippingNHANH);
        setShippingUnitScrollTextViewClickListener(tvscrollshippingTIETKIEM);

        setPriceRangeScrollTextViewClickListener(tvscrollprice0to100k);
        setPriceRangeScrollTextViewClickListener(tvscrollprice100kto200k);

        setRateScrollTextViewClickListener(tvscrollrate1STAR);
        setRateScrollTextViewClickListener(tvscrollrate2STAR);
        setRateScrollTextViewClickListener(tvscrollrate3STAR);
        setRateScrollTextViewClickListener(tvscrollrate4STAR);
        setRateScrollTextViewClickListener(tvscrollrate5STAR);

        setPromotionScrollTextViewClickListener(tvscrollpromotionVOUCHERXTRA);
        setPromotionScrollTextViewClickListener(tvscrollpromotionDANGGIAMGIA);
        setPromotionScrollTextViewClickListener(tvscrollpromotionGICUNGRE);
        setPromotionScrollTextViewClickListener(tvscrollpromotionHANGCOSAN);

        setShopTypeScrollTextViewClickListener(tvscrollshoptypeSHOPXUHUONG);
        setShopTypeScrollTextViewClickListener(tvscrollshoptypeSHOPYEUTHICH);
        setShopTypeScrollTextViewClickListener(tvscrollshoptypeSHOPKYCUU);
        setShopTypeScrollTextViewClickListener(tvscrollshoptypeCHOICE);

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
        Xacnhanfilterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightFrame.setVisibility(View.GONE);
                dimOverlay.setVisibility(View.GONE);
            }
        });
        Xaclaplaifilterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                resetLocationScrollTextViewColors();
                resetShippingUnitScrollTextViewColors();
                resetPriceRangeScrollTextViewColors();
                resetRateScrollTextViewColors();
                resetPromotionScrollTextViewColors();
                resetShopTypePromotionScrollTextViewColors();
                scrollpricerangeMIN.setText("");
                scrollpricerangeMAX.setText("");
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

    private void setLocationScrollTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            // Đặt màu đen cho tất cả các TextView
            resetLocationScrollTextViewColors();

            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
        });
    }

    private void setShippingUnitScrollTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            // Đặt màu đen cho tất cả các TextView
            resetShippingUnitScrollTextViewColors();

            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
        });
    }

    @SuppressLint("SetTextI18n")
    private void setPriceRangeScrollTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView == tvscrollprice0to100k) {
                scrollpricerangeMIN.setText("0");
                scrollpricerangeMAX.setText("100000");
                resetPriceRangeScrollTextViewColors();
                textView.setTextColor(Color.RED);
                textView.setBackgroundResource(R.drawable.border_textview_red);
            } else {
                scrollpricerangeMIN.setText("100000");
                scrollpricerangeMAX.setText("200000");
                resetPriceRangeScrollTextViewColors();
                textView.setTextColor(Color.RED);
                textView.setBackgroundResource(R.drawable.border_textview_red);
            }
        });
    }

    private void setRateScrollTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            // Đặt màu đen cho tất cả các TextView
            resetRateScrollTextViewColors();

            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
        });
    }

    private void setPromotionScrollTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            // Đặt màu đen cho tất cả các TextView
            resetPromotionScrollTextViewColors();

            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
        });
    }

    private void setShopTypeScrollTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            // Đặt màu đen cho tất cả các TextView
            resetShopTypePromotionScrollTextViewColors();

            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
        });
    }

    private void resetTextViewColors() {
        tvLienQuan.setTextColor(Color.BLACK);
        tvMoiNhat.setTextColor(Color.BLACK);
        tvBanChay.setTextColor(Color.BLACK);
        tvGia.setTextColor(Color.BLACK);
    }

    private void resetLocationScrollTextViewColors() {
        tvscrollTPHCM.setTextColor(Color.BLACK);
        tvscrollHUE.setTextColor(Color.BLACK);
        tvscrollDANANG.setTextColor(Color.BLACK);
        tvscrollHANOI.setTextColor(Color.BLACK);

        tvscrollTPHCM.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollHUE.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollDANANG.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollHANOI.setBackgroundColor(Color.parseColor("#D3D3D3"));
    }

    private void resetShippingUnitScrollTextViewColors() {
        tvscrollshippingTIETKIEM.setTextColor(Color.BLACK);
        tvscrollshippingNHANH.setTextColor(Color.BLACK);
        tvscrollshippingHOATOC.setTextColor(Color.BLACK);

        tvscrollshippingTIETKIEM.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollshippingNHANH.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollshippingHOATOC.setBackgroundColor(Color.parseColor("#D3D3D3"));


    }

    private void resetPriceRangeScrollTextViewColors() {
        tvscrollprice100kto200k.setTextColor(Color.BLACK);
        tvscrollprice0to100k.setTextColor(Color.BLACK);

        tvscrollprice100kto200k.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollprice0to100k.setBackgroundColor(Color.parseColor("#D3D3D3"));

    }

    private void resetRateScrollTextViewColors() {
        tvscrollrate1STAR.setTextColor(Color.BLACK);
        tvscrollrate2STAR.setTextColor(Color.BLACK);
        tvscrollrate3STAR.setTextColor(Color.BLACK);
        tvscrollrate4STAR.setTextColor(Color.BLACK);
        tvscrollrate5STAR.setTextColor(Color.BLACK);

        tvscrollrate1STAR.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollrate2STAR.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollrate3STAR.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollrate4STAR.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollrate5STAR.setBackgroundColor(Color.parseColor("#D3D3D3"));

    }

    private void resetPromotionScrollTextViewColors() {
        tvscrollpromotionVOUCHERXTRA.setTextColor(Color.BLACK);
        tvscrollpromotionDANGGIAMGIA.setTextColor(Color.BLACK);
        tvscrollpromotionGICUNGRE.setTextColor(Color.BLACK);
        tvscrollpromotionHANGCOSAN.setTextColor(Color.BLACK);

        tvscrollpromotionVOUCHERXTRA.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollpromotionDANGGIAMGIA.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollpromotionGICUNGRE.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollpromotionHANGCOSAN.setBackgroundColor(Color.parseColor("#D3D3D3"));
    }

    private void resetShopTypePromotionScrollTextViewColors() {
        tvscrollshoptypeSHOPYEUTHICH.setTextColor(Color.BLACK);
        tvscrollshoptypeCHOICE.setTextColor(Color.BLACK);
        tvscrollshoptypeSHOPKYCUU.setTextColor(Color.BLACK);
        tvscrollshoptypeSHOPXUHUONG.setTextColor(Color.BLACK);

        tvscrollshoptypeSHOPYEUTHICH.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollshoptypeCHOICE.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollshoptypeSHOPKYCUU.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollshoptypeSHOPXUHUONG.setBackgroundColor(Color.parseColor("#D3D3D3"));
    }

    private void onFilterButtonClick() {
        if (rightFrame.getVisibility() == View.GONE) {
            rightFrame.setVisibility(View.VISIBLE);
            dimOverlay.setVisibility(View.VISIBLE);
        } else {
            rightFrame.setVisibility(View.GONE);
            dimOverlay.setVisibility(View.GONE);
        }
    }
}

package com.example.productdetail;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class FullCommentActivity extends AppCompatActivity {
    private ImageButton ibArrow;
    private String productId;
    private List<ProductDetailComment> MyListProductDetailComment;
    private RecyclerView rcvFULLCOMMENT;
    private ProductDetailCommentAdapter productDetailCommentAdapter;
    private TextView tvCOOMENTSAO,tvbsBOLOC,tvbsDONGY,tvTATCA,tvCOHINHANHVAVIDEO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_comment);
        ibArrow = findViewById(R.id.arrowfullcomment);
        ibArrow.setOnClickListener(v -> {
            finish();
        });
        tvCOOMENTSAO = findViewById(R.id.tvCOMMENTSAO);
        String text = "Sao ★ ▾";
        Spannable spannable = new SpannableString(text);
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FFD700")), 4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // 4 và 5 là vị trí của sao
        tvCOOMENTSAO.setText(spannable);
        tvTATCA = findViewById(R.id.tvCOMMENTTATCA);
        tvCOHINHANHVAVIDEO = findViewById(R.id.tvCOMMENTCOHINHANHVAVIDEO);

        setTextViewClickListener(tvTATCA);
        setTextViewClickListener(tvCOHINHANHVAVIDEO);

        tvCOOMENTSAO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(FullCommentActivity.this);
                View view = LayoutInflater.from(FullCommentActivity.this).inflate(R.layout.bottom_sheet_filtercommentstar,null);
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();

                tvbsBOLOC = view.findViewById(R.id.tvbsBOLOC);
                tvbsDONGY = view.findViewById(R.id.tvbsDONGY);

                tvbsBOLOC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                tvbsDONGY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
            }
        });

        productId = getIntent().getStringExtra("PRODUCT_ID");

        rcvFULLCOMMENT = findViewById(R.id.rcvFULLCOMMENT);
        MyListProductDetailComment = new ArrayList<>();
        productDetailCommentAdapter = new ProductDetailCommentAdapter(MyListProductDetailComment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvFULLCOMMENT.setLayoutManager(linearLayoutManager);
        rcvFULLCOMMENT.setAdapter(productDetailCommentAdapter);

        for (ProductDetailComment product_detail_comment : ProductDetailComment.commentsList) {
            if (product_detail_comment.getProductId().equals(productId)) {
                MyListProductDetailComment.add(product_detail_comment);
            }
        }
    }

    private void setTextViewClickListener(TextView textView){
        textView.setOnClickListener(v -> {
            // Đặt màu đen cho tất cả các TextView
            resetTextViewColors();

            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
        });
    }
    private void resetTextViewColors() {
        tvTATCA.setTextColor(Color.BLACK);
        tvCOHINHANHVAVIDEO.setTextColor(Color.BLACK);
    }
}

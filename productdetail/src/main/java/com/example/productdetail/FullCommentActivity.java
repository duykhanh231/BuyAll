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
        ProductDetailComment productDetailComment1 = new ProductDetailComment("1","1","1" ,"User 1", R.drawable.user,"Sản phẩm rất tuyệt vời, không có gì để chê",R.drawable.ddr4_1,R.drawable.ddr4_2,5);
        ProductDetailComment productDetailComment2 = new ProductDetailComment("1","2","2" ,"User 2", R.drawable.user,"Sản phẩm khá tốt, tối khá hài lòng",R.drawable.ddr4_2,0,4);
        ProductDetailComment productDetailComment3 = new ProductDetailComment("2","3","3" ,"User 3", R.drawable.user,"Sản phẩm cũng tạm ổn, shop giao hàng chậm",R.drawable.ddr5_1,R.drawable.ddr5_2,3);
        ProductDetailComment productDetailComment4 = new ProductDetailComment("2","4","4" ,"User 4", R.drawable.user,"Sản phẩm rất mạnh mẽ tôi rất thích, 2 thanh thừa sức chiến Wukong ",R.drawable.ddr5_3,R.drawable.ddr5_4,5);
        ProductDetailComment productDetailComment5 = new ProductDetailComment("3","3","5" ,"User 5", R.drawable.user,"Sản phẩm cũng hoạt động ổn định, giao hàng sớm hơn dự kiến",R.drawable.ddr3_1,R.drawable.ddr3_2,5);
        ProductDetailComment productDetailComment6 = new ProductDetailComment("3","4","6" ,"User 6", R.drawable.user,"Sản phẩm hoat động không ổn định lỗi màn hình xanh",R.drawable.ddr3_3,0,1);


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

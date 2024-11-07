package com.group9.buyall.ProductDetail;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.group9.buyall.CartFragment;
import com.group9.buyall.R;

import java.util.ArrayList;
import java.util.List;

public class FullCommentFragment extends Fragment {
    private String productId;
    private List<ProductDetailComment> MyListProductDetailComment;
    private RecyclerView rcvFULLCOMMENT;
    private ProductDetailCommentAdapter productDetailCommentAdapter;
    private TextView tvCOOMENTSAO, tvbsBOLOC, tvbsDONGY, tvTATCA, tvCOHINHANHVAVIDEO;

    public FullCommentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.full_comment, container, false);

        tvCOOMENTSAO = view.findViewById(R.id.tvCOMMENTSAO);
        String text = "Sao ★ ▾";
        Spannable spannable = new SpannableString(text);
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FFD700")), 4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvCOOMENTSAO.setText(spannable);

        tvTATCA = view.findViewById(R.id.tvCOMMENTTATCA);
        tvCOHINHANHVAVIDEO = view.findViewById(R.id.tvCOMMENTCOHINHANHVAVIDEO);

        setTextViewClickListener(tvTATCA);
        setTextViewClickListener(tvCOHINHANHVAVIDEO);

        tvCOOMENTSAO.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
            View bottomSheetView = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_filtercommentstar, null);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();

            tvbsBOLOC = bottomSheetView.findViewById(R.id.tvbsBOLOC);
            tvbsDONGY = bottomSheetView.findViewById(R.id.tvbsDONGY);

            tvbsBOLOC.setOnClickListener(v1 -> bottomSheetDialog.dismiss());
            tvbsDONGY.setOnClickListener(v1 -> bottomSheetDialog.dismiss());
        });

        Bundle args = getArguments();
        if (args != null) {
            productId = args.getString("PRODUCT_ID");
        } else {
            Log.e("FullCommentFragment", "Arguments are null");
        }

        rcvFULLCOMMENT = view.findViewById(R.id.rcvFULLCOMMENT);
        MyListProductDetailComment = new ArrayList<>();
        productDetailCommentAdapter = new ProductDetailCommentAdapter(MyListProductDetailComment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvFULLCOMMENT.setLayoutManager(linearLayoutManager);
        rcvFULLCOMMENT.setAdapter(productDetailCommentAdapter);

        for (ProductDetailComment product_detail_comment : ProductDetailComment.commentsList) {
            if (product_detail_comment.getProductId().equals(productId)) {
                MyListProductDetailComment.add(product_detail_comment);
            }
        }

        return view;
    }

    private void setTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            resetTextViewColors();
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
        });
    }

    private void resetTextViewColors() {
        tvTATCA.setTextColor(Color.BLACK);
        tvCOHINHANHVAVIDEO.setTextColor(Color.BLACK);
        tvTATCA.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvCOHINHANHVAVIDEO.setBackgroundColor(Color.parseColor("#D3D3D3"));
    }

}

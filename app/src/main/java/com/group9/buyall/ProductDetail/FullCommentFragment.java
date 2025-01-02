package com.group9.buyall.ProductDetail;

import android.content.Context;
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
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group9.buyall.CartFragment;
import com.group9.buyall.ProductList.CpuIntel;
import com.group9.buyall.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FullCommentFragment extends Fragment {
    private int productId;
    private List<ProductDetailComment> MyListProductDetailComment,secondProductDetailComment;
    private RecyclerView rcvFULLCOMMENT;
    private ProductDetailCommentAdapter productDetailCommentAdapter;
    private TextView tvCOOMENTSAO, tvbsBOLOC, tvbsDONGY, tvTATCA;
    private Context context;
    private RadioButton rb1,rb2,rb3,rb4,rb5;
    private ArrayList<Integer> arrayList;

    public FullCommentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.full_comment, container, false);

        secondProductDetailComment = new ArrayList<>();
        arrayList = new ArrayList<>();
        tvCOOMENTSAO = view.findViewById(R.id.tvCOMMENTSAO);
        String text = "Sao ★ ▾";
        Spannable spannable = new SpannableString(text);
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FFD700")), 4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvCOOMENTSAO.setText(spannable);
        tvTATCA = view.findViewById(R.id.tvCOMMENTTATCA);
        tvTATCA.setTextColor(Color.RED);
        tvTATCA.setBackgroundResource(R.drawable.border_textview_red);
        setTextViewClickListener(tvTATCA);
        Bundle args = getArguments();
        if (args != null) {
            productId = args.getInt("PRODUCT_ID");
        } else {
            Log.e("FullCommentFragment", "Arguments are null");
        }

        rcvFULLCOMMENT = view.findViewById(R.id.rcvFULLCOMMENT);
        MyListProductDetailComment = new ArrayList<>();
        productDetailCommentAdapter = new ProductDetailCommentAdapter(MyListProductDetailComment,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvFULLCOMMENT.setLayoutManager(linearLayoutManager);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("ProductComment");

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    List<ProductDetailComment> productDetailCommentList = new ArrayList<>();

                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                        int productID = userSnapshot.child("ProductID").getValue(Integer.class);
                        if (productID!=productId) continue;
                        int commentID = userSnapshot.child("CommentID").getValue(Integer.class);
                        int userID = userSnapshot.child("UserID").getValue(Integer.class);
                        String userName = userSnapshot.child("UserName").getValue(String.class);
                        String userImageURL = userSnapshot.child("UserImageURL").getValue(String.class);
                        String userComment = userSnapshot.child("UserComment").getValue(String.class);
                        String productImage1 = userSnapshot.child("ProductImage1").getValue(String.class);
                        String productImage2 = userSnapshot.child("ProductImage2").getValue(String.class);
                        int userRating = userSnapshot.child("UserRating").getValue(Integer.class);
                        ProductDetailComment comment = new ProductDetailComment(
                                productID, commentID, userID, userName, userImageURL, userComment, productImage1, productImage2, userRating);
                        productDetailCommentList.add(comment);

                    }
                    MyListProductDetailComment.addAll(productDetailCommentList);
                    secondProductDetailComment.addAll(productDetailCommentList);
                    rcvFULLCOMMENT.setAdapter(productDetailCommentAdapter);
                } else {
                    Log.d("Firebase", "No products available.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Error retrieving data", databaseError.toException());
            }
        });
        tvCOOMENTSAO.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
            View bottomSheetView = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_filtercommentstar, null);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();

            rb1 = bottomSheetView.findViewById(R.id.rb1);
            rb2 = bottomSheetView.findViewById(R.id.rb2);
            rb3 = bottomSheetView.findViewById(R.id.rb3);
            rb4 = bottomSheetView.findViewById(R.id.rb4);
            rb5 = bottomSheetView.findViewById(R.id.rb5);

            tvbsBOLOC = bottomSheetView.findViewById(R.id.tvbsBOLOC);

            tvbsDONGY = bottomSheetView.findViewById(R.id.tvbsDONGY);

            tvbsBOLOC.setOnClickListener(v1 -> bottomSheetDialog.dismiss());
            tvbsDONGY.setOnClickListener(v1 -> {
                setRadioClickListener(rb1);
                setRadioClickListener(rb2);
                setRadioClickListener(rb3);
                setRadioClickListener(rb4);
                setRadioClickListener(rb5);
                if (!arrayList.isEmpty()){GetTheList();}
                productDetailCommentAdapter.notifyDataSetChanged();
                bottomSheetDialog.dismiss();
                arrayList.clear();
                resetTextViewColors();
            });
        });
        return view;
    }

    private void setTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor()==Color.BLACK){
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);}
            MyListProductDetailComment.clear();
            MyListProductDetailComment.addAll(secondProductDetailComment);
            productDetailCommentAdapter.notifyDataSetChanged();
        });
    }
    private void setRadioClickListener(RadioButton radioButton) {
        if (radioButton.isChecked()){
        int k;
        if (radioButton==rb1) {k = 1;}
        else if (radioButton==rb2) {k = 2;}
        else if (radioButton==rb3) {k = 3;}
        else if (radioButton==rb4) {k = 4;}
        else {k = 5;}
        arrayList.add(k);}
    }
    private void resetTextViewColors() {
        tvTATCA.setTextColor(Color.BLACK);
        tvTATCA.setBackgroundColor(Color.parseColor("#D3D3D3"));
    }

    private void SortTheListFromRB(int listsize){
        MyListProductDetailComment.clear();
        MyListProductDetailComment.addAll(secondProductDetailComment);
        Iterator<ProductDetailComment> iterator = MyListProductDetailComment.iterator();
        while (iterator.hasNext()) {
            ProductDetailComment productDetailComment = iterator.next();
            if (listsize == 1) {
                if (!(productDetailComment.getRating() == (arrayList.get(0)))){
                    iterator.remove();
                }
            } else if (listsize == 2) {
                if (!(productDetailComment.getRating() == (arrayList.get(0)) || (productDetailComment.getRating() == (arrayList.get(1))))) {
                    iterator.remove();
                }
            } else if (listsize == 3) {
                if (!(productDetailComment.getRating() == (arrayList.get(0)) || (productDetailComment.getRating() == (arrayList.get(1))) || (productDetailComment.getRating() == (arrayList.get(2))))) {
                    iterator.remove();
                }
            } else if (listsize == 4) {
                if (!(productDetailComment.getRating() == (arrayList.get(0)) || (productDetailComment.getRating() == (arrayList.get(1))) || (productDetailComment.getRating() == (arrayList.get(2)))|| (productDetailComment.getRating() == (arrayList.get(3))))) {
                    iterator.remove();
                }
            }
        }
    }

    private void GetTheList(){
        if (arrayList.isEmpty()) {return;}
            int jack = arrayList.size();
            SortTheListFromRB(jack);
        }
    }



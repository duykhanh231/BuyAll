package com.group9.buyall.ProductList;

import androidx.fragment.app.Fragment;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group9.buyall.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class VgaAMDFilterFragment extends Fragment {
    private TextView tvfilter8GB,  tvfilter12GB, tvfilter16GB;
    private TextView tvfilterAMDRX7000SERIES, tvfilterAMDRX6000SERIES;
    private TextView tvscrollprice0to100k, tvscrollprice100kto200k;
    private TextView tvscrollrate1STAR, tvscrollrate2STAR, tvscrollrate3STAR, tvscrollrate4STAR, tvscrollrate5STAR;
    private EditText scrollpricerangeMAX, scrollpricerangeMIN;
    private Button Xacnhanfilterbtn,Xaclaplaifilterbtn;
    private ImageView imageView;
    List<VgaAMD> vgaAMDS;
    ArrayList<String> productlinelist,capacitylist;
    private Integer Rate;
    ArrayList<Integer> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.vga_amd_filter, container, false);

        vgaAMDS = new ArrayList<>();
        productlinelist = new ArrayList<>();
        capacitylist = new ArrayList<>();
        arrayList = new ArrayList<>();
        Rate = 0;

        Xacnhanfilterbtn = view.findViewById(R.id.btnscrollXACNHAN);
        Xaclaplaifilterbtn = view.findViewById(R.id.btnscrollXACLAPLAI);

        imageView = view.findViewById(R.id.ivARROWTURNBACKALLFILTER);

        scrollpricerangeMAX = view.findViewById(R.id.scrollpricerangeMAX);
        scrollpricerangeMIN = view.findViewById(R.id.scrollpricerangeMIN);

        tvfilter8GB = view.findViewById(R.id.tvfilter8GB);
        tvfilter12GB = view.findViewById(R.id.tvfilter12GB);
        tvfilter16GB = view.findViewById(R.id.tvfilter16GB);

        tvfilterAMDRX6000SERIES = view.findViewById(R.id.tvfilterAMDRX6000SERIES);
        tvfilterAMDRX7000SERIES = view.findViewById(R.id.tvfilterAMDRX7000SERIES);

        tvscrollprice0to100k = view.findViewById(R.id.tvscrollprice0to100k);
        tvscrollprice100kto200k = view.findViewById(R.id.tvscrollprice100kto200k);

        tvscrollrate1STAR = view.findViewById(R.id.tvscrollrate1STAR);
        tvscrollrate2STAR = view.findViewById(R.id.tvscrollrate2STAR);
        tvscrollrate3STAR = view.findViewById(R.id.tvscrollrate3STAR);
        tvscrollrate4STAR = view.findViewById(R.id.tvscrollrate4STAR);
        tvscrollrate5STAR = view.findViewById(R.id.tvscrollrate5STAR);

        setCapacityTextViewClickListener(tvfilter8GB);
        setCapacityTextViewClickListener(tvfilter12GB);
        setCapacityTextViewClickListener(tvfilter16GB);

        setVGALineTextViewClickListener(tvfilterAMDRX7000SERIES);
        setVGALineTextViewClickListener(tvfilterAMDRX6000SERIES);

        setPriceRangeTextViewClickListener(tvscrollprice0to100k);
        setPriceRangeTextViewClickListener(tvscrollprice100kto200k);

        setRateTextViewClickListener(tvscrollrate1STAR);
        setRateTextViewClickListener(tvscrollrate2STAR);
        setRateTextViewClickListener(tvscrollrate3STAR);
        setRateTextViewClickListener(tvscrollrate4STAR);
        setRateTextViewClickListener(tvscrollrate5STAR);



        Xacnhanfilterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                GetTheList();
                container.setVisibility(View.GONE);
                ((ProductList) getActivity()).updateDimOverlayVisibility();
            }
        });
        Xaclaplaifilterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                resetCapacityTextViewColors();
                resetVGALineTextViewColors();
                resetPriceRangeTextViewColors();
                resetRateTextViewColors();
                scrollpricerangeMIN.setText("");
                scrollpricerangeMAX.setText("");
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.setVisibility(View.GONE);
                ((ProductList) getActivity()).onAllFilterButtonClick();
                arrayList.clear();
                resetData();
            }
        });
        TaiListVgaAMD();
        return view;
    }

    private void setCapacityTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor()==Color.RED){
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(Color.parseColor("#D3D3D3"));
                DeleteCapicityFromTextView(textView);
                return;
            }
            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
            AddCapicityFromTextView(textView);
        });
    }

    private void setVGALineTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor()==Color.RED){
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(Color.parseColor("#D3D3D3"));
                DeleteProductLineFromTextView(textView);
                return;
            }
            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
            AddProductLineFromTextView(textView);
        });
    }

    @SuppressLint("SetTextI18n")
    private void setPriceRangeTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor()==Color.RED){
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(Color.parseColor("#D3D3D3"));
                scrollpricerangeMIN.setText("");
                scrollpricerangeMAX.setText("");
                return;
            }
            if (textView == tvscrollprice0to100k) {
                scrollpricerangeMIN.setText("0");
                scrollpricerangeMAX.setText("100000");
                resetPriceRangeTextViewColors();
                textView.setTextColor(Color.RED);
                textView.setBackgroundResource(R.drawable.border_textview_red);
            } else {
                scrollpricerangeMIN.setText("100000");
                scrollpricerangeMAX.setText("200000");
                resetPriceRangeTextViewColors();
                textView.setTextColor(Color.RED);
                textView.setBackgroundResource(R.drawable.border_textview_red);
            }
        });
    }

    private void setRateTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor()==Color.RED){
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(Color.parseColor("#D3D3D3"));
                Rate = 0;
                return;
            }
            // Đặt màu đen cho tất cả các TextView
            resetRateTextViewColors();

            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
            Rate = getRatingFromTextView(textView);
        });
    }

    private void resetCapacityTextViewColors() {
        tvfilter8GB.setTextColor(Color.BLACK);
        tvfilter12GB.setTextColor(Color.BLACK);
        tvfilter16GB.setTextColor(Color.BLACK);


        tvfilter8GB.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilter12GB.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilter16GB.setBackgroundColor(Color.parseColor("#D3D3D3"));

    }

    private void resetPriceRangeTextViewColors() {
        tvscrollprice100kto200k.setTextColor(Color.BLACK);
        tvscrollprice0to100k.setTextColor(Color.BLACK);

        tvscrollprice100kto200k.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollprice0to100k.setBackgroundColor(Color.parseColor("#D3D3D3"));

    }

    private void resetRateTextViewColors() {
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

        Rate = 0;
    }


    private void resetVGALineTextViewColors() {
        tvfilterAMDRX6000SERIES.setTextColor(Color.BLACK);
        tvfilterAMDRX7000SERIES.setTextColor(Color.BLACK);

        tvfilterAMDRX6000SERIES.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterAMDRX7000SERIES.setBackgroundColor(Color.parseColor("#D3D3D3"));

    }

    private String GetCapicityFromTextView(TextView textView){
        if (textView==tvfilter8GB) return "8 GB";
        if (textView==tvfilter12GB) return "12 GB";
        if (textView==tvfilter16GB) return "16 GB";
        return null;
    }

    private void AddCapicityFromTextView(TextView textView){
        String capi = GetCapicityFromTextView(textView);
        if (capi != null) {
            capacitylist.add(capi);
        }
    }

    private void DeleteCapicityFromTextView(TextView textView){
        String capi = GetCapicityFromTextView(textView);
        if (capi != null) {
            capacitylist.remove(capi);
        }
    }

    private String GetProductLineFromTextView(TextView textView){
        if (textView==tvfilterAMDRX6000SERIES) return "AMD RX 6000 Series";
        if (textView==tvfilterAMDRX7000SERIES) return "AMD RX 7000 Series";
        return null;
    }
    private void AddProductLineFromTextView(TextView textView){
        String produc = GetProductLineFromTextView(textView);
        if (produc != null) {
            productlinelist.add(produc);
        }
    }

    private void DeleteProductLineFromTextView(TextView textView){
        String produc = GetProductLineFromTextView(textView);
        if (produc != null) {
            productlinelist.remove(produc);
        }
    }
    private Integer getRatingFromTextView(TextView textView) {
        if (textView == tvscrollrate1STAR) return 1;
        if (textView == tvscrollrate2STAR) return 2;
        if (textView == tvscrollrate3STAR) return 3;
        if (textView == tvscrollrate4STAR) return 4;
        if (textView == tvscrollrate5STAR) return 5;
        return null;
    }

    private void GetTheList(){
        if (productlinelist.isEmpty() && capacitylist.isEmpty() && (scrollpricerangeMAX.getText().toString().isEmpty() || scrollpricerangeMIN.getText().toString().isEmpty()) && Rate == 0) {
            return;
        }

        if (!capacitylist.isEmpty()) {
            int jack = capacitylist.size();
            SortTheListFromCAPICITY(jack);
        }

        if (!productlinelist.isEmpty()) {
            int jack = productlinelist.size();
            SortTheListFromPRODUCTLINE(jack);
        }

        if (!(scrollpricerangeMAX.getText().toString().isEmpty() || scrollpricerangeMIN.getText().toString().isEmpty())) {
            double jack1, jack2;
            jack1 = Integer.parseInt(scrollpricerangeMIN.getText().toString());
            jack2 = Integer.parseInt(scrollpricerangeMAX.getText().toString());
            SortTheListPRICERANGE(jack1, jack2);
        }

        if (Rate != 0) {
            GetTheListFromRate(Rate);
        }
        for (VgaAMD vga : vgaAMDS) {
            int a = vga.getProductID();
            arrayList.add(a);
        }
        ((ProductList) getActivity()).UpdateListProductAdapter(arrayList);
        resetData();
    }

    private void SortTheListFromCAPICITY(int listsize) {
        Iterator<VgaAMD> iterator = vgaAMDS.iterator();
        while (iterator.hasNext()) {
            VgaAMD vgaAMD = iterator.next();
            if (listsize == 1) {
                if (!vgaAMD.getCapacity().equals(capacitylist.get(0))) {
                    iterator.remove();
                }
            } else if (listsize == 2) {
                if (!(vgaAMD.getCapacity().equals(capacitylist.get(0)) || vgaAMD.getCapacity().equals(capacitylist.get(1)))) {
                    iterator.remove();
                }
            }
        }
    }

    private void SortTheListFromPRODUCTLINE(int listsize){
        Iterator<VgaAMD> iterator = vgaAMDS.iterator();
        while (iterator.hasNext()) {
            VgaAMD vgaAMD = iterator.next();
            if (listsize == 1) {
                if (!vgaAMD.getProductLine().equals(productlinelist.get(0))) {
                    iterator.remove();
                }
            }
        }
    }
    private void SortTheListPRICERANGE(double min, double max) {
        Iterator<VgaAMD> iterator = vgaAMDS.iterator();
        while (iterator.hasNext()) {
            VgaAMD vgaAMD = iterator.next();
            if (vgaAMD.getPrice() < min || vgaAMD.getPrice() > max) {
                iterator.remove();
            }
        }
    }

    private void GetTheListFromRate(int rate) {
        Iterator<VgaAMD> iterator = vgaAMDS.iterator();
        while (iterator.hasNext()) {
            VgaAMD vgaAMD = iterator.next();
            if (vgaAMD.getRate() < rate) {
                iterator.remove();
            }
        }
    }

    private void resetData() {
        productlinelist.clear();
        capacitylist.clear();
        vgaAMDS.clear();
        Rate = 0;
        scrollpricerangeMIN.setText("");
        scrollpricerangeMAX.setText("");
        resetCapacityTextViewColors();
        resetVGALineTextViewColors();
        resetPriceRangeTextViewColors();
        resetRateTextViewColors();
        TaiListVgaAMD();
    }
    private void TaiListVgaAMD(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("VgaAMD");
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<VgaAMD> vgaAMDS1 = new ArrayList<>();
                    for (DataSnapshot ramSnapshot : dataSnapshot.getChildren()) {
                        int productID = ramSnapshot.child("ProductID").getValue(int.class);
                        String Capacity = ramSnapshot.child("Capacity").getValue(String.class);
                        double Price = ramSnapshot.child("Price").getValue(double.class);
                        String Productl = ramSnapshot.child("ProductLine").getValue(String.class);
                        double Rate = ramSnapshot.child("Rate").getValue(double.class);
                        VgaAMD vgaAMD = new VgaAMD(productID, Capacity, Productl, Price, Rate);
                        vgaAMDS1.add(vgaAMD);
                    }
                    vgaAMDS.addAll(vgaAMDS1);
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
}
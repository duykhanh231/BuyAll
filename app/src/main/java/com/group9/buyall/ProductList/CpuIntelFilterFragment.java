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


public class CpuIntelFilterFragment extends Fragment {
    private TextView tvfilterCOREI3, tvfilterCOREI5, tvfilterCOREI7, tvfilterCOREI9;
    private TextView tvfilterLGA1700, tvfilterLGA1200;
    private TextView tvfilterINTELTHEHE11, tvfilterINTELTHEHE12, tvfilterINTELTHEHE13, tvfilterINTELTHEHE14;
    private TextView tvscrollprice0to100k, tvscrollprice100kto200k;
    private TextView tvscrollrate1STAR, tvscrollrate2STAR, tvscrollrate3STAR, tvscrollrate4STAR, tvscrollrate5STAR;
    private EditText scrollpricerangeMAX, scrollpricerangeMIN;
    private Button Xacnhanfilterbtn,Xaclaplaifilterbtn;
    private ImageView imageView;
    List<CpuIntel> cpuIntels;
    ArrayList<String>  productline,generation,socket;
    private Integer Rate;
    ArrayList<Integer> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cpu_intel_filter, container, false);
        Xacnhanfilterbtn = view.findViewById(R.id.btnscrollXACNHAN);
        Xaclaplaifilterbtn = view.findViewById(R.id.btnscrollXACLAPLAI);

        imageView = view.findViewById(R.id.ivARROWTURNBACKALLFILTER);

        cpuIntels = new ArrayList<>();
        productline = new ArrayList<>();
        socket = new ArrayList<>();
        generation = new ArrayList<>();
        arrayList = new ArrayList<>();
        Rate = 0;

        scrollpricerangeMAX = view.findViewById(R.id.scrollpricerangeMAX);
        scrollpricerangeMIN = view.findViewById(R.id.scrollpricerangeMIN);

        tvfilterCOREI5 = view.findViewById(R.id.tvfilterCOREI5);
        tvfilterCOREI9 = view.findViewById(R.id.tvfilterCOREI9);
        tvfilterCOREI7 = view.findViewById(R.id.tvfilterCOREI7);
        tvfilterCOREI3 = view.findViewById(R.id.tvfilterCOREI3);

        tvfilterLGA1200 = view.findViewById(R.id.tvfilterLGA1200);
        tvfilterLGA1700 = view.findViewById(R.id.tvfilterLGA1700);

        tvfilterINTELTHEHE11 = view.findViewById(R.id.tvfilterINTELTHEHE11);
        tvfilterINTELTHEHE12 = view.findViewById(R.id.tvfilterINTELTHEHE12);
        tvfilterINTELTHEHE13 = view.findViewById(R.id.tvfilterINTELTHEHE13);
        tvfilterINTELTHEHE14 = view.findViewById(R.id.tvfilterINTELTHEHE14);

        tvscrollprice0to100k = view.findViewById(R.id.tvscrollprice0to100k);
        tvscrollprice100kto200k = view.findViewById(R.id.tvscrollprice100kto200k);

        tvscrollrate1STAR = view.findViewById(R.id.tvscrollrate1STAR);
        tvscrollrate2STAR = view.findViewById(R.id.tvscrollrate2STAR);
        tvscrollrate3STAR = view.findViewById(R.id.tvscrollrate3STAR);
        tvscrollrate4STAR = view.findViewById(R.id.tvscrollrate4STAR);
        tvscrollrate5STAR = view.findViewById(R.id.tvscrollrate5STAR);


        setProductLineTextViewClickListener(tvfilterCOREI5);
        setProductLineTextViewClickListener(tvfilterCOREI9);
        setProductLineTextViewClickListener(tvfilterCOREI7);
        setProductLineTextViewClickListener(tvfilterCOREI3);

        setSocketTextViewClickListener(tvfilterLGA1700);
        setSocketTextViewClickListener(tvfilterLGA1200);

        setPriceRangeTextViewClickListener(tvscrollprice0to100k);
        setPriceRangeTextViewClickListener(tvscrollprice100kto200k);

        setRateTextViewClickListener(tvscrollrate1STAR);
        setRateTextViewClickListener(tvscrollrate2STAR);
        setRateTextViewClickListener(tvscrollrate3STAR);
        setRateTextViewClickListener(tvscrollrate4STAR);
        setRateTextViewClickListener(tvscrollrate5STAR);


        setCpuGenerationTextViewClickListener(tvfilterINTELTHEHE11);
        setCpuGenerationTextViewClickListener(tvfilterINTELTHEHE12);
        setCpuGenerationTextViewClickListener(tvfilterINTELTHEHE13);
        setCpuGenerationTextViewClickListener(tvfilterINTELTHEHE14);



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
                resetProductLineTextViewColors();
                resetSocketTextViewColors();
                resetPriceRangeTextViewColors();
                resetRateTextViewColors();
                resetCpuGenerationTextViewColors();
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
        TaiListCpuIntel();
        return view;
    }
    private void setProductLineTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor()==Color.RED){
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(Color.parseColor("#D3D3D3"));
                DeleteProductLineFromTextView(textView);
                return;
            }

            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
            AddProductLineFromTextView(textView);
        });
    }

    private void setSocketTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor()==Color.RED){
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(Color.parseColor("#D3D3D3"));
                DeleteSocketFromTextView(textView);
                return;
            }

            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
            AddSocketFromTextView(textView);
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

            resetRateTextViewColors();


            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
            Rate = getRatingFromTextView(textView);
        });
    }


    private void setCpuGenerationTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor()==Color.RED){
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(Color.parseColor("#D3D3D3"));
                DeleteGenerationFromTextView(textView);
                return;
            }

            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
            AddGenerationFromTextView(textView);
        });
    }

    private void resetProductLineTextViewColors() {
        tvfilterCOREI3.setTextColor(Color.BLACK);
        tvfilterCOREI7.setTextColor(Color.BLACK);
        tvfilterCOREI9.setTextColor(Color.BLACK);
        tvfilterCOREI5.setTextColor(Color.BLACK);


        tvfilterCOREI3.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterCOREI7.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterCOREI9.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterCOREI5.setBackgroundColor(Color.parseColor("#D3D3D3"));

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
    }

    private void resetCpuGenerationTextViewColors() {
        tvfilterINTELTHEHE11.setTextColor(Color.BLACK);
        tvfilterINTELTHEHE12.setTextColor(Color.BLACK);
        tvfilterINTELTHEHE13.setTextColor(Color.BLACK);
        tvfilterINTELTHEHE14.setTextColor(Color.BLACK);


        tvfilterINTELTHEHE11.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterINTELTHEHE12.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterINTELTHEHE13.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterINTELTHEHE14.setBackgroundColor(Color.parseColor("#D3D3D3"));
    }

    private void resetSocketTextViewColors() {
        tvfilterLGA1200.setTextColor(Color.BLACK);
        tvfilterLGA1700.setTextColor(Color.BLACK);

        tvfilterLGA1200.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterLGA1700.setBackgroundColor(Color.parseColor("#D3D3D3"));

    }

    private String GetProductLineFromTextView (TextView textView){
        if (textView==tvfilterCOREI3) return "Core I3";
        if (textView==tvfilterCOREI5) return "Core I5";
        if (textView==tvfilterCOREI7) return "Core I7";
        if (textView==tvfilterCOREI9) return "Core I9";
        return null;
    }

    void AddProductLineFromTextView(TextView textView) {
        String productLineFromTextView = GetProductLineFromTextView(textView);
        if (productLineFromTextView != null) {
            productline.add(productLineFromTextView);
        }
    }

    void DeleteProductLineFromTextView(TextView textView) {
        String productLineFromTextView = GetProductLineFromTextView(textView);
        if (productLineFromTextView != null) {
            productline.remove(productLineFromTextView);
        }
    }

    private String GetGenerationFromTextView (TextView textView){
        if (textView==tvfilterINTELTHEHE11) return "Intel thế hệ 11";
        if (textView==tvfilterINTELTHEHE12) return "Intel thế hệ 12";
        if (textView==tvfilterINTELTHEHE13) return "Intel thế hệ 13";
        if (textView==tvfilterINTELTHEHE14) return "Intel thế hệ 14";
        return null;
    }

    void AddGenerationFromTextView(TextView textView) {
        String generations = GetGenerationFromTextView(textView);
        if (generations != null) {
            generation.add(generations);
        }
    }

    void DeleteGenerationFromTextView(TextView textView) {
        String generations = GetGenerationFromTextView(textView);
        if (generations != null) {
            generation.remove(generations);
        }
    }

    private String GetSocketFromTextView (TextView textView){
        if (textView==tvfilterLGA1200) return "LGA 1200";
        if (textView==tvfilterLGA1700) return "LGA 1700";
        return null;
    }

    void AddSocketFromTextView(TextView textView) {
        String sockets = GetSocketFromTextView(textView);
        if (sockets != null) {
            socket.add(sockets);
        }
    }

    void DeleteSocketFromTextView(TextView textView) {
        String sockets = GetSocketFromTextView(textView);
        if (sockets != null) {
            socket.remove(sockets);
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
        if (productline.isEmpty() && generation.isEmpty() && socket.isEmpty() && (scrollpricerangeMAX.getText().toString().isEmpty() || scrollpricerangeMIN.getText().toString().isEmpty()) && Rate == 0) {
            return;
        }

        if (!productline.isEmpty()) {
            int jack = productline.size();
            SortTheListFromPRODUCTLINE(jack);
        }

        if (!generation.isEmpty()) {
            int jack = generation.size();
            SortTheListFromGENERATION(jack);
        }

        if (!socket.isEmpty()) {
            int jack = socket.size();
            SortTheListFromSOCKET(jack);
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
        for (CpuIntel cpuIntel : cpuIntels) {
            int a = cpuIntel.getProductID();
            arrayList.add(a);
        }
        ((ProductList) getActivity()).UpdateListProductAdapter(arrayList);
        resetData();
    }

    private void SortTheListFromPRODUCTLINE(int listsize){
        Iterator<CpuIntel> iterator = cpuIntels.iterator();
        while (iterator.hasNext()) {
            CpuIntel cpuIntel = iterator.next();
            if (listsize == 1) {
                if (!cpuIntel.getProductLine().equals(productline.get(0))) {
                    iterator.remove();
                }
            } else if (listsize == 2) {
                if (!(cpuIntel.getProductLine().equals(productline.get(0)) || cpuIntel.getProductLine().equals(productline.get(1)))) {
                    iterator.remove();
                }
            } else if (listsize == 3) {
                if (!(cpuIntel.getProductLine().equals(productline.get(0)) || cpuIntel.getProductLine().equals(productline.get(1)) || cpuIntel.getProductLine().equals(productline.get(2)))) {
                    iterator.remove();
                }
            }

        }
    }
    private void SortTheListFromGENERATION(int listsize){
        Iterator<CpuIntel> iterator = cpuIntels.iterator();
        while (iterator.hasNext()) {
            CpuIntel cpuIntel = iterator.next();
            if (listsize == 1) {
                if (!cpuIntel.getGeneration().equals(generation.get(0))) {
                    iterator.remove();
                }
            } else if (listsize == 2) {
                if (!(cpuIntel.getGeneration().equals(generation.get(0)) || cpuIntel.getGeneration().equals(generation.get(1)))) {
                    iterator.remove();
                }
            } else if (listsize == 3) {
                if (!(cpuIntel.getGeneration().equals(generation.get(0)) || cpuIntel.getGeneration().equals(generation.get(1)) || cpuIntel.getGeneration().equals(generation.get(2)))) {
                    iterator.remove();
                }
            }
        }

    }

    private void SortTheListFromSOCKET(int listsize){
        Iterator<CpuIntel> iterator = cpuIntels.iterator();
        while (iterator.hasNext()) {
            CpuIntel cpuIntel = iterator.next();
            if (listsize == 1) {
                if (!cpuIntel.getSocket().equals(socket.get(0))) {
                    iterator.remove();
                }
            }
        }
    }

    private void SortTheListPRICERANGE(double min, double max) {
        Iterator<CpuIntel> iterator = cpuIntels.iterator();
        while (iterator.hasNext()) {
            CpuIntel cpuIntel = iterator.next();
            if (cpuIntel.getPrice() < min || cpuIntel.getPrice() > max) {
                iterator.remove();
            }
        }
    }

    private void GetTheListFromRate(int rate) {
        Iterator<CpuIntel> iterator = cpuIntels.iterator();
        while (iterator.hasNext()) {
            CpuIntel cpuIntel = iterator.next();
            if (cpuIntel.getRate() < rate) {
                iterator.remove();
            }
        }
    }

    private void resetData() {
        productline.clear();
        generation.clear();
        socket.clear();
        cpuIntels.clear();
        Rate = 0;
        scrollpricerangeMIN.setText("");
        scrollpricerangeMAX.setText("");
        resetProductLineTextViewColors();
        resetSocketTextViewColors();
        resetPriceRangeTextViewColors();
        resetRateTextViewColors();
        resetCpuGenerationTextViewColors();
        TaiListCpuIntel();
    }

    private void TaiListCpuIntel(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("CpuIntel");
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<CpuIntel> cpuIntels1 = new ArrayList<>();
                    for (DataSnapshot ramSnapshot : dataSnapshot.getChildren()) {
                        int productID = ramSnapshot.child("ProductID").getValue(int.class);
                        String ProductLine = ramSnapshot.child("ProductLine").getValue(String.class);
                        String Generation = ramSnapshot.child("Generation").getValue(String.class);
                        double Price = ramSnapshot.child("Price").getValue(double.class);
                        String Socket = ramSnapshot.child("Socket").getValue(String.class);
                        double Rate = ramSnapshot.child("Rate").getValue(double.class);
                        CpuIntel cpuIntel = new CpuIntel(productID, ProductLine, Generation, Socket, Price, Rate);
                        cpuIntels1.add(cpuIntel);
                    }
                    cpuIntels.addAll(cpuIntels1);
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
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


public class CpuAMDFilterFragment extends Fragment {
    private TextView tvfilterRYZEN3, tvfilterRYZEN5, tvfilterRYZEN7, tvfilterRYZEN9;
    private TextView tvfilterAM5, tvfilterAM4;
    private TextView tvfilterAMDTHEHETHU3, tvfilterAMDTHEHETHU4, tvfilterAMDTHEHETHU5, tvfilterAMDTHEHETHU7, tvfilterAMDTHEHETHU9;
    private TextView tvscrollprice0to100k, tvscrollprice100kto200k;
    private TextView tvscrollrate1STAR, tvscrollrate2STAR, tvscrollrate3STAR, tvscrollrate4STAR, tvscrollrate5STAR;
    private EditText scrollpricerangeMAX, scrollpricerangeMIN;
    private Button Xacnhanfilterbtn,Xaclaplaifilterbtn;
    private ImageView imageView;
    List<CpuIntel> cpuIntelslist;
    ArrayList<String> productlinelist,generationlist,socketlist;
    private Integer Rate;
    ArrayList<Integer> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cpu_amd_filter, container, false);

        cpuIntelslist = new ArrayList<>();
        productlinelist = new ArrayList<>();
        generationlist = new ArrayList<>();
        socketlist = new ArrayList<>();
        arrayList = new ArrayList<>();
        Rate = 0;
        Xacnhanfilterbtn = view.findViewById(R.id.btnscrollXACNHAN);
        Xaclaplaifilterbtn = view.findViewById(R.id.btnscrollXACLAPLAI);

        imageView = view.findViewById(R.id.ivARROWTURNBACKALLFILTER);

        scrollpricerangeMAX = view.findViewById(R.id.scrollpricerangeMAX);
        scrollpricerangeMIN = view.findViewById(R.id.scrollpricerangeMIN);

        tvfilterRYZEN5 = view.findViewById(R.id.tvfilterRYZEN5);
        tvfilterRYZEN9 = view.findViewById(R.id.tvfilterRYZEN9);
        tvfilterRYZEN7 = view.findViewById(R.id.tvfilterRYZEN7);
        tvfilterRYZEN3 = view.findViewById(R.id.tvfilterRYZEN3);

        tvfilterAM4 = view.findViewById(R.id.tvfilterAM4);
        tvfilterAM5 = view.findViewById(R.id.tvfilterAM5);

        tvfilterAMDTHEHETHU3 = view.findViewById(R.id.tvfilterAMDTHEHETHU3);
        tvfilterAMDTHEHETHU4 = view.findViewById(R.id.tvfilterAMDTHEHETHU4);
        tvfilterAMDTHEHETHU5 = view.findViewById(R.id.tvfilterAMDTHEHETHU5);
        tvfilterAMDTHEHETHU7 = view.findViewById(R.id.tvfilterAMDTHEHETHU7);
        tvfilterAMDTHEHETHU9 = view.findViewById(R.id.tvfilterAMDTHEHETHU9);

        tvscrollprice0to100k = view.findViewById(R.id.tvscrollprice0to100k);
        tvscrollprice100kto200k = view.findViewById(R.id.tvscrollprice100kto200k);

        tvscrollrate1STAR = view.findViewById(R.id.tvscrollrate1STAR);
        tvscrollrate2STAR = view.findViewById(R.id.tvscrollrate2STAR);
        tvscrollrate3STAR = view.findViewById(R.id.tvscrollrate3STAR);
        tvscrollrate4STAR = view.findViewById(R.id.tvscrollrate4STAR);
        tvscrollrate5STAR = view.findViewById(R.id.tvscrollrate5STAR);

        setProductLineTextViewClickListener(tvfilterRYZEN5);
        setProductLineTextViewClickListener(tvfilterRYZEN9);
        setProductLineTextViewClickListener(tvfilterRYZEN7);
        setProductLineTextViewClickListener(tvfilterRYZEN3);

        setSocketTextViewClickListener(tvfilterAM5);
        setSocketTextViewClickListener(tvfilterAM4);

        setPriceRangeTextViewClickListener(tvscrollprice0to100k);
        setPriceRangeTextViewClickListener(tvscrollprice100kto200k);

        setRateTextViewClickListener(tvscrollrate1STAR);
        setRateTextViewClickListener(tvscrollrate2STAR);
        setRateTextViewClickListener(tvscrollrate3STAR);
        setRateTextViewClickListener(tvscrollrate4STAR);
        setRateTextViewClickListener(tvscrollrate5STAR);

        setCpuGenerationTextViewClickListener(tvfilterAMDTHEHETHU3);
        setCpuGenerationTextViewClickListener(tvfilterAMDTHEHETHU4);
        setCpuGenerationTextViewClickListener(tvfilterAMDTHEHETHU5);
        setCpuGenerationTextViewClickListener(tvfilterAMDTHEHETHU7);
        setCpuGenerationTextViewClickListener(tvfilterAMDTHEHETHU9);


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
        TaiListCpuAMD();
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
            // Đặt màu đỏ cho TextView được nhấn
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
            // Đặt màu đỏ cho TextView được nhấn
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
            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
            AddGenerationFromTextView(textView);
        });
    }

    private void resetProductLineTextViewColors() {
        tvfilterRYZEN3.setTextColor(Color.BLACK);
        tvfilterRYZEN7.setTextColor(Color.BLACK);
        tvfilterRYZEN9.setTextColor(Color.BLACK);
        tvfilterRYZEN5.setTextColor(Color.BLACK);


        tvfilterRYZEN3.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterRYZEN7.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterRYZEN9.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterRYZEN5.setBackgroundColor(Color.parseColor("#D3D3D3"));

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
        tvfilterAMDTHEHETHU3.setTextColor(Color.BLACK);
        tvfilterAMDTHEHETHU4.setTextColor(Color.BLACK);
        tvfilterAMDTHEHETHU5.setTextColor(Color.BLACK);
        tvfilterAMDTHEHETHU7.setTextColor(Color.BLACK);
        tvfilterAMDTHEHETHU9.setTextColor(Color.BLACK);


        tvfilterAMDTHEHETHU3.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterAMDTHEHETHU4.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterAMDTHEHETHU5.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterAMDTHEHETHU7.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterAMDTHEHETHU9.setBackgroundColor(Color.parseColor("#D3D3D3"));
    }

    private void resetSocketTextViewColors() {
        tvfilterAM4.setTextColor(Color.BLACK);
        tvfilterAM5.setTextColor(Color.BLACK);

        tvfilterAM4.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterAM5.setBackgroundColor(Color.parseColor("#D3D3D3"));

    }

    private String GetProductLineFromTextView (TextView textView){
        if (textView==tvfilterRYZEN3) return "Ryzen 3";
        if (textView==tvfilterRYZEN5) return "Ryzen 5";
        if (textView==tvfilterRYZEN7) return "Ryzen 7";
        if (textView==tvfilterRYZEN9) return "Ryzen 9";
        return null;
    }

    void AddProductLineFromTextView(TextView textView) {
        String productLineFromTextView = GetProductLineFromTextView(textView);
        if (productLineFromTextView != null) {
            productlinelist.add(productLineFromTextView);
        }
    }

    void DeleteProductLineFromTextView(TextView textView) {
        String productLineFromTextView = GetProductLineFromTextView(textView);
        if (productLineFromTextView != null) {
            productlinelist.remove(productLineFromTextView);
        }
    }

    private String GetGenerationFromTextView (TextView textView){
        if (textView==tvfilterAMDTHEHETHU3) return "AMD Ryzen thế hệ thứ 3";
        if (textView==tvfilterAMDTHEHETHU4) return "AMD Ryzen thế hệ thứ 4";
        if (textView==tvfilterAMDTHEHETHU5) return "AMD Ryzen thế hệ thứ 5";
        if (textView==tvfilterAMDTHEHETHU7) return "AMD Ryzen thế hệ thứ 7";
        if (textView==tvfilterAMDTHEHETHU9) return "AMD Ryzen thế hệ thứ 9";
        return null;
    }

    void AddGenerationFromTextView(TextView textView) {
        String generations = GetGenerationFromTextView(textView);
        if (generations != null) {
            generationlist.add(generations);
        }
    }

    void DeleteGenerationFromTextView(TextView textView) {
        String generations = GetGenerationFromTextView(textView);
        if (generations != null) {
            generationlist.remove(generations);
        }
    }

    private String GetSocketFromTextView (TextView textView){
        if (textView==tvfilterAM4) return "AM4";
        if (textView==tvfilterAM5) return "AM5";
        return null;
    }

    void AddSocketFromTextView(TextView textView) {
        String sockets = GetSocketFromTextView(textView);
        if (sockets != null) {
            socketlist.add(sockets);
        }
    }
    void DeleteSocketFromTextView(TextView textView) {
        String sockets = GetSocketFromTextView(textView);
        if (sockets != null) {
            socketlist.remove(sockets);
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
        if (productlinelist.isEmpty() && generationlist.isEmpty() && socketlist.isEmpty() && (scrollpricerangeMAX.getText().toString().isEmpty() || scrollpricerangeMIN.getText().toString().isEmpty()) && Rate == 0) {
            return;
        }

        if (!productlinelist.isEmpty()) {
            int jack = productlinelist.size();
            SortTheListFromPRODUCTLINE(jack);
        }

        if (!generationlist.isEmpty()) {
            int jack = generationlist.size();
            SortTheListFromGENERATION(jack);
        }

        if (!socketlist.isEmpty()) {
            int jack = socketlist.size();
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
        for (CpuIntel CA : cpuIntelslist) {
            int a = CA.getProductID();
            arrayList.add(a);
        }
        ((ProductList) getActivity()).UpdateListProductAdapter(arrayList);
        resetData();
    }

    private void SortTheListFromPRODUCTLINE(int listsize){
        Iterator<CpuIntel> iterator = cpuIntelslist.iterator();
        while (iterator.hasNext()) {
            CpuIntel cpuIntel = iterator.next();
            if (listsize == 1) {
                if (!cpuIntel.getProductLine().equals(productlinelist.get(0))) {
                    iterator.remove();
                }
            } else if (listsize == 2) {
                if (!(cpuIntel.getProductLine().equals(productlinelist.get(0)) || cpuIntel.getProductLine().equals(productlinelist.get(1)))) {
                    iterator.remove();
                }
            } else if (listsize == 3) {
                if (!(cpuIntel.getProductLine().equals(productlinelist.get(0)) || cpuIntel.getProductLine().equals(productlinelist.get(1)) || cpuIntel.getProductLine().equals(productlinelist.get(2)))) {
                    iterator.remove();
                }
            }

        }
    }

    private void SortTheListFromGENERATION(int listsize){
        Iterator<CpuIntel> iterator = cpuIntelslist.iterator();
        while (iterator.hasNext()) {
            CpuIntel cpuIntel = iterator.next();
            if (listsize == 1) {
                if (!cpuIntel.getGeneration().equals(generationlist.get(0))) {
                    iterator.remove();
                }
            } else if (listsize == 2) {
                if (!(cpuIntel.getGeneration().equals(generationlist.get(0)) || cpuIntel.getGeneration().equals(generationlist.get(1)))) {
                    iterator.remove();
                }
            } else if (listsize == 3) {
                if (!(cpuIntel.getGeneration().equals(generationlist.get(0)) || cpuIntel.getGeneration().equals(generationlist.get(1)) || cpuIntel.getGeneration().equals(generationlist.get(2)))) {
                    iterator.remove();
                }
            }else if (listsize == 4) {
                if (!(cpuIntel.getGeneration().equals(generationlist.get(0)) || cpuIntel.getGeneration().equals(generationlist.get(1)) || cpuIntel.getGeneration().equals(generationlist.get(2))||cpuIntel.getGeneration().equals(generationlist.get(3)))) {
                    iterator.remove();
                }
            }
        }

    }

    private void SortTheListFromSOCKET(int listsize){
        Iterator<CpuIntel> iterator = cpuIntelslist.iterator();
        while (iterator.hasNext()) {
            CpuIntel cpuIntel = iterator.next();
            if (listsize == 1) {
                if (!cpuIntel.getSocket().equals(socketlist.get(0))) {
                    iterator.remove();
                }
            }
        }
    }

    private void SortTheListPRICERANGE(double min, double max) {
        Iterator<CpuIntel> iterator = cpuIntelslist.iterator();
        while (iterator.hasNext()) {
            CpuIntel cpuIntel = iterator.next();
            if (cpuIntel.getPrice() < min || cpuIntel.getPrice() > max) {
                iterator.remove();
            }
        }
    }

    private void GetTheListFromRate(int rate) {
        Iterator<CpuIntel> iterator = cpuIntelslist.iterator();
        while (iterator.hasNext()) {
            CpuIntel cpuIntel = iterator.next();
            if (cpuIntel.getRate() < rate) {
                iterator.remove();
            }
        }
    }

    private void resetData() {
        productlinelist.clear();
        generationlist.clear();
        socketlist.clear();
        cpuIntelslist.clear();
        Rate = 0;
        scrollpricerangeMIN.setText("");
        scrollpricerangeMAX.setText("");
        resetProductLineTextViewColors();
        resetSocketTextViewColors();
        resetPriceRangeTextViewColors();
        resetRateTextViewColors();
        resetCpuGenerationTextViewColors();
        TaiListCpuAMD();
    }
    private void TaiListCpuAMD(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("CpuAMD");
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
                    cpuIntelslist.addAll(cpuIntels1);
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
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


public class VgaNvidiaFilterFragment extends Fragment {
    private TextView tvfilter8GB, tvfilter2GB, tvfilter6GB, tvfilter4GB, tvfilter12GB, tvfilter16GB, tvfilter24GB;
    private TextView tvfilterNVIDIARTX3000SERIES, tvfilterNVIDIARTX2000SERIES, tvfilterNVIDIARTX4000SERIES, tvfilterNVIDIARTX7000SERIES, tvfilterNVIDIAGTX, tvfilterNVIDIAGT;
    private TextView tvfilterGDDR6, tvfilterGDDR6X, tvfilterGDDR5;
    private TextView tvscrollprice0to100k, tvscrollprice100kto200k;
    private TextView tvscrollrate1STAR, tvscrollrate2STAR, tvscrollrate3STAR, tvscrollrate4STAR, tvscrollrate5STAR;
    private EditText scrollpricerangeMAX, scrollpricerangeMIN;
    private Button Xacnhanfilterbtn,Xaclaplaifilterbtn;
    private ImageView imageView;
    List<VgaNvidia> vgaNvidias,vgaNvidias2;
    ArrayList<String> productlinelist,capacitylist,memorytypelist;
    private Integer Rate;
    ArrayList<Integer> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.vga_nvidia_filter, container, false);

        vgaNvidias = new ArrayList<>();
        productlinelist = new ArrayList<>();
        capacitylist = new ArrayList<>();
        memorytypelist = new ArrayList<>();
        arrayList = new ArrayList<>();
        vgaNvidias2 = new ArrayList<>();
        Rate = 0;

        Xacnhanfilterbtn = view.findViewById(R.id.btnscrollXACNHAN);
        Xaclaplaifilterbtn = view.findViewById(R.id.btnscrollXACLAPLAI);

        imageView = view.findViewById(R.id.ivARROWTURNBACKALLFILTER);

        scrollpricerangeMAX = view.findViewById(R.id.scrollpricerangeMAX);
        scrollpricerangeMIN = view.findViewById(R.id.scrollpricerangeMIN);

        tvfilter2GB = view.findViewById(R.id.tvfilter2GB);
        tvfilter4GB = view.findViewById(R.id.tvfilter4GB);
        tvfilter6GB = view.findViewById(R.id.tvfilter6GB);
        tvfilter8GB = view.findViewById(R.id.tvfilter8GB);
        tvfilter12GB = view.findViewById(R.id.tvfilter12GB);
        tvfilter16GB = view.findViewById(R.id.tvfilter16GB);
        tvfilter24GB = view.findViewById(R.id.tvfilter24GB);

        tvfilterNVIDIARTX2000SERIES = view.findViewById(R.id.tvfilterNVIDIARTX2000SERIES);
        tvfilterNVIDIARTX3000SERIES = view.findViewById(R.id.tvfilterNVIDIARTX3000SERIES);
        tvfilterNVIDIARTX4000SERIES = view.findViewById(R.id.tvfilterNVIDIARTX4000SERIES);
        tvfilterNVIDIARTX7000SERIES = view.findViewById(R.id.tvfilterNVIDIARTX7000SERIES);
        tvfilterNVIDIAGTX = view.findViewById(R.id.tvfilterNVIDIAGTX);
        tvfilterNVIDIAGT = view.findViewById(R.id.tvfilterNVIDIAGT);

        tvfilterGDDR6 = view.findViewById(R.id.tvfilterGDDR6);
        tvfilterGDDR6X = view.findViewById(R.id.tvfilterGDDR6X);
        tvfilterGDDR5 = view.findViewById(R.id.tvfilterGDDR5);


        tvscrollprice0to100k = view.findViewById(R.id.tvscrollprice0to100k);
        tvscrollprice100kto200k = view.findViewById(R.id.tvscrollprice100kto200k);

        tvscrollrate1STAR = view.findViewById(R.id.tvscrollrate1STAR);
        tvscrollrate2STAR = view.findViewById(R.id.tvscrollrate2STAR);
        tvscrollrate3STAR = view.findViewById(R.id.tvscrollrate3STAR);
        tvscrollrate4STAR = view.findViewById(R.id.tvscrollrate4STAR);
        tvscrollrate5STAR = view.findViewById(R.id.tvscrollrate5STAR);

        setCapacityTextViewClickListener(tvfilter2GB);
        setCapacityTextViewClickListener(tvfilter4GB);
        setCapacityTextViewClickListener(tvfilter6GB);
        setCapacityTextViewClickListener(tvfilter8GB);
        setCapacityTextViewClickListener(tvfilter12GB);
        setCapacityTextViewClickListener(tvfilter16GB);
        setCapacityTextViewClickListener(tvfilter24GB);

        setVGALineTextViewClickListener(tvfilterNVIDIARTX3000SERIES);
        setVGALineTextViewClickListener(tvfilterNVIDIARTX2000SERIES);
        setVGALineTextViewClickListener(tvfilterNVIDIARTX4000SERIES);
        setVGALineTextViewClickListener(tvfilterNVIDIARTX7000SERIES);
        setVGALineTextViewClickListener(tvfilterNVIDIAGTX);
        setVGALineTextViewClickListener(tvfilterNVIDIAGT);

        setPriceRangeTextViewClickListener(tvscrollprice0to100k);
        setPriceRangeTextViewClickListener(tvscrollprice100kto200k);

        setRateTextViewClickListener(tvscrollrate1STAR);
        setRateTextViewClickListener(tvscrollrate2STAR);
        setRateTextViewClickListener(tvscrollrate3STAR);
        setRateTextViewClickListener(tvscrollrate4STAR);
        setRateTextViewClickListener(tvscrollrate5STAR);

        setMemoryTypeTextViewClickListener(tvfilterGDDR6);
        setMemoryTypeTextViewClickListener(tvfilterGDDR6X);
        setMemoryTypeTextViewClickListener(tvfilterGDDR5);




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
                resetMemoryTypeTextViewColors();
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
        TaiListVgaNvidial();
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
                scrollpricerangeMAX.setText("15000000");
                resetPriceRangeTextViewColors();
                textView.setTextColor(Color.RED);
                textView.setBackgroundResource(R.drawable.border_textview_red);
            } else {
                scrollpricerangeMIN.setText("15000000");
                scrollpricerangeMAX.setText("30000000");
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

    private void setMemoryTypeTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor()==Color.RED){
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(Color.parseColor("#D3D3D3"));
                DeleteMemoryTypeFromTextView(textView);
                return;
            }
            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
            AddMemoryTypeFromTextView(textView);
        });
    }

    private void resetCapacityTextViewColors() {
        tvfilter8GB.setTextColor(Color.BLACK);
        tvfilter6GB.setTextColor(Color.BLACK);
        tvfilter4GB.setTextColor(Color.BLACK);
        tvfilter2GB.setTextColor(Color.BLACK);
        tvfilter12GB.setTextColor(Color.BLACK);
        tvfilter16GB.setTextColor(Color.BLACK);
        tvfilter24GB.setTextColor(Color.BLACK);


        tvfilter8GB.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilter6GB.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilter4GB.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilter2GB.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilter12GB.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilter16GB.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilter24GB.setBackgroundColor(Color.parseColor("#D3D3D3"));

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

    private void resetMemoryTypeTextViewColors() {
        tvfilterGDDR6.setTextColor(Color.BLACK);
        tvfilterGDDR6X.setTextColor(Color.BLACK);
        tvfilterGDDR5.setTextColor(Color.BLACK);



        tvfilterGDDR6.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterGDDR6X.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterGDDR5.setBackgroundColor(Color.parseColor("#D3D3D3"));

    }

    private void resetVGALineTextViewColors() {
        tvfilterNVIDIARTX2000SERIES.setTextColor(Color.BLACK);
        tvfilterNVIDIARTX3000SERIES.setTextColor(Color.BLACK);
        tvfilterNVIDIARTX4000SERIES.setTextColor(Color.BLACK);
        tvfilterNVIDIARTX7000SERIES.setTextColor(Color.BLACK);
        tvfilterNVIDIAGTX.setTextColor(Color.BLACK);
        tvfilterNVIDIAGT.setTextColor(Color.BLACK);

        tvfilterNVIDIARTX2000SERIES.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterNVIDIARTX3000SERIES.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterNVIDIARTX4000SERIES.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterNVIDIARTX7000SERIES.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterNVIDIAGTX.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterNVIDIAGT.setBackgroundColor(Color.parseColor("#D3D3D3"));

    }

    private String GetCapicityFromTextView(TextView textView){
        if (textView==tvfilter2GB) return "2 GB";
        if (textView==tvfilter4GB) return "4 GB";
        if (textView==tvfilter6GB) return "6 GB";
        if (textView==tvfilter8GB) return "8 GB";
        if (textView==tvfilter12GB) return "12 GB";
        if (textView==tvfilter16GB) return "16 GB";
        if (textView==tvfilter24GB) return "24 GB";
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

    private String GetMemoryTypeFromTextView(TextView textView){
        if (textView==tvfilterGDDR5) return "GDDR5";
        if (textView==tvfilterGDDR6) return "GDDR6";
        if (textView==tvfilterGDDR6X) return "GDDR6X";
        return null;
    }
    private void AddMemoryTypeFromTextView(TextView textView){
        String memo = GetMemoryTypeFromTextView(textView);
        if (memo != null) {
            memorytypelist.add(memo);
        }
    }

    private void DeleteMemoryTypeFromTextView(TextView textView){
        String memo = GetMemoryTypeFromTextView(textView);
        if (memo != null) {
            memorytypelist.remove(memo);
        }
    }

    private String GetProductLineFromTextView(TextView textView){
        if (textView==tvfilterNVIDIARTX2000SERIES) return "NVIDIA RTX 2000 Series";
        if (textView==tvfilterNVIDIARTX3000SERIES) return "NVIDIA RTX 3000 Series";
        if (textView==tvfilterNVIDIARTX4000SERIES) return "NVIDIA RTX 4000 Series";
        if (textView==tvfilterNVIDIARTX7000SERIES) return "NVIDIA RTX 7000 Series";
        if (textView==tvfilterNVIDIAGTX) return "NVIDIA GTX";
        if (textView==tvfilterNVIDIAGT) return "NVIDIA GT";
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
        if (productlinelist.isEmpty() && capacitylist.isEmpty() && memorytypelist.isEmpty() && (scrollpricerangeMAX.getText().toString().isEmpty() || scrollpricerangeMIN.getText().toString().isEmpty()) && Rate == 0) {
            return;
        }

        if (!capacitylist.isEmpty()) {
            int jack = capacitylist.size();
            SortTheListFromCAPICITY(jack);
        }

        if (!memorytypelist.isEmpty()) {
            int jack = memorytypelist.size();
            SortTheListFromMEMORYTYPE(jack);
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
        for (VgaNvidia vga : vgaNvidias) {
            int a = vga.getProductID();
            arrayList.add(a);
        }
        ((ProductList) getActivity()).UpdateListProductAdapter(arrayList);
        resetData();
    }
    private void SortTheListFromCAPICITY(int listsize) {
        Iterator<VgaNvidia> iterator = vgaNvidias.iterator();
        while (iterator.hasNext()) {
            VgaNvidia vgaNvidia = iterator.next();
            boolean isValid = false;
            for (int i = 0; i < listsize; i++) {
                if (vgaNvidia.getCapacity().equals(capacitylist.get(i))) {
                    isValid = true;
                    break;
                }
            }
            if (!isValid) {
                iterator.remove();
            }
        }
    }


    private void SortTheListFromMEMORYTYPE(int listsize) {
        Iterator<VgaNvidia> iterator = vgaNvidias.iterator();
        while (iterator.hasNext()) {
            VgaNvidia vgaNvidia = iterator.next();
            boolean isValid = false;
            for (int i = 0; i < listsize; i++) {
                if (vgaNvidia.getMemoryType().equals(memorytypelist.get(i))) {
                    isValid = true;
                    break;
                }
            }
            if (!isValid) {
                iterator.remove();
            }
        }
    }

    private void SortTheListFromPRODUCTLINE(int listsize) {
        Iterator<VgaNvidia> iterator = vgaNvidias.iterator();
        while (iterator.hasNext()) {
            VgaNvidia vgaNvidia = iterator.next();
            boolean isValid = false;
            for (int i = 0; i < listsize; i++) {
                if (vgaNvidia.getProductLine().equals(productlinelist.get(i))) {
                    isValid = true;
                    break;
                }
            }
            if (!isValid) {
                iterator.remove();
            }
        }
    }


    private void SortTheListPRICERANGE(double min, double max) {
        Iterator<VgaNvidia> iterator = vgaNvidias.iterator();
        while (iterator.hasNext()) {
            VgaNvidia vgaNvidia = iterator.next();
            if (vgaNvidia.getPrice() < min || vgaNvidia.getPrice() > max) {
                iterator.remove();
            }
        }
    }

    private void GetTheListFromRate(int rate) {
        Iterator<VgaNvidia> iterator = vgaNvidias.iterator();
        while (iterator.hasNext()) {
            VgaNvidia vgaNvidia = iterator.next();
            if (vgaNvidia.getRate() < rate) {
                iterator.remove();
            }
        }
    }
    private void resetData() {
        productlinelist.clear();
        memorytypelist.clear();
        capacitylist.clear();
        vgaNvidias.clear();
        Rate = 0;
        scrollpricerangeMIN.setText("");
        scrollpricerangeMAX.setText("");
        resetCapacityTextViewColors();
        resetVGALineTextViewColors();
        resetPriceRangeTextViewColors();
        resetRateTextViewColors();
        resetMemoryTypeTextViewColors();
        vgaNvidias.addAll(vgaNvidias2);
    }

    private void TaiListVgaNvidial(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("VgaNvidia");
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<VgaNvidia> vgaNvidias1 = new ArrayList<>();
                    for (DataSnapshot ramSnapshot : dataSnapshot.getChildren()) {
                        int productID = ramSnapshot.child("ProductID").getValue(int.class);
                        String Capacity = ramSnapshot.child("Capacity").getValue(String.class);
                        String Memory = ramSnapshot.child("MemoryType").getValue(String.class);
                        double Price = ramSnapshot.child("Price").getValue(double.class);
                        String Productl = ramSnapshot.child("ProductLine").getValue(String.class);
                        double Rate = ramSnapshot.child("Rate").getValue(double.class);
                        VgaNvidia vgaNvidia = new VgaNvidia(productID, Capacity, Memory, Productl, Price, Rate);
                        vgaNvidias1.add(vgaNvidia);
                    }
                    vgaNvidias2.addAll(vgaNvidias1);
                    vgaNvidias.addAll(vgaNvidias1);
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
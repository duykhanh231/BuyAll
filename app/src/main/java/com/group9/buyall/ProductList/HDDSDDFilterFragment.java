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


public class HDDSDDFilterFragment extends Fragment {
    private TextView tvfilter240GB, tvfilter480GB, tvfilter960GB, tvfilter250GB, tvfilter500GB, tvfilter1TB, tvfilter256GB, tvfilter512GB, tvfilter2TB, tvfilter4TB;
    private TextView tvfilterSSD, tvfilterHDD;
    private TextView tvscrollprice0to100k, tvscrollprice100kto200k;
    private TextView tvscrollrate1STAR, tvscrollrate2STAR, tvscrollrate3STAR, tvscrollrate4STAR, tvscrollrate5STAR;
    private EditText scrollpricerangeMAX, scrollpricerangeMIN;
    private Button Xacnhanfilterbtn,Xaclaplaifilterbtn;
    private ImageView imageView;
    private List<Disk> disks,disks2;
    private ArrayList<String> TypeofDisk,Capacity;
    private Integer Rate;
    private ArrayList<Integer> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hdd_ssd_filter, container, false);
        Xacnhanfilterbtn = view.findViewById(R.id.btnscrollXACNHAN);
        Xaclaplaifilterbtn = view.findViewById(R.id.btnscrollXACLAPLAI);
        disks = new ArrayList<>();
        TypeofDisk = new ArrayList<>();
        Capacity = new ArrayList<>();
        arrayList = new ArrayList<>();
        disks2 = new ArrayList<>();
        Rate = 0;
        imageView = view.findViewById(R.id.ivARROWTURNBACKALLFILTER);

        scrollpricerangeMAX = view.findViewById(R.id.scrollpricerangeMAX);
        scrollpricerangeMIN = view.findViewById(R.id.scrollpricerangeMIN);

        tvfilter240GB = view.findViewById(R.id.tvfilter240GB);
        tvfilter480GB = view.findViewById(R.id.tvfilter480GB);
        tvfilter960GB = view.findViewById(R.id.tvfilter960GB);
        tvfilter250GB = view.findViewById(R.id.tvfilter250GB);
        tvfilter500GB = view.findViewById(R.id.tvfilter500GB);
        tvfilter1TB = view.findViewById(R.id.tvfilter1TB);
        tvfilter256GB = view.findViewById(R.id.tvfilter256GB);
        tvfilter512GB = view.findViewById(R.id.tvfilter512GB);
        tvfilter2TB = view.findViewById(R.id.tvfilter2TB);
        tvfilter4TB = view.findViewById(R.id.tvfilter4TB);

        tvfilterHDD = view.findViewById(R.id.tvfilterHDD);
        tvfilterSSD = view.findViewById(R.id.tvfilterSSD);

        tvscrollprice0to100k = view.findViewById(R.id.tvscrollprice0to100k);
        tvscrollprice100kto200k = view.findViewById(R.id.tvscrollprice100kto200k);

        tvscrollrate1STAR = view.findViewById(R.id.tvscrollrate1STAR);
        tvscrollrate2STAR = view.findViewById(R.id.tvscrollrate2STAR);
        tvscrollrate3STAR = view.findViewById(R.id.tvscrollrate3STAR);
        tvscrollrate4STAR = view.findViewById(R.id.tvscrollrate4STAR);
        tvscrollrate5STAR = view.findViewById(R.id.tvscrollrate5STAR);

        setCapacityTextViewClickListener(tvfilter240GB);
        setCapacityTextViewClickListener(tvfilter480GB);
        setCapacityTextViewClickListener(tvfilter960GB);
        setCapacityTextViewClickListener(tvfilter250GB);
        setCapacityTextViewClickListener(tvfilter500GB);
        setCapacityTextViewClickListener(tvfilter1TB);
        setCapacityTextViewClickListener(tvfilter256GB);
        setCapacityTextViewClickListener(tvfilter512GB);
        setCapacityTextViewClickListener(tvfilter2TB);
        setCapacityTextViewClickListener(tvfilter4TB);


        setHardDriveTypeTextViewClickListener(tvfilterSSD);
        setHardDriveTypeTextViewClickListener(tvfilterHDD);

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
                resetHardDriveTypeTextViewColors();
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
        TaiListDisk();
        return view;
    }

    private void setCapacityTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor()==Color.RED){
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(Color.parseColor("#D3D3D3"));
                DeleteCapacityFromTextView(textView);
                return;
            }
            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
            AddCapacityFromTextView(textView);
        });
    }

    private void setHardDriveTypeTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor()==Color.RED){
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(Color.parseColor("#D3D3D3"));
                DeleteTypeOfDiskFromTextView(textView);
                return;
            }
            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
            AddTypeOfDiskFromTextView(textView);
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
                scrollpricerangeMAX.setText("5000000");
                resetPriceRangeTextViewColors();
                textView.setTextColor(Color.RED);
                textView.setBackgroundResource(R.drawable.border_textview_red);
            } else {
                scrollpricerangeMIN.setText("5000000");
                scrollpricerangeMAX.setText("10000000");
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
                Rate=0;
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
        tvfilter240GB.setTextColor(Color.BLACK);
        tvfilter480GB.setTextColor(Color.BLACK);
        tvfilter960GB.setTextColor(Color.BLACK);
        tvfilter250GB.setTextColor(Color.BLACK);
        tvfilter500GB.setTextColor(Color.BLACK);
        tvfilter1TB.setTextColor(Color.BLACK);
        tvfilter256GB.setTextColor(Color.BLACK);
        tvfilter512GB.setTextColor(Color.BLACK);
        tvfilter2TB.setTextColor(Color.BLACK);
        tvfilter4TB.setTextColor(Color.BLACK);


        tvfilter240GB.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilter480GB.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilter960GB.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilter250GB.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilter500GB.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilter1TB.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilter256GB.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilter512GB.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilter2TB.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilter4TB.setBackgroundColor(Color.parseColor("#D3D3D3"));
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

    private void resetHardDriveTypeTextViewColors() {
        tvfilterHDD.setTextColor(Color.BLACK);
        tvfilterSSD.setTextColor(Color.BLACK);

        tvfilterHDD.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterSSD.setBackgroundColor(Color.parseColor("#D3D3D3"));

    }

    private String getTypeOfDiskFromTextView(TextView textView){
        if (textView==tvfilterSSD) return "SSD";
        if (textView==tvfilterHDD) return "HDD";
        return null;
    }

    void AddTypeOfDiskFromTextView(TextView textView){
        String typeofdisk = getTypeOfDiskFromTextView(textView);
        if (typeofdisk != null) {
            TypeofDisk.add(typeofdisk);
        }
    }

    void DeleteTypeOfDiskFromTextView(TextView textView){
        String typeofdisk = getTypeOfDiskFromTextView(textView);
        if (typeofdisk != null) {
            TypeofDisk.remove(typeofdisk);
        }
    }

    private String getCapacityFromTextView(TextView textView){
        if (textView==tvfilter240GB) return "240 GB";
        if (textView==tvfilter480GB) return "480 GB";
        if (textView==tvfilter960GB) return "960 GB";
        if (textView==tvfilter250GB) return "250 GB";
        if (textView==tvfilter500GB) return "500 GB";
        if (textView==tvfilter1TB) return "1 TB";
        if (textView==tvfilter256GB) return "256 GB";
        if (textView==tvfilter512GB) return "512 GB";
        if (textView==tvfilter2TB) return "2 TB";
        if (textView==tvfilter4TB) return "4 GB";
        return null;
    }

    void AddCapacityFromTextView(TextView textView){
        String capacity = getCapacityFromTextView(textView);
        if (capacity != null) {
            Capacity.add(capacity);
        }
    }

    void DeleteCapacityFromTextView(TextView textView){
        String capacity = getCapacityFromTextView(textView);
        if (capacity != null) {
            Capacity.remove(capacity);
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

        if ( TypeofDisk.isEmpty() && Capacity.isEmpty() && Rate == 0 && (scrollpricerangeMAX.getText().toString().isEmpty() || scrollpricerangeMIN.getText().toString().isEmpty()) && Rate == 0) {
            return;
        }
        if (!TypeofDisk.isEmpty()){
            int jack = TypeofDisk.size();
            SortTheListFromTypeOfDisk(jack);
        }
        if (!Capacity.isEmpty()){
            int jack = Capacity.size();
            SortTheListFromCapacity(jack);
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
        for (Disk disk : disks) {
            arrayList.add(disk.getProductId());
        }

        ((ProductList) getActivity()).UpdateListProductAdapter(arrayList);
        resetData();

    }

    private void SortTheListFromTypeOfDisk(int listsize) {
        Iterator<Disk> iterator = disks.iterator();
        while (iterator.hasNext()) {
            Disk disk = iterator.next();
            boolean isValid = false;
            for (int i = 0; i < listsize; i++) {
                if (disk.getDisktype().equals(TypeofDisk.get(i))) {
                    isValid = true;
                    break;
                }
            }
            if (!isValid) {
                iterator.remove();
            }
        }
    }

    private void SortTheListFromCapacity(int listsize) {
        Iterator<Disk> iterator = disks.iterator();
        while (iterator.hasNext()) {
            Disk disk = iterator.next();
            boolean isValid = false;
            for (int i = 0; i < listsize; i++) {
                if (disk.getCapacity().equals(Capacity.get(i))) {
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
        Iterator<Disk> iterator = disks.iterator();
        while (iterator.hasNext()) {
            Disk disk = iterator.next();
            if (disk.getPrice() < min || disk.getPrice() > max) {
                iterator.remove();
            }
        }
    }
    private void GetTheListFromRate(int rate) {
        Iterator<Disk> iterator = disks.iterator();
        while (iterator.hasNext()) {
            Disk disk = iterator.next();
            if (disk.getRate() < rate) {
                iterator.remove();
            }
        }
    }

    private void resetData() {
        TypeofDisk.clear();
        Capacity.clear();
        disks.clear();
        Rate = 0;
        scrollpricerangeMIN.setText("");
        scrollpricerangeMAX.setText("");
        resetCapacityTextViewColors();
        resetHardDriveTypeTextViewColors();
        resetPriceRangeTextViewColors();
        resetRateTextViewColors();
        disks.addAll(disks2);
    }

    private void TaiListDisk(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Disk");
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Disk> disks1 = new ArrayList<>();
                    for (DataSnapshot ramSnapshot : dataSnapshot.getChildren()) {
                        int productID = ramSnapshot.child("ProductID").getValue(int.class);
                        String Capacity = ramSnapshot.child("Capacity").getValue(String.class);
                        double Price = ramSnapshot.child("Price").getValue(double.class);
                        String DiskType = ramSnapshot.child("DiskType").getValue(String.class);
                        double Rate = ramSnapshot.child("Rate").getValue(double.class);;
                        Disk disk = new Disk(productID, Capacity, DiskType, Price, Rate);
                        disks1.add(disk);
                    }
                    disks2.addAll(disks1);
                    disks.addAll(disks1);
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



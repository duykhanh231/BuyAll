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


public class RamFilterFragment extends Fragment {
    private TextView tvfilter64GB, tvfilter8GB, tvfilter16GB, tvfilter32GB;
    private TextView tvfilterDDR5, tvfilterDDR4, tvfilterDDR3;
    private TextView tvfilterSODIMM, tvfilterDIMM;
    private TextView tvfilterSAMSUNG, tvfilterCORSAIR, tvfilterKINGSTON, tvfilterCRUCIAL, tvfilterGSKILL, tvfilterADATA;
    private TextView tvscrollprice0to100k, tvscrollprice100kto200k;
    private TextView tvscrollrate1STAR, tvscrollrate2STAR, tvscrollrate3STAR, tvscrollrate4STAR, tvscrollrate5STAR;
    private EditText scrollpricerangeMAX, scrollpricerangeMIN;
    private Button Xacnhanfilterbtn, Xaclaplaifilterbtn;
    private ImageView imageView;
    private int Rate;
    private List<Ram> rams,rams2;
    private ArrayList<String> arrayListBRAND, arrayListMEMORYTYPE, arrayListCAPACITY,arrayListSUPPORT;
    private ArrayList<Integer> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ram_filter, container, false);
        Xacnhanfilterbtn = view.findViewById(R.id.btnscrollXACNHAN);
        Xaclaplaifilterbtn = view.findViewById(R.id.btnscrollXACLAPLAI);
        rams = new ArrayList<>();
        arrayListBRAND = new ArrayList<>();
        arrayListMEMORYTYPE = new ArrayList<>();
        arrayListCAPACITY = new ArrayList<>();
        arrayListSUPPORT = new ArrayList<>();
        arrayList = new ArrayList<>();
        rams2 = new ArrayList<>();
        Rate = 0;
        imageView = view.findViewById(R.id.ivARROWTURNBACKALLFILTER);

        scrollpricerangeMAX = view.findViewById(R.id.scrollpricerangeMAX);
        scrollpricerangeMIN = view.findViewById(R.id.scrollpricerangeMIN);

        tvfilter8GB = view.findViewById(R.id.tvfilter8GB);
        tvfilter32GB = view.findViewById(R.id.tvfilter32GB);
        tvfilter16GB = view.findViewById(R.id.tvfilter16GB);
        tvfilter64GB = view.findViewById(R.id.tvfilter64GB);

        tvfilterDDR5 = view.findViewById(R.id.tvfilterDDR5);
        tvfilterDDR4 = view.findViewById(R.id.tvfilterDDR4);
        tvfilterDDR3 = view.findViewById(R.id.tvfilterDDR3);

        tvfilterSAMSUNG = view.findViewById(R.id.tvfilterSAMSUNG);
        tvfilterCORSAIR = view.findViewById(R.id.tvfilterCORSAIR);
        tvfilterKINGSTON = view.findViewById(R.id.tvfilterKINGSTON);
        tvfilterCRUCIAL = view.findViewById(R.id.tvfilterCRUCIAL);
        tvfilterGSKILL = view.findViewById(R.id.tvfilterGSKILL);
        tvfilterADATA = view.findViewById(R.id.tvfilterADATA);

        tvscrollprice0to100k = view.findViewById(R.id.tvscrollprice0to100k);
        tvscrollprice100kto200k = view.findViewById(R.id.tvscrollprice100kto200k);

        tvscrollrate1STAR = view.findViewById(R.id.tvscrollrate1STAR);
        tvscrollrate2STAR = view.findViewById(R.id.tvscrollrate2STAR);
        tvscrollrate3STAR = view.findViewById(R.id.tvscrollrate3STAR);
        tvscrollrate4STAR = view.findViewById(R.id.tvscrollrate4STAR);
        tvscrollrate5STAR = view.findViewById(R.id.tvscrollrate5STAR);

        tvfilterSODIMM = view.findViewById(R.id.tvfilterSODIMM);
        tvfilterDIMM = view.findViewById(R.id.tvfilterDIMM);

        setCapacityTextViewClickListener(tvfilter8GB);
        setCapacityTextViewClickListener(tvfilter32GB);
        setCapacityTextViewClickListener(tvfilter16GB);
        setCapacityTextViewClickListener(tvfilter64GB);

        setRamTypeTextViewClickListener(tvfilterDDR5);
        setRamTypeTextViewClickListener(tvfilterDDR4);
        setRamTypeTextViewClickListener(tvfilterDDR3);

        setPriceRangeTextViewClickListener(tvscrollprice0to100k);
        setPriceRangeTextViewClickListener(tvscrollprice100kto200k);

        setRateTextViewClickListener(tvscrollrate1STAR);
        setRateTextViewClickListener(tvscrollrate2STAR);
        setRateTextViewClickListener(tvscrollrate3STAR);
        setRateTextViewClickListener(tvscrollrate4STAR);
        setRateTextViewClickListener(tvscrollrate5STAR);

        setSupportTextViewClickListener(tvfilterSODIMM);
        setSupportTextViewClickListener(tvfilterDIMM);

        setBrandTextViewClickListener(tvfilterSAMSUNG);
        setBrandTextViewClickListener(tvfilterCORSAIR);
        setBrandTextViewClickListener(tvfilterKINGSTON);
        setBrandTextViewClickListener(tvfilterCRUCIAL);
        setBrandTextViewClickListener(tvfilterGSKILL);
        setBrandTextViewClickListener(tvfilterADATA);

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
            public void onClick(View v) {
                resetCapacityTextViewColors();
                resetRamTypeTextViewColors();
                resetPriceRangeTextViewColors();
                resetRateTextViewColors();
                resetSupportTextViewColors();
                resetBrandTextViewColors();
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
        TaiListRam();
        return view;
    }

    private void setCapacityTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor() == Color.RED) {
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(Color.parseColor("#D3D3D3"));
                DeleteMemorySizeTextViewValue(textView);
                return;
            }
            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
            AddMemorySizeTextViewValue(textView);
        });
    }

    private void setRamTypeTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor() == Color.RED) {
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(Color.parseColor("#D3D3D3"));
                DeletememoryTypeTextViewValue(textView);
                return;
            }
            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
            AddmemoryTypeTextViewValue(textView);
        });
    }

    @SuppressLint("SetTextI18n")
    private void setPriceRangeTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor() == Color.RED) {
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
            if (textView.getCurrentTextColor() == Color.RED) {
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

    private void setSupportTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor() == Color.RED) {
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(Color.parseColor("#D3D3D3"));
                DeleteSupportTextViewValue(textView);
                return;
            }


            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
            AddSupportTextViewValue(textView);
        });
    }

    private void setBrandTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor() == Color.RED) {
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(Color.parseColor("#D3D3D3"));
                DeleteBrandTextViewValue(textView);
                return;
            }
            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
            AddBrandTextViewValue(textView);
        });
    }

    private void resetCapacityTextViewColors() {
        tvfilter64GB.setTextColor(Color.BLACK);
        tvfilter16GB.setTextColor(Color.BLACK);
        tvfilter32GB.setTextColor(Color.BLACK);
        tvfilter8GB.setTextColor(Color.BLACK);


        tvfilter64GB.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilter16GB.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilter32GB.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilter8GB.setBackgroundColor(Color.parseColor("#D3D3D3"));

    }

    private void resetSupportTextViewColors() {
        tvfilterSODIMM.setTextColor(Color.BLACK);
        tvfilterDIMM.setTextColor(Color.BLACK);

        tvfilterSODIMM.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterDIMM.setBackgroundColor(Color.parseColor("#D3D3D3"));
    }

    private void resetRamTypeTextViewColors() {
        tvfilterDDR3.setTextColor(Color.BLACK);
        tvfilterDDR4.setTextColor(Color.BLACK);
        tvfilterDDR5.setTextColor(Color.BLACK);

        tvfilterDDR3.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterDDR4.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterDDR5.setBackgroundColor(Color.parseColor("#D3D3D3"));

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

    private void resetBrandTextViewColors() {
        tvfilterSAMSUNG.setTextColor(Color.BLACK);
        tvfilterCORSAIR.setTextColor(Color.BLACK);
        tvfilterKINGSTON.setTextColor(Color.BLACK);
        tvfilterCRUCIAL.setTextColor(Color.BLACK);
        tvfilterGSKILL.setTextColor(Color.BLACK);
        tvfilterADATA.setTextColor(Color.BLACK);

        tvfilterSAMSUNG.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterCORSAIR.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterKINGSTON.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterCRUCIAL.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterGSKILL.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvfilterADATA.setBackgroundColor(Color.parseColor("#D3D3D3"));
    }

    private String getBrandNameFromTextView(TextView textView) {
        if (textView == tvfilterSAMSUNG) return "Samsung";
        if (textView == tvfilterCORSAIR) return "Corsair";
        if (textView == tvfilterKINGSTON) return "Kingston";
        if (textView == tvfilterCRUCIAL) return "Crucial";
        if (textView == tvfilterGSKILL) return "G.SKILL";
        if (textView == tvfilterADATA) return "ADATA";
        return null;
    }

    void AddBrandTextViewValue(TextView textView) {
        String brand = getBrandNameFromTextView(textView);
        if (brand != null) {
            arrayListBRAND.add(brand);
        }
    }

    void DeleteBrandTextViewValue(TextView textView) {
        String brand = getBrandNameFromTextView(textView);
        if (brand != null) {
            arrayListBRAND.remove(brand);
        }
    }

    void AddmemoryTypeTextViewValue(TextView textView) {
        String memoryType = getmemoryTypeFromTextView(textView);
        if (memoryType != null) {
            arrayListMEMORYTYPE.add(memoryType);
        }
    }

    void DeletememoryTypeTextViewValue(TextView textView) {
        String memoryType = getmemoryTypeFromTextView(textView);
        if (memoryType != null) {
            arrayListMEMORYTYPE.remove(memoryType);
        }
    }

    private String getmemoryTypeFromTextView(TextView textView) {
        if (textView == tvfilterDDR5) return "DDR5";
        if (textView == tvfilterDDR4) return "DDR4";
        if (textView == tvfilterDDR3) return "DDR3";
        return null;
    }

    void AddMemorySizeTextViewValue(TextView textView) {
        String memorySize = getmemorySizeFromTextView(textView);
        if (memorySize != null) {
            arrayListCAPACITY.add(memorySize);
        }
    }

    void DeleteMemorySizeTextViewValue(TextView textView) {
        String memorySize = getmemorySizeFromTextView(textView);
        if (memorySize != null) {
            arrayListCAPACITY.remove(memorySize);
        }
    }

    private String getmemorySizeFromTextView(TextView textView) {
        if (textView == tvfilter8GB) return "8 GB";
        if (textView == tvfilter16GB) return "16 GB";
        if (textView == tvfilter32GB) return "32 GB";
        if (textView == tvfilter64GB) return "64 GB";
        return null;
    }

    private Integer getRatingFromTextView(TextView textView) {
        if (textView == tvscrollrate1STAR) return 1;
        if (textView == tvscrollrate2STAR) return 2;
        if (textView == tvscrollrate3STAR) return 3;
        if (textView == tvscrollrate4STAR) return 4;
        if (textView == tvscrollrate5STAR) return 5;
        return null;
    }

    private String getSupportFromTextView(TextView textView) {
        if (textView == tvfilterSODIMM) return "SO-DIMM";
        if (textView == tvfilterDIMM) return "DIMM";
        return null;
    }
    void AddSupportTextViewValue(TextView textView) {
        String support = getSupportFromTextView(textView);
        if (support != null) {
            arrayListSUPPORT.add(support);
        }
    }

    void DeleteSupportTextViewValue(TextView textView) {
        String support = getSupportFromTextView(textView);
        if (support != null) {
            arrayListSUPPORT.remove(support);
        }
    }
    private void GetTheList() {

        if (arrayListBRAND.isEmpty() && arrayListMEMORYTYPE.isEmpty() && arrayListCAPACITY.isEmpty() && arrayListSUPPORT.isEmpty() && Rate == 0 && (scrollpricerangeMAX.getText().toString().isEmpty() || scrollpricerangeMIN.getText().toString().isEmpty()) && Rate == 0) {
            return;
        }
        if (!arrayListCAPACITY.isEmpty()) {
            int jack = arrayListCAPACITY.size();
            SortTheListFromCAPACITY(jack);
        }
        if (!arrayListMEMORYTYPE.isEmpty()) {
            int jack = arrayListMEMORYTYPE.size();
            SortTheListFromMEMORYTYPE(jack);
        }
        if (!arrayListBRAND.isEmpty()) {
            int jack = arrayListBRAND.size();
            SortTheListFromBRAND(jack);
        }

        if (!arrayListSUPPORT.isEmpty()) {
            int jack = arrayListSUPPORT.size();
            SortTheListFromSUPPORT(jack);
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

        for (Ram ram : rams) {
            int a = ram.getProductId();
            arrayList.add(a);
        }

        ((ProductList) getActivity()).UpdateListProductAdapter(arrayList);
        resetData();
    }

    private void SortTheListFromCAPACITY(int listsize) {
        Iterator<Ram> iterator = rams.iterator();
        while (iterator.hasNext()) {
            Ram ram = iterator.next();
            boolean isValid = false;
            for (int i = 0; i < listsize; i++) {
                if (ram.getCapacity().equals(arrayListCAPACITY.get(i))) {
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
        Iterator<Ram> iterator = rams.iterator();
        while (iterator.hasNext()) {
            Ram ram = iterator.next();
            boolean isValid = false;
            for (int i = 0; i < listsize; i++) {
                if (ram.getRamType().equals(arrayListMEMORYTYPE.get(i))) {
                    isValid = true;
                    break;
                }
            }
            if (!isValid) {
                iterator.remove();
            }
        }
    }


    private void SortTheListFromBRAND(int listsize) {
        Iterator<Ram> iterator = rams.iterator();
        while (iterator.hasNext()) {
            Ram ram = iterator.next();
            boolean isValid = false;
            for (int i = 0; i < listsize; i++) {
                if (ram.getBrand().equals(arrayListBRAND.get(i))) {
                    isValid = true;
                    break;
                }
            }
            if (!isValid) {
                iterator.remove();
            }
        }
    }


    private  void SortTheListFromSUPPORT(int listsize){
        Iterator<Ram> iterator = rams.iterator();
        while (iterator.hasNext()) {
            Ram ram = iterator.next();
            if (listsize == 1) {
                if (!ram.getSupport().equals(arrayListSUPPORT.get(0))) {
                    iterator.remove();
                }
            }
        }
    }

    private void SortTheListPRICERANGE(double min, double max) {
        Iterator<Ram> iterator = rams.iterator();
        while (iterator.hasNext()) {
            Ram ram = iterator.next();
            if (ram.getPrice() < min || ram.getPrice() > max) {
                iterator.remove();
            }
        }
    }

    private void GetTheListFromRate(int rate) {
        Iterator<Ram> iterator = rams.iterator();
        while (iterator.hasNext()) {
            Ram ram = iterator.next();
            if (ram.getRate() < rate) {
                iterator.remove();
            }
        }
    }
    private void resetData() {
        arrayListBRAND.clear();
        arrayListMEMORYTYPE.clear();
        arrayListCAPACITY.clear();
        arrayListSUPPORT.clear();
        rams.clear();
        Rate = 0;
        scrollpricerangeMIN.setText("");
        scrollpricerangeMAX.setText("");
        resetCapacityTextViewColors();
        resetRamTypeTextViewColors();
        resetPriceRangeTextViewColors();
        resetRateTextViewColors();
        resetSupportTextViewColors();
        resetBrandTextViewColors();
        rams.addAll(rams2);
    }
    private void TaiListRam(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Ram");
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Ram> rams1 = new ArrayList<>();
                    for (DataSnapshot ramSnapshot : dataSnapshot.getChildren()) {
                        int productID = ramSnapshot.child("ProductID").getValue(int.class);
                        String productBrand = ramSnapshot.child("Brand").getValue(String.class);
                        String Capacity = ramSnapshot.child("Capacity").getValue(String.class);
                        double Price = ramSnapshot.child("Price").getValue(double.class);
                        String RamType = ramSnapshot.child("RamType").getValue(String.class);
                        double Rate = ramSnapshot.child("Rate").getValue(double.class);
                        String Support = ramSnapshot.child("Support").getValue(String.class);
                        Ram ram = new Ram(productID, Capacity, RamType, productBrand, Support, Price, Rate);
                        rams1.add(ram);
                    }
                    rams2.addAll(rams1);
                    rams.addAll(rams1);
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
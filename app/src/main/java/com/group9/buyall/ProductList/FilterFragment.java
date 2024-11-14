package com.group9.buyall.ProductList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.group9.buyall.R;



public class FilterFragment extends Fragment {
    private TextView tvscrollTPHCM, tvscrollHANOI, tvscrollHUE, tvscrollDANANG;
    private TextView tvscrollshippingHOATOC, tvscrollshippingNHANH, tvscrollshippingTIETKIEM;
    private TextView tvscrollprice0to100k, tvscrollprice100kto200k;
    private TextView tvscrollrate1STAR, tvscrollrate2STAR, tvscrollrate3STAR, tvscrollrate4STAR, tvscrollrate5STAR;
    private TextView tvscrollpromotionVOUCHERXTRA, tvscrollpromotionDANGGIAMGIA, tvscrollpromotionGICUNGRE, tvscrollpromotionHANGCOSAN;
    private TextView tvscrollshoptypeSHOPXUHUONG, tvscrollshoptypeSHOPYEUTHICH, tvscrollshoptypeSHOPKYCUU, tvscrollshoptypeCHOICE;
    private EditText scrollpricerangeMAX, scrollpricerangeMIN;
    private Button Xacnhanfilterbtn,Xaclaplaifilterbtn;
    private TextView tvXEMTHEM;
    private LinearLayout llmORELOCATION;
    private TextView tvXEMFULL;
    private TextView tvscrollHOIAN,tvscrollVUNGTAU,tvscrollBINHDUONG,tvscrollNHATRANG,tvscrollANGIANG,tvscrollBINHTHUAN,
            tvscrollBINHPHUOC,tvscrollBINHDINH,tvscrollBACLIEU,tvscrollBACGIANG,tvscrollBACKAN,
            tvscrollBACNINH,tvscrollBENTRE,tvscrollCAOBANG;
    FrameLayout frameFullCommentContainer,frameCHE;
    public FilterFragment() {
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_scroll, container, false);

        tvXEMFULL = view.findViewById(R.id.tvscrollXEMFULL);

        Xacnhanfilterbtn = view.findViewById(R.id.btnscrollXACNHAN);
        Xaclaplaifilterbtn = view.findViewById(R.id.btnscrollXACLAPLAI);

        scrollpricerangeMAX = view.findViewById(R.id.scrollpricerangeMAX);
        scrollpricerangeMIN = view.findViewById(R.id.scrollpricerangeMIN);

        tvscrollHANOI = view.findViewById(R.id.tvscrollHANOI);
        tvscrollDANANG = view.findViewById(R.id.tvscrollDANANG);
        tvscrollHUE = view.findViewById(R.id.tvscrollHUE);
        tvscrollTPHCM = view.findViewById(R.id.tvscrollTPHCM);

        tvscrollHOIAN = view.findViewById(R.id.tvscrollHOIAN);
        tvscrollVUNGTAU = view.findViewById(R.id.tvscrollVUNGTAU);
        tvscrollBINHDUONG = view.findViewById(R.id.tvscrollBINHDUONG);
        tvscrollNHATRANG = view.findViewById(R.id.tvscrollNHATRANG);
        tvscrollANGIANG = view.findViewById(R.id.tvscrollANGIANG);
        tvscrollBINHTHUAN = view.findViewById(R.id.tvscrollBINHTHUAN);
        tvscrollBINHPHUOC = view.findViewById(R.id.tvscrollBINHPHUOC);
        tvscrollBINHDINH = view.findViewById(R.id.tvscrollBINHDINH);
        tvscrollBACLIEU = view.findViewById(R.id.tvscrollBACLIEU);
        tvscrollBACGIANG = view.findViewById(R.id.tvscrollBACGIANG);
        tvscrollBACKAN = view.findViewById(R.id.tvscrollBACKAN);
        tvscrollBACNINH = view.findViewById(R.id.tvscrollBACNINH);
        tvscrollBENTRE = view.findViewById(R.id.tvscrollBENTRE);
        tvscrollCAOBANG = view.findViewById(R.id.tvscrollCAOBANG);

        tvscrollshippingHOATOC = view.findViewById(R.id.tvscrollshippingHOATOC);
        tvscrollshippingNHANH = view.findViewById(R.id.tvscrollshippingNHANH);
        tvscrollshippingTIETKIEM = view.findViewById(R.id.tvscrollshippingTIETKIEM);

        tvscrollprice0to100k = view.findViewById(R.id.tvscrollprice0to100k);
        tvscrollprice100kto200k = view.findViewById(R.id.tvscrollprice100kto200k);

        tvscrollrate1STAR = view.findViewById(R.id.tvscrollrate1STAR);
        tvscrollrate2STAR = view.findViewById(R.id.tvscrollrate2STAR);
        tvscrollrate3STAR = view.findViewById(R.id.tvscrollrate3STAR);
        tvscrollrate4STAR = view.findViewById(R.id.tvscrollrate4STAR);
        tvscrollrate5STAR = view.findViewById(R.id.tvscrollrate5STAR);

        tvscrollpromotionVOUCHERXTRA = view.findViewById(R.id.tvscrollpromotionVOUCHERXTRA);
        tvscrollpromotionDANGGIAMGIA = view.findViewById(R.id.tvscrollpromotionDANGGIAMGIA);
        tvscrollpromotionGICUNGRE = view.findViewById(R.id.tvscrollpromotionGICUNGRE);
        tvscrollpromotionHANGCOSAN = view.findViewById(R.id.tvscrollpromotionHANGCOSAN);

        tvscrollshoptypeSHOPYEUTHICH = view.findViewById(R.id.tvscrollshoptypeSHOPYEUTHICH);
        tvscrollshoptypeSHOPXUHUONG = view.findViewById(R.id.tvscrollshoptypeSHOPXUHUONG);
        tvscrollshoptypeSHOPKYCUU = view.findViewById(R.id.tvscrollshoptypeSHOPKYCUU);
        tvscrollshoptypeCHOICE = view.findViewById(R.id.tvscrollshoptypeCHOICE);

        setLocationScrollTextViewClickListener(tvscrollHANOI);
        setLocationScrollTextViewClickListener(tvscrollDANANG);
        setLocationScrollTextViewClickListener(tvscrollHUE);
        setLocationScrollTextViewClickListener(tvscrollTPHCM);

        setLocationScrollTextViewClickListener(tvscrollHOIAN);
        setLocationScrollTextViewClickListener(tvscrollVUNGTAU);
        setLocationScrollTextViewClickListener(tvscrollBINHDUONG);
        setLocationScrollTextViewClickListener(tvscrollNHATRANG);
        setLocationScrollTextViewClickListener(tvscrollANGIANG);
        setLocationScrollTextViewClickListener(tvscrollBINHTHUAN);
        setLocationScrollTextViewClickListener(tvscrollBINHPHUOC);
        setLocationScrollTextViewClickListener(tvscrollBINHDINH);
        setLocationScrollTextViewClickListener(tvscrollBACLIEU);
        setLocationScrollTextViewClickListener(tvscrollBACGIANG);
        setLocationScrollTextViewClickListener(tvscrollBACKAN);
        setLocationScrollTextViewClickListener(tvscrollBACNINH);
        setLocationScrollTextViewClickListener(tvscrollBENTRE);
        setLocationScrollTextViewClickListener(tvscrollCAOBANG);

        setShippingUnitScrollTextViewClickListener(tvscrollshippingHOATOC);
        setShippingUnitScrollTextViewClickListener(tvscrollshippingNHANH);
        setShippingUnitScrollTextViewClickListener(tvscrollshippingTIETKIEM);

        setPriceRangeScrollTextViewClickListener(tvscrollprice0to100k);
        setPriceRangeScrollTextViewClickListener(tvscrollprice100kto200k);

        setRateScrollTextViewClickListener(tvscrollrate1STAR);
        setRateScrollTextViewClickListener(tvscrollrate2STAR);
        setRateScrollTextViewClickListener(tvscrollrate3STAR);
        setRateScrollTextViewClickListener(tvscrollrate4STAR);
        setRateScrollTextViewClickListener(tvscrollrate5STAR);

        setPromotionScrollTextViewClickListener(tvscrollpromotionVOUCHERXTRA);
        setPromotionScrollTextViewClickListener(tvscrollpromotionDANGGIAMGIA);
        setPromotionScrollTextViewClickListener(tvscrollpromotionGICUNGRE);
        setPromotionScrollTextViewClickListener(tvscrollpromotionHANGCOSAN);

        setShopTypeScrollTextViewClickListener(tvscrollshoptypeSHOPXUHUONG);
        setShopTypeScrollTextViewClickListener(tvscrollshoptypeSHOPYEUTHICH);
        setShopTypeScrollTextViewClickListener(tvscrollshoptypeSHOPKYCUU);
        setShopTypeScrollTextViewClickListener(tvscrollshoptypeCHOICE);

        llmORELOCATION = view.findViewById(R.id.llMORELOCATION);
        tvXEMTHEM = view.findViewById(R.id.tvscrollXEMTHEM);
        tvXEMTHEM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llmORELOCATION.setVisibility(View.VISIBLE);
                tvXEMTHEM.setVisibility(View.GONE);
            }
        });

        tvXEMFULL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                FullLocationFragment childFragment = new FullLocationFragment();

                FrameLayout containerView = view.findViewById(R.id.flFullcomment);

                // Thêm fragment con vào container trong fragment cha
                transaction.replace(R.id.flFullcomment, childFragment);
                transaction.commit();
                frameFullCommentContainer = getActivity().findViewById(R.id.flFullcomment);
                frameCHE = getActivity().findViewById(R.id.flCHE);
                frameFullCommentContainer.setVisibility(View.VISIBLE);
                updateVisibility();

            }
        });

        Xacnhanfilterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.setVisibility(View.GONE);

                //((ProductList) getActivity()).findViewById(R.id.right_frame).setVisibility(View.GONE);

                // Cập nhật lại trạng thái của dimOverlay
                ((ProductList) getActivity()).updateDimOverlayVisibility();

            }
        });
        Xaclaplaifilterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                resetLocationScrollTextViewColors();
                resetShippingUnitScrollTextViewColors();
                resetPriceRangeScrollTextViewColors();
                resetRateScrollTextViewColors();
                resetPromotionScrollTextViewColors();
                resetShopTypePromotionScrollTextViewColors();
                scrollpricerangeMIN.setText("");
                scrollpricerangeMAX.setText("");
            }
        });
        return view;
    }
    private void setLocationScrollTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor()==Color.RED){
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(Color.parseColor("#D3D3D3"));
                return;
            }
            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
        });
    }

    private void setShippingUnitScrollTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor()==Color.RED){
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(Color.parseColor("#D3D3D3"));
                return;
            }
            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
        });
    }

    @SuppressLint("SetTextI18n")
    private void setPriceRangeScrollTextViewClickListener(TextView textView) {
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
                resetPriceRangeScrollTextViewColors();
                textView.setTextColor(Color.RED);
                textView.setBackgroundResource(R.drawable.border_textview_red);
            } else {
                scrollpricerangeMIN.setText("100000");
                scrollpricerangeMAX.setText("200000");
                resetPriceRangeScrollTextViewColors();
                textView.setTextColor(Color.RED);
                textView.setBackgroundResource(R.drawable.border_textview_red);
            }
        });
    }

    private void setRateScrollTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor()==Color.RED){
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(Color.parseColor("#D3D3D3"));
                return;
            }
            // Đặt màu đen cho tất cả các TextView
            resetRateScrollTextViewColors();

            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
        });
    }

    private void setPromotionScrollTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor()==Color.RED){
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(Color.parseColor("#D3D3D3"));
                return;
            }
            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
        });
    }

    private void setShopTypeScrollTextViewClickListener(TextView textView) {
        textView.setOnClickListener(v -> {
            if (textView.getCurrentTextColor()==Color.RED){
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(Color.parseColor("#D3D3D3"));
                return;
            }
            // Đặt màu đỏ cho TextView được nhấn
            textView.setTextColor(Color.RED);
            textView.setBackgroundResource(R.drawable.border_textview_red);
        });
    }


    private void resetLocationScrollTextViewColors() {
        tvscrollTPHCM.setTextColor(Color.BLACK);
        tvscrollHUE.setTextColor(Color.BLACK);
        tvscrollDANANG.setTextColor(Color.BLACK);
        tvscrollHANOI.setTextColor(Color.BLACK);
        tvscrollHOIAN.setTextColor(Color.BLACK);
        tvscrollVUNGTAU.setTextColor(Color.BLACK);
        tvscrollBINHDUONG.setTextColor(Color.BLACK);
        tvscrollNHATRANG.setTextColor(Color.BLACK);
        tvscrollANGIANG.setTextColor(Color.BLACK);
        tvscrollBINHTHUAN.setTextColor(Color.BLACK);
        tvscrollBINHPHUOC.setTextColor(Color.BLACK);
        tvscrollBINHDINH.setTextColor(Color.BLACK);
        tvscrollBACLIEU.setTextColor(Color.BLACK);
        tvscrollBACGIANG.setTextColor(Color.BLACK);
        tvscrollBACKAN.setTextColor(Color.BLACK);
        tvscrollBACNINH.setTextColor(Color.BLACK);
        tvscrollBENTRE.setTextColor(Color.BLACK);
        tvscrollCAOBANG.setTextColor(Color.BLACK);

        tvscrollTPHCM.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollHUE.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollDANANG.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollHANOI.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollHOIAN.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollVUNGTAU.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollBINHDUONG.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollNHATRANG.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollANGIANG.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollBINHTHUAN.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollBINHPHUOC.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollBINHDINH.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollBACLIEU.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollBACGIANG.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollBACKAN.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollBACNINH.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollBENTRE.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollCAOBANG.setBackgroundColor(Color.parseColor("#D3D3D3"));
    }

    private void resetShippingUnitScrollTextViewColors() {
        tvscrollshippingTIETKIEM.setTextColor(Color.BLACK);
        tvscrollshippingNHANH.setTextColor(Color.BLACK);
        tvscrollshippingHOATOC.setTextColor(Color.BLACK);

        tvscrollshippingTIETKIEM.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollshippingNHANH.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollshippingHOATOC.setBackgroundColor(Color.parseColor("#D3D3D3"));


    }

    private void resetPriceRangeScrollTextViewColors() {
        tvscrollprice100kto200k.setTextColor(Color.BLACK);
        tvscrollprice0to100k.setTextColor(Color.BLACK);

        tvscrollprice100kto200k.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollprice0to100k.setBackgroundColor(Color.parseColor("#D3D3D3"));

    }

    private void resetRateScrollTextViewColors() {
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

    private void resetPromotionScrollTextViewColors() {
        tvscrollpromotionVOUCHERXTRA.setTextColor(Color.BLACK);
        tvscrollpromotionDANGGIAMGIA.setTextColor(Color.BLACK);
        tvscrollpromotionGICUNGRE.setTextColor(Color.BLACK);
        tvscrollpromotionHANGCOSAN.setTextColor(Color.BLACK);

        tvscrollpromotionVOUCHERXTRA.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollpromotionDANGGIAMGIA.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollpromotionGICUNGRE.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollpromotionHANGCOSAN.setBackgroundColor(Color.parseColor("#D3D3D3"));
    }

    private void resetShopTypePromotionScrollTextViewColors() {
        tvscrollshoptypeSHOPYEUTHICH.setTextColor(Color.BLACK);
        tvscrollshoptypeCHOICE.setTextColor(Color.BLACK);
        tvscrollshoptypeSHOPKYCUU.setTextColor(Color.BLACK);
        tvscrollshoptypeSHOPXUHUONG.setTextColor(Color.BLACK);

        tvscrollshoptypeSHOPYEUTHICH.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollshoptypeCHOICE.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollshoptypeSHOPKYCUU.setBackgroundColor(Color.parseColor("#D3D3D3"));
        tvscrollshoptypeSHOPXUHUONG.setBackgroundColor(Color.parseColor("#D3D3D3"));
    }
    void updateVisibility() {

        if (frameFullCommentContainer.getVisibility() == View.VISIBLE) {
            frameCHE.setVisibility(View.VISIBLE);
        } else {
            frameCHE.setVisibility(View.GONE);
        }
    }
}
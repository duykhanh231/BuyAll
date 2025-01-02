package com.group9.buyall.ProductList;

import androidx.fragment.app.Fragment;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.group9.buyall.R;



public class AllFilterFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout linearLayoutRAM, linearOCUNG, linearLayoutCPUINTEL, linearLayoutCPUAMD, linearLayoutVGANVIDIA, linearLayoutVGAAMD;


        View view = inflater.inflate(R.layout.all_filter, container, false);

        linearLayoutRAM = view.findViewById(R.id.llramfilter);
        linearOCUNG = view.findViewById(R.id.llocungfilter);
        linearLayoutCPUINTEL = view.findViewById(R.id.llcpuintelfilter);
        linearLayoutCPUAMD = view.findViewById(R.id.llcpuamdfilter);
        linearLayoutVGANVIDIA = view.findViewById(R.id.llvganvidiafilter);
        linearLayoutVGAAMD = view.findViewById(R.id.llvgaamdfilter);

        linearLayoutRAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.setVisibility(View.GONE);
                ((ProductList) getActivity()).onRamFilterButtonClick();
            }
        });

        linearOCUNG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.setVisibility(View.GONE);
                ((ProductList) getActivity()).onHDDSDDFilterButtonClick();
            }
        });

        linearLayoutCPUINTEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.setVisibility(View.GONE);
                ((ProductList) getActivity()).onCpuIntelFilterButtonClick();
            }
        });

        linearLayoutCPUAMD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.setVisibility(View.GONE);
                ((ProductList) getActivity()).onCpuAMDFilterButtonClick();
            }
        });

        linearLayoutVGANVIDIA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.setVisibility(View.GONE);
                ((ProductList) getActivity()).onVgaNvidiaFilterButtonClick();
            }
        });

        linearLayoutVGAAMD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.setVisibility(View.GONE);
                ((ProductList) getActivity()).onVgaAMDFilterButtonClick();
            }
        });

        return view;
    }
}
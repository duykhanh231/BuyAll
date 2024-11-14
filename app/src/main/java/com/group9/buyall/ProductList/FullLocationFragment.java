package com.group9.buyall.ProductList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import com.group9.buyall.R;

import java.util.ArrayList;
import java.util.List;

public class FullLocationFragment extends Fragment {
    private RecyclerView rcvFullLocation;
    private List<Location_List> locationLists;
    private FullLocationAdapter fullLocationAdapter;
    private ImageButton ibBACK;
    private TextView tvXACNHAN;
    // Phương thức tạo view cho Fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout cho Fragment
        View view = inflater.inflate(R.layout.full_location, container, false);

        // Khởi tạo RecyclerView và thiết lập Adapter
        rcvFullLocation = view.findViewById(R.id.rcvALLLOCATION);
        locationLists = new ArrayList<>(); // Lấy danh sách địa điểm, giả sử từ một phương thức
        fullLocationAdapter = new FullLocationAdapter(locationLists);
        tvXACNHAN = view.findViewById(R.id.tvXACNHAN);
        ibBACK = view.findViewById(R.id.arrow);
        ibBACK.setOnClickListener(v -> {
            container.setVisibility(View.GONE);
            ((FilterFragment) getParentFragment()).updateVisibility();
        });
        tvXACNHAN.setOnClickListener(v -> {
            container.setVisibility(View.GONE);
            ((FilterFragment) getParentFragment()).updateVisibility();
        });

        String[] locations = {
                "Ha Noi", "TP. Ho Chi Minh", "Da Nang", "Can Tho", "Nha Trang",
                "Hai Phong", "An Giang", "Ba Ria-Vung Tau", "Bac Giang", "Bac Kan",
                "Bac Lieu", "Bac Ninh", "Ben Tre", "Binh Duong", "Binh Dinh",
                "Binh Phuoc", "Binh Thuan", "Ca Mau", "Cao Bang", "Can Tho",
                "Dak Lak", "Dak Nong", "Dien Bien", "Dong Nai", "Dong Thap",
                "Gia Lai", "Ha Giang", "Ha Nam", "Ha Tinh", "Hau Giang",
                "Ho Chi Minh", "Hoa Binh", "Hung Yen", "Khanh Hoa", "Kien Giang",
                "Kon Tum", "Lai Chau", "Lam Dong", "Lang Son", "Lao Cai",
                "Long An", "Nam Dinh", "Nghe An", "Ninh Binh", "Ninh Thuan",
                "Phu Tho", "Phu Yen", "Quang Binh", "Quang Nam", "Quang Ngai",
                "Quang Ninh", "Quang Tri", "Soc Trang", "Son La", "Tay Ninh",
                "Thai Binh", "Thai Nguyen", "Thanh Hoa", "Thua Thien-Hue", "Tien Giang",
                "Tra Vinh", "Tuyen Quang", "Vinh Long", "Vinh Phuc", "Yen Bai"
        };

        for (String location : locations) {
            locationLists.add(new Location_List(location));
        }



        rcvFullLocation.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rcvFullLocation.setAdapter(fullLocationAdapter);

        return view;
    }
}

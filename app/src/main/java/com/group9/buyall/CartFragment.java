package com.group9.buyall;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    private RecyclerView cartItemRecyclerView;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartItemRecyclerView = view.findViewById(R.id.cart_items_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecyclerView.setLayoutManager(layoutManager);

        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,R.drawable.iphone16,"iPhone 128GB VN/A",2,"16.000.000 VND","32.000.000 VND",1,0,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.applewatch,"Apple Watch Series 10",0,"11.799.000 VND","27.990.000 VND",1,1,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.macbookpro,"MacBook Pro 13.3-inch Apple M1 Chip 8‑Core CPU",2,"28.990.000 VND","49.990.000 VND",1,0,0));
        cartItemModelList.add(new CartItemModel(1,"Price (3 items)","109.980.000 VND","Free","56.789.000 VND","53.191.000 VND"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        cartItemRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        Button checkoutButton = view.findViewById(R.id.cart_checkout_btn);
        checkoutButton.setOnClickListener(v -> {
            // Navigate to the OrderHistory activity
            Intent intent = new Intent(getActivity(), OrderHistoryActivity.class);
            startActivity(intent);
        });
        return view;
    }
}
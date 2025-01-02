package com.group9.buyall.ProductDetail;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group9.buyall.R;

import java.util.List;

public class Product_Linked_Adapter extends RecyclerView.Adapter<Product_Linked_Adapter.ProductLinkedViewHolder>{
    private List<Linked_Product> linkedProducts;
    Context context;
    private int currentSelectedID = -1;
    private int selectedPosition = RecyclerView.NO_POSITION;


    public Product_Linked_Adapter(List<Linked_Product> linkedProducts, Context context) {
        this.linkedProducts = linkedProducts;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductLinkedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_linked,parent,false);
        return new ProductLinkedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductLinkedViewHolder holder, int position) {
        Linked_Product linkedProduct = linkedProducts.get(position);
        if (linkedProduct == null){
            return;
        }
        holder.tvINFOSANPHAM.setText(linkedProduct.getProductFEWINFO());
        double price = linkedProduct.getPrice();
        String formattedPrice = String.format("đ %,.0f", price);
        holder.tvGIASANPHAM.setText(formattedPrice);

        if (holder.getAdapterPosition() == selectedPosition) {
            holder.linearLayout.setBackgroundResource(R.drawable.border_red_radius); }

        holder.itemView.setOnClickListener(v -> {

            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition == RecyclerView.NO_POSITION) {
                return; }
            selectedPosition = adapterPosition;
            notifyDataSetChanged();

            if (linkedProduct.getProductID()==currentSelectedID) {
                return;
            }
            currentSelectedID = linkedProduct.getProductID();
            ((Activity)context).finish();
            Intent intent = new Intent(context, ProductDetail.class);
            intent.putExtra("PRODUCT_ID", linkedProduct.getProductID());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (linkedProducts!=null){
            return linkedProducts.size();
        }
        return 0;
    }

    class ProductLinkedViewHolder extends RecyclerView.ViewHolder {
        private TextView tvINFOSANPHAM,tvGIASANPHAM;
        private LinearLayout linearLayout;
        public ProductLinkedViewHolder(@NonNull View itemView) {
            super(itemView);
            tvINFOSANPHAM = itemView.findViewById(R.id.tvINFOLINKEDPRODUCT);
            tvGIASANPHAM = itemView.findViewById(R.id.tvPRICELINKEDPRODUCT);
            linearLayout = itemView.findViewById(R.id.lllinked);
        }
    }
}

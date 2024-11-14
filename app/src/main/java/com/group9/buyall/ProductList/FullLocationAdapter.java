package com.group9.buyall.ProductList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group9.buyall.R;

import java.util.List;

public class FullLocationAdapter extends RecyclerView.Adapter<FullLocationAdapter.Location_ListViewHolder> {
    private List<Location_List> locationLists;
    private Context context;

    public FullLocationAdapter(List<Location_List> locationLists) {
        this.locationLists = locationLists;
        this.context = context;
    }

    @NonNull
    @Override
    public Location_ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.full_location_item,parent,false);
        return new Location_ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Location_ListViewHolder holder, int position) {
        Location_List locationList = locationLists.get(position);
        if (locationList==null){return;}
        holder.tvLocation.setText(locationList.getLocation());
        holder.tvLocation.setOnClickListener(v -> {
            if (holder.tvLocation.getCurrentTextColor() == Color.RED) {
                holder.tvLocation.setTextColor(Color.BLACK);
                holder.tvLocation.setBackgroundColor(Color.parseColor("#D3D3D3"));
            } else {
                holder.tvLocation.setTextColor(Color.RED);
                holder.tvLocation.setBackgroundResource(R.drawable.border_textview_red);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (locationLists != null){return locationLists.size();}
        return 0;
    }

    class Location_ListViewHolder extends RecyclerView.ViewHolder{
        private TextView tvLocation;
        public Location_ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLocation = itemView.findViewById(R.id.tvlocation);
        }
    }
}

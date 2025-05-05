package com.example.g_tiu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_tiu.R;
import com.example.g_tiu.item.giaoDich;

import java.util.ArrayList;
import java.util.List;

public class giaoDichAdapter extends RecyclerView.Adapter<giaoDichAdapter.giaoDichViewHolder> {
    private final List<giaoDich> list;
    Context context;
    LayoutInflater layoutInflater;

    public giaoDichAdapter(List<giaoDich> list) {
        this.list = list;
    }

    public giaoDichAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public giaoDichViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                // don't do inflate(R.layout.item_phanloai, null)
                .inflate(R.layout.item_giaodich, parent, false);

        return new giaoDichAdapter.giaoDichViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull giaoDichViewHolder holder, int position) {
        giaoDich giaoDich = list.get(position);
        holder.loaiGD.setText(giaoDich.getLoaiGD());
        holder.soTien.setText(String.valueOf(giaoDich.getSoTien()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(List<giaoDich> item) {
        list.clear();
        list.addAll(item);
        notifyDataSetChanged();
    }

    public static class giaoDichViewHolder extends RecyclerView.ViewHolder {
        protected TextView loaiGD;
        protected TextView soTien;

        public giaoDichViewHolder(@NonNull View itemView) {
            super(itemView);

            loaiGD = itemView.findViewById(R.id.textView_giaoDich_phanLoai);
            soTien = itemView.findViewById(R.id.textView_giaoDich_soTien);
        }
    }
}

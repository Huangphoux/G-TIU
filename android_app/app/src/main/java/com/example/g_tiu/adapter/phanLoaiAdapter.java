package com.example.g_tiu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_tiu.R;
import com.example.g_tiu.item.loaiGD;

import java.util.ArrayList;
import java.util.List;

public class phanLoaiAdapter extends RecyclerView.Adapter<phanLoaiAdapter.phanLoaiViewHolder> {
    private final List<loaiGD> list;
    Context context;
    LayoutInflater layoutInflater;

    public phanLoaiAdapter(List<loaiGD> list) {
        this.list = list;
    }

    public phanLoaiAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public phanLoaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                // don't do inflate(R.layout.item_phanloai, null)
                .inflate(R.layout.item_phanloai, parent, false);
        return new phanLoaiViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull phanLoaiViewHolder holder, int position) {
        loaiGD phanLoai = list.get(position);
        holder.tenPhanLoai.setText(phanLoai.getTenLGD());
        holder.tongPhanLoai.setText("100");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(List<loaiGD> item) {
        list.clear();
        list.addAll(item);
        notifyDataSetChanged();
    }

    public static class phanLoaiViewHolder extends RecyclerView.ViewHolder {
        protected TextView tenPhanLoai;
        protected TextView tongPhanLoai;

        public phanLoaiViewHolder(@NonNull View itemView) {
            super(itemView);

            tenPhanLoai = itemView.findViewById(R.id.textView_phanLoai_ten);
            tongPhanLoai = itemView.findViewById(R.id.textView_phanLoai_tong);
        }
    }

}

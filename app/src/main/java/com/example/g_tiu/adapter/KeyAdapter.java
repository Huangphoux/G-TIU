package com.example.g_tiu.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_tiu.databinding.ItemCategoryBinding;
import com.example.g_tiu.databinding.ItemKeyBinding;
import com.example.g_tiu.item.Keyword;

import java.util.List;

public class KeyAdapter extends RecyclerView.Adapter<KeyAdapter.KeywordViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;

    private List<Keyword> keywords;
    private final OnKeywordListener onKeywordListener;

    public KeyAdapter(List<Keyword> keywords, OnKeywordListener onKeywordListener) {
        this.keywords = keywords;
        this.onKeywordListener = onKeywordListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public KeywordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new KeywordViewHolder(
                ItemKeyBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false),
                onKeywordListener
        );
    }

    @Override
    public void onBindViewHolder(@NonNull KeywordViewHolder holder, int position) {
        holder.bind(keywords.get(position));
    }

    @Override
    public int getItemCount() {
        return keywords != null ? keywords.size() : 0;
    }

    static class KeywordViewHolder extends RecyclerView.ViewHolder {
        private final ItemKeyBinding binding;
        private final OnKeywordListener onKeywordListener;

        KeywordViewHolder(ItemKeyBinding binding, OnKeywordListener onKeywordListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onKeywordListener = onKeywordListener;
        }

        @SuppressLint("SetTextI18n")
        public void bind(Keyword keyword) {


            binding.getRoot().setOnClickListener(v -> {
                if (onKeywordListener != null) {
                    onKeywordListener.onClickItemKeyword(keyword);
                }
            });
        }
    }

    public interface OnKeywordListener {
        void onClickItemKeyword(Keyword keyword);
    }
}


package com.example.g_tiu.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_tiu.R;
import com.example.g_tiu.databinding.ItemCategoryBinding;
import com.example.g_tiu.databinding.ItemCategoryHeaderBinding;
import com.example.g_tiu.item.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<Category> categories;

    public CategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (categories.get(position).isHeader()) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new HeaderCategoryViewHolder(ItemCategoryHeaderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
        return new CategoryViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CategoryViewHolder) {
            ((CategoryViewHolder) holder).bind(categories.get(position));
        } else if (holder instanceof HeaderCategoryViewHolder) {
            ((HeaderCategoryViewHolder) holder).bind(categories.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemCategoryBinding binding;

        CategoryViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Category category) {
            binding.tvName.setText(category.getName());
            // TODO: set total
        }
    }

    static class HeaderCategoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemCategoryHeaderBinding binding;

        HeaderCategoryViewHolder(ItemCategoryHeaderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Category category) {
            switch (category.getName()) {
                case "expenses":
                    binding.ivIcon.setImageResource(R.drawable.icons_expenses);
                    break;
                case "income":
                    binding.ivIcon.setImageResource(R.drawable.icons_income);
                    break;
                case "saving":
                    binding.ivIcon.setImageResource(R.drawable.icons_saving);
                    break;
            }
            binding.tvName.setText(category.getName());
            // TODO: set total
        }
    }
}

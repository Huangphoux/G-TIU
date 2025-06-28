package com.example.g_tiu.adapter;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_tiu.R;
import com.example.g_tiu.databinding.ItemCategoryBinding;
import com.example.g_tiu.databinding.ItemCategoryHeaderBinding;
import com.example.g_tiu.helper.AppConstants;
import com.example.g_tiu.item.Category;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;

    private List<Category> categories;
    private final OnCategoryListener onCategoryListener;
    private final boolean isShowBudget;

    public CategoryAdapter(List<Category> categories, OnCategoryListener onCategoryListener) {
        this.categories = categories;
        this.onCategoryListener = onCategoryListener;
        isShowBudget = true;
    }

    public CategoryAdapter(List<Category> categories, OnCategoryListener onCategoryListener, boolean isShowBudget) {
        this.categories = categories;
        this.onCategoryListener = onCategoryListener;
        this.isShowBudget = isShowBudget;
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
        return new CategoryViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), onCategoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CategoryViewHolder) {
            ((CategoryViewHolder) holder).bind(categories.get(position), isShowBudget);
        } else if (holder instanceof HeaderCategoryViewHolder) {
            ((HeaderCategoryViewHolder) holder).bind(categories.get(position), isShowBudget);
        }
    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemCategoryBinding binding;
        private final OnCategoryListener onCategoryListener;

        CategoryViewHolder(ItemCategoryBinding binding, OnCategoryListener onCategoryListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onCategoryListener = onCategoryListener;
        }

        @SuppressLint("SetTextI18n")
        public void bind(Category category, boolean isShowBudget) {
            binding.tvName.setText(category.getName());
            NumberFormat format = NumberFormat.getInstance(new Locale("vi", "VN"));
            if (category.getBudget() > 0) {
                String formatted = format.format(category.getBudget());
                binding.tvBudget.setText(format.format(category.getActual()) + "/" + formatted);
            } else {
                binding.tvBudget.setText(format.format(category.getActual()));
            }
            if (isShowBudget) {
                binding.layoutBudget.setVisibility(View.VISIBLE);
            } else {
                binding.layoutBudget.setVisibility(View.GONE);
            }
            binding.ivIcon.setImageResource(AppConstants.getIcons().get(category.getIcon()).getResId());
            binding.ivIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(binding.getRoot().getContext(), R.color.color_main)));
            binding.cardColor.setCardBackgroundColor(ColorStateList.valueOf(android.graphics.Color.parseColor(category.getHex())));

            if (category.getLastTime() != 0) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                Date date = new Date(category.getLastTime());
                binding.tvLastTime.setText(sdf.format(date));
                binding.tvLastTime.setVisibility(View.VISIBLE);
            } else {
                binding.tvLastTime.setVisibility(View.GONE);
            }

            binding.getRoot().setOnClickListener(v -> {
                if (onCategoryListener != null) {
                    onCategoryListener.onClickItemCategory(category);
                }
            });
            binding.getRoot().setOnLongClickListener(view -> {
                if (onCategoryListener != null) {
                    onCategoryListener.onLongClickItemCategory(category);
                }
                return false;
            });
        }
    }

    static class HeaderCategoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemCategoryHeaderBinding binding;

        HeaderCategoryViewHolder(ItemCategoryHeaderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(Category category, boolean isShowBudget) {
            switch (category.getName()) {
                case "expense":
                    binding.ivIcon.setImageResource(R.drawable.icons_expenses);
                    binding.tvName.setText("chi tiêu");
                    break;
                case "income":
                    binding.ivIcon.setImageResource(R.drawable.icons_income);
                    binding.tvName.setText("thu nhập");
                    break;
                case "saving":
                    binding.ivIcon.setImageResource(R.drawable.icons_saving);
                    binding.tvName.setText("tiết kiệm");
                    break;
            }
        }
    }

    public interface OnCategoryListener {
        void onClickItemCategory(Category category);

        void onLongClickItemCategory(Category category);
    }
}

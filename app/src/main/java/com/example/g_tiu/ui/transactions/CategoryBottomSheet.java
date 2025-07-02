package com.example.g_tiu.ui.transactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.g_tiu.adapter.CategoryAdapter;
import com.example.g_tiu.databinding.BottomSheetCategoryBinding;
import com.example.g_tiu.item.Category;
import com.example.g_tiu.ui.category.CategoryViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryBottomSheet extends BottomSheetDialogFragment {

    private BottomSheetCategoryBinding binding;
    private List<Category> categories;
    private CategoryAdapter adapter;
    private CategoryAdapter.OnCategoryListener onCategoryListener;

    public void setOnCategoryListener(CategoryAdapter.OnCategoryListener onCategoryListener) {
        this.onCategoryListener = onCategoryListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CategoryViewModel viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        viewModel.init(requireActivity().getApplication());

        viewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result == null || result.isEmpty()) return;
            List<Category> expenseList = result.stream()
                    .filter(c -> "expense".equalsIgnoreCase(c.getType()))
                    .collect(Collectors.toList());

            long totalExpenseBudget = expenseList.stream()
                    .mapToLong(Category::getBudget)
                    .sum();

            List<Category> incomeList = result.stream()
                    .filter(c -> "income".equalsIgnoreCase(c.getType()))
                    .collect(Collectors.toList());

            long totalIncomeBudget = incomeList.stream()
                    .mapToLong(Category::getBudget)
                    .sum();

            List<Category> savingList = result.stream()
                    .filter(c -> "saving".equalsIgnoreCase(c.getType()))
                    .collect(Collectors.toList());

            long totalSavingBudget = savingList.stream()
                    .mapToLong(Category::getBudget)
                    .sum();

            categories = new ArrayList<>();
            categories.add(new Category(-1, "expenses", true, totalExpenseBudget));
            categories.addAll(expenseList);
            categories.add(new Category(-1, "income", true, totalIncomeBudget));
            categories.addAll(incomeList);
            categories.add(new Category(-1, "saving", true, totalSavingBudget));
            categories.addAll(savingList);
            adapter = new CategoryAdapter(categories, onCategoryListener, false);

            binding.recyclerView.setHasFixedSize(true);
            binding.recyclerView.setNestedScrollingEnabled(false);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recyclerView.setAdapter(adapter);
        });

        viewModel.getAll();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


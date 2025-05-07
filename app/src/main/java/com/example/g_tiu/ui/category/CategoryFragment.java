package com.example.g_tiu.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.g_tiu.adapter.CategoryAdapter;
import com.example.g_tiu.databinding.FragmentCategoryBinding;
import com.example.g_tiu.item.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    private CategoryAdapter adapter;
    private List<Category> categories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categories = new ArrayList<>();
        categories.add(new Category(-1, "expenses", true));
        categories.add(new Category(0, "Ăn uống", false));
        categories.add(new Category(1, "Thời trang", false));
        categories.add(new Category(2, "Xe cộ", false));
        categories.add(new Category(-1, "income", true));
        categories.add(new Category(3, "Lương", false));
        categories.add(new Category(4, "Phụ cấp", false));
        categories.add(new Category(5, "Freelancer", false));
        categories.add(new Category(-1, "saving", true));
        categories.add(new Category(6, "Đầu tư", false));

        adapter = new CategoryAdapter(categories);

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

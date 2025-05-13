package com.example.g_tiu.ui.category;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.g_tiu.MainActivity;
import com.example.g_tiu.R;
import com.example.g_tiu.databinding.FragmentAddCategoryBinding;
import com.example.g_tiu.item.Category;

public class AddCategoryFragment extends Fragment {

    private FragmentAddCategoryBinding binding;
    private CategoryViewModel viewModel;
    private String type = "EXPENSE";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddCategoryBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        viewModel.init(requireActivity().getApplication());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.ivBack.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).showMenu();
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });
        viewModel.getInsertResultLiveData().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(requireContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                ((MainActivity) requireActivity()).showMenu();
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            } else {
                Toast.makeText(requireContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        // default
        binding.buttonExpense.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.color_main_30)));

        binding.buttonExpense.setOnClickListener(v -> {
            binding.buttonExpense.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.color_main_30)));
            binding.buttonIncome.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));
            binding.buttonSaving.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));

            type = "EXPENSE";
        });
        binding.buttonIncome.setOnClickListener(v -> {
            binding.buttonIncome.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.color_main_30)));
            binding.buttonExpense.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));
            binding.buttonSaving.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));

            type = "INCOME";
        });
        binding.buttonSaving.setOnClickListener(v -> {
            binding.buttonSaving.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.color_main_30)));
            binding.buttonExpense.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));
            binding.buttonIncome.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));

            type = "SAVING";
        });

        binding.ivDone.setOnClickListener(v -> {
            String name = binding.edtName.getText().toString();
            String budget = binding.edtBudget.getText().toString();
            if (name.isEmpty() || budget.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            long budgetLong = -1;
            try {
                budgetLong = Long.parseLong(budget);
            } catch (Exception e) {
                Toast.makeText(requireContext(), "Vui lòng nhập đúng định dạng số", Toast.LENGTH_SHORT).show();
                return;
            }
            if (budgetLong <= 0) {
                Toast.makeText(requireContext(), "Vui lòng nhập ngân sách hơn 0", Toast.LENGTH_SHORT).show();
                return;
            }
            Category category = new Category(name, type, budgetLong);
            viewModel.insertCategory(category);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity) requireActivity()).showMenu();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}

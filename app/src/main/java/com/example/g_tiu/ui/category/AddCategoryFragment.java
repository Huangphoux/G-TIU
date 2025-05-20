package com.example.g_tiu.ui.category;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import java.text.NumberFormat;
import java.util.Locale;

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
        if (getArguments() != null && requireArguments().containsKey("category")) {
            viewModel.setCategory((Category) requireArguments().getSerializable("category"));
        }
        binding.ivBack.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).showMenu();
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });
        viewModel.getCategoryLiveData().observe(getViewLifecycleOwner(), result -> {
            binding.tvTitle.setText("Sửa phân loại");
            binding.edtName.setText(result.getName());
            binding.edtBudget.setText(String.valueOf(result.getBudget()));
            if (result.getType().equalsIgnoreCase("expense")) {
                binding.buttonExpense.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.color_main_30)));
                binding.buttonIncome.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));
                binding.buttonSaving.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));
                type = "EXPENSE";
            } else if (result.getType().equalsIgnoreCase("income")) {
                binding.buttonIncome.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.color_main_30)));
                binding.buttonExpense.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));
                binding.buttonSaving.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));
                type = "INCOME";
            } else if (result.getType().equalsIgnoreCase("saving")) {
                binding.buttonSaving.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.color_main_30)));
                binding.buttonExpense.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));
                binding.buttonIncome.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));
                type = "SAVING";
            }
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
        viewModel.getUpdateResultLiveData().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(requireContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                ((MainActivity) requireActivity()).showMenu();
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            } else {
                Toast.makeText(requireContext(), "Sủa thất bại", Toast.LENGTH_SHORT).show();
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
        binding.edtBudget.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    binding.edtBudget.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll(",", "");
                    try {
                        long parsed = Long.parseLong(cleanString);
                        String formatted = NumberFormat.getInstance(Locale.US).format(parsed);
                        current = formatted;
                        binding.edtBudget.setText(formatted);
                        binding.edtBudget.setSelection(formatted.length());
                    } catch (NumberFormatException e) {
                        Log.e("GT456_x", "Error: " + e);
                    }

                    binding.edtBudget.addTextChangedListener(this);
                }
            }
        });

        binding.ivDone.setOnClickListener(v -> {
            String name = binding.edtName.getText().toString();
            String budget = binding.edtBudget.getText().toString().replace(",", "");
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
            if (viewModel.getCategoryLiveData().getValue() != null) {
                Category category = new Category(viewModel.getCategoryLiveData().getValue().getId(), name, type, budgetLong);
                viewModel.updateCategory(category);
            } else {
                Category category = new Category(name, type, budgetLong);
                viewModel.insertCategory(category);
            }
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

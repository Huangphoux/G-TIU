package com.example.g_tiu.ui.report.statistic;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.g_tiu.R;
import com.example.g_tiu.databinding.FragmentStatisticBinding;
import com.example.g_tiu.item.Keyword;
import com.example.g_tiu.item.Transactions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StatisticFragment extends Fragment {

    private FragmentStatisticBinding binding;
    private StatisticViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStatisticBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(StatisticViewModel.class);
        viewModel.init(requireActivity().getApplication());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getTransactionsLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result == null) {
                binding.tvAverage.setText("---");
                return;
            }
            long expense = 0L;
            long income = 0L;
            long saving = 0L;

            for (Transactions transaction : result) {
                if (transaction.getCategory() != null) {
                    switch (transaction.getCategory().getType().toLowerCase(Locale.ROOT)) {
                        case "expense":
                            expense += transaction.getAmount();
                            break;
                        case "income":
                            income += transaction.getAmount();
                            break;
                        case "saving":
                            saving += transaction.getAmount();
                            break;
                    }
                }
            }
            long total = expense + income + saving;
            if (total <= 0) {
                binding.tvAverage.setText("---");
                return;
            }

            double average = (double) (expense - income - saving) / total;
            average *= 100;

            // Format: tối đa 2 chữ số sau dấu phẩy, không hiện .00 nếu là số nguyên
            DecimalFormat df = new DecimalFormat("0.##");
            String percent = df.format(average);
            binding.tvAverage.setText(percent + "%");
        });
        viewModel.getTagLiveData().observe(getViewLifecycleOwner(), result -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    result
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spinner.setAdapter(adapter);
            binding.spinner.setSelection(0);
            binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        viewModel.clearKeyword();
                        return;
                    }
                    viewModel.getAllTransactions(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // do nothing
                }
            });
        });
        updateMonthYearDisplay();

        binding.ivNext.setOnClickListener(v -> {
            viewModel.currentDate = viewModel.currentDate.plusMonths(1);
            updateMonthYearDisplay();
            viewModel.getAllTransactions();
        });
        binding.ivPrev.setOnClickListener(v -> {
            viewModel.currentDate = viewModel.currentDate.minusMonths(1);
            updateMonthYearDisplay();
            viewModel.getAllTransactions();
        });

        viewModel.getTags();
    }

    private void updateMonthYearDisplay() {
        String monthOfYear = "Tháng " + viewModel.currentDate.getMonthValue() + ", " + viewModel.currentDate.getYear();
        binding.tvMonthYear.setText(monthOfYear);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}

package com.example.g_tiu.ui.chart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.g_tiu.R;
import com.example.g_tiu.databinding.FragmentChartBinding;
import com.example.g_tiu.item.Category;
import com.example.g_tiu.item.Transactions;
import com.example.g_tiu.ui.category.CategoryViewModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ChartFragment extends Fragment {
    private FragmentChartBinding binding;
    private CategoryViewModel viewModel;
    private final List<Category> categories = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChartBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        viewModel.init(requireActivity().getApplication());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result == null || result.isEmpty()) return;
            List<Category> expenseList = result.stream()
                    .filter(c -> "expense".equalsIgnoreCase(c.getType()))
                    .collect(Collectors.toList());

            List<Category> incomeList = result.stream()
                    .filter(c -> "income".equalsIgnoreCase(c.getType()))
                    .collect(Collectors.toList());

            List<Category> savingList = result.stream()
                    .filter(c -> "saving".equalsIgnoreCase(c.getType()))
                    .collect(Collectors.toList());

            categories.addAll(expenseList);
            categories.addAll(incomeList);
            categories.addAll(savingList);

        });
        viewModel.getTransactionsLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result == null) return;
            for (Transactions transactions : result) {
                for (Category category : categories) {
                    if (category.getId() == transactions.getCategoryId()) {
                        category.setActual(category.getActual() + transactions.getAmount());
                    }
                }
            }

            setupPieChart();
        });

        viewModel.getAll();
    }

    private void setupPieChart() {
        binding.anyChartView.setProgressBar(binding.progressBar);

        Pie pie = AnyChart.pie();

        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {

            }
        });

        List<DataEntry> data = new ArrayList<>();
        for (Category category : categories) {
            data.add(new ValueDataEntry(category.getName(), category.getActual()));
        }

        pie.data(data);

        pie.title("Báo cáo tổng quan");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Danh mục")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        binding.anyChartView.setChart(pie);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

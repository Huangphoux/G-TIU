package com.example.g_tiu.ui.report.chart;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.g_tiu.databinding.FragmentChartBinding;
import com.example.g_tiu.item.Category;
import com.example.g_tiu.item.Transactions;
import com.example.g_tiu.ui.LineChartActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChartFragment extends Fragment {
    private FragmentChartBinding binding;
    private ChartViewModel viewModel;
    private final List<Category> categories = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChartBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ChartViewModel.class);
        viewModel.init(requireActivity().getApplication());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateMonthYearDisplay();
        binding.ivNext.setOnClickListener(v -> {
            viewModel.currentDate = viewModel.currentDate.plusMonths(1);
            updateMonthYearDisplay();
            categories.clear();
            binding.layoutAnyChartView.removeAllViews();
            viewModel.getAll();
        });
        binding.ivPrev.setOnClickListener(v -> {
            viewModel.currentDate = viewModel.currentDate.minusMonths(1);
            updateMonthYearDisplay();
            categories.clear();
            binding.layoutAnyChartView.removeAllViews();
            viewModel.getAll();
        });
        binding.cardViewLineChart.setOnClickListener(v -> {
            startActivity(new Intent(requireActivity(), LineChartActivity.class));
        });

        viewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result == null || result.isEmpty()) return;
            categories.clear();
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
            if (result.isEmpty()) {
                binding.tvEmpty.setVisibility(View.VISIBLE);
                binding.layoutAnyChartView.setVisibility(View.GONE);
                return;
            }
            binding.tvEmpty.setVisibility(View.GONE);
            binding.layoutAnyChartView.setVisibility(View.VISIBLE);
            for (Transactions transactions : result) {
                for (Category category : categories) {
                    if (category.getId() == transactions.getCategoryId()) {
                        category.setActual(category.getActual() + transactions.getAmount());
                    }
                }
            }

            setupPieChart();
        });

        categories.clear();
        binding.layoutAnyChartView.removeAllViews();
        viewModel.getAll();
    }

    private void updateMonthYearDisplay() {
        String monthOfYear = "Tháng " + viewModel.currentDate.getMonthValue() + ", " + viewModel.currentDate.getYear();
        binding.tvMonthYear.setText(monthOfYear);
    }

    private void setupPieChart() {
        AnyChartView anyChartView = new AnyChartView(requireContext());
        anyChartView.setProgressBar(binding.progressBar);

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

        anyChartView.setChart(pie);
        binding.layoutAnyChartView.addView(anyChartView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

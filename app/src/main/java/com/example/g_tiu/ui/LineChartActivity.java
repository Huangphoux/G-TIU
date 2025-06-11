package com.example.g_tiu.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.g_tiu.databinding.ActivityLineChartBinding;
import com.example.g_tiu.item.Transactions;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;
import kotlin.Triple;

public class LineChartActivity extends AppCompatActivity {

    private ActivityLineChartBinding binding;
    private LineChartViewModel viewModel;
    private int count = 2;
    private List<Pair<String, Triple<Number, Number, Number>>> dataChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataChart = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(LineChartViewModel.class);
        viewModel.init(getApplication());
        binding = ActivityLineChartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel.getTransactionsLiveData().observe(this, result -> {
            if (result == null) {
                binding.layoutAnyChartView.setVisibility(View.GONE);
                return;
            }
            dataChart.clear();
            binding.layoutAnyChartView.removeAllViews();
            binding.layoutAnyChartView.setVisibility(View.VISIBLE);
            Number expense = 0;
            Number income = 0;
            Number saving = 0;
            for (Pair<String, List<Transactions>> pair : result) {
                for (Transactions transaction : pair.getSecond()) {
                    if (transaction.getCategory().getType().equalsIgnoreCase("expense")) {
                        expense = transaction.getAmount();
                    } else if (transaction.getCategory().getType().equalsIgnoreCase("income")) {
                        income = transaction.getAmount();
                    } else if (transaction.getCategory().getType().equalsIgnoreCase("saving")) {
                        saving = transaction.getAmount();
                    }
                }
                dataChart.add(new Pair<>(pair.getFirst(), new Triple<>(
                        expense.longValue() / 1000,
                        income.longValue() / 1000,
                        saving.longValue() / 1000
                )));
            }
            loadChart();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        String[] months = {"2 tháng", "3 tháng", "6 tháng", "9 tháng"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                months
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerTime.setAdapter(adapter);
        binding.spinnerTime.setSelection(0);
        binding.spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    count = 2;
                } else if (position == 1) {
                    count = 3;
                } else if (position == 2) {
                    count = 6;
                } else if (position == 3) {
                    count = 9;
                }
                viewModel.getAll(count);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
    }

    private void loadChart() {
        binding.layoutAnyChartView.removeAllViews();
        AnyChartView anyChartView = new AnyChartView(this);
        anyChartView.setProgressBar(binding.progressBar);
        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 30d, 10d);//  3 bottom ,4 right

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Biểu đồ đường");

        cartesian.yAxis(0).title("Số tiền (x1.000 VNĐ)");
        cartesian.xAxis(0).labels().padding(5d, 0d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String x = dataChart.get(i).getFirst();
            Triple<Number, Number, Number> y = dataChart.get(i).getSecond();

            seriesData.add(new CustomDataEntry(x, y.getFirst(), y.getSecond(), y.getThird()));
        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Thu nhập");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("Chi tiêu");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series3 = cartesian.line(series3Mapping);
        series3.name("Tiết kiệm");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(12d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
        binding.layoutAnyChartView.addView(anyChartView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private static class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }
}
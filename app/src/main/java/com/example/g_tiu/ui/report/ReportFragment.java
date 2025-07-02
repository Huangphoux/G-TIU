package com.example.g_tiu.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.g_tiu.databinding.FragmentReportBinding;
import com.example.g_tiu.ui.report.chart.ChartFragment;
import com.example.g_tiu.ui.report.statistic.StatisticFragment;

import java.util.List;

public class ReportFragment extends Fragment {

    private FragmentReportBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReportBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ReportPagerAdapter adapter = new ReportPagerAdapter(this, List.of(
                ChartFragment.getInstance(),
                StatisticFragment.getInstance()
        ));
        binding.viewPager.setAdapter(adapter);

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                Fragment fragment = adapter.getFragmentAt(position);
                if (fragment instanceof ChartFragment) {
                    ((ChartFragment) fragment).fetchData();
                } else if (fragment instanceof StatisticFragment) {
                    ((StatisticFragment) fragment).fetchData();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}


package com.example.g_tiu.ui.report;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.g_tiu.ui.report.chart.ChartFragment;
import com.example.g_tiu.ui.report.statistic.StatisticFragment;

public class ReportPagerAdapter extends FragmentStateAdapter {

    public ReportPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new ChartFragment();
        } else {
            return new StatisticFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

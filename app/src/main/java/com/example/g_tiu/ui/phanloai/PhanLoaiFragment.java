package com.example.g_tiu.ui.phanloai;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.g_tiu.databinding.FragmentPhanloaiBinding;

public class PhanLoaiFragment extends Fragment {

    private FragmentPhanloaiBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PhanLoaiViewModel phanLoaiViewModel =
                new ViewModelProvider(this).get(PhanLoaiViewModel.class);

        binding = FragmentPhanloaiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPhanloai;
        phanLoaiViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
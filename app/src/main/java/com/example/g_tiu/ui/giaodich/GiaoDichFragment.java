package com.example.g_tiu.ui.giaodich;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.g_tiu.databinding.FragmentGiaodichBinding;

public class GiaoDichFragment extends Fragment {

    private FragmentGiaodichBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GiaoDichViewModel giaoDichViewModel =
                new ViewModelProvider(this).get(GiaoDichViewModel.class);

        binding = FragmentGiaodichBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGiaodich;
        giaoDichViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
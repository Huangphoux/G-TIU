package com.example.g_tiu.ui.baocao;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.g_tiu.databinding.FragmentBaocaoBinding;

public class BaoCaoFragment extends Fragment {

    private FragmentBaocaoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BaoCaoViewModel baoCaoViewModel =
                new ViewModelProvider(this).get(BaoCaoViewModel.class);

        binding = FragmentBaocaoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textBaocao;
        baoCaoViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
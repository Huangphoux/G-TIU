package com.example.g_tiu.ui.transactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.g_tiu.MainActivity;
import com.example.g_tiu.databinding.FragmentFromTransactionsBinding;
import com.example.g_tiu.databinding.FragmentTransactionsBinding;

public class FromTransactionsFragment extends Fragment {

    private FragmentFromTransactionsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFromTransactionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.ivBack.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).showMenu();
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
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

package com.example.g_tiu.ui.keyword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.g_tiu.adapter.KeyAdapter;
import com.example.g_tiu.databinding.FragmentKeyBinding;

import java.util.Collections;

public class KeyFragment extends Fragment {

    private FragmentKeyBinding binding;
    private KeyViewModel viewModel;
    private KeyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentKeyBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(KeyViewModel.class);
        viewModel.init(requireActivity().getApplication());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new KeyAdapter(Collections.emptyList(), keyword -> {
            // TODO: Handle item click
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.ivBack.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });
        viewModel.getAll();

        viewModel.getKeywordLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result == null || result.isEmpty()) {
                return;
            }
            adapter.setKeywords(result);
        });
    }
}

package com.example.g_tiu.ui.transactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.lifecycle.ViewModelProvider;

import com.example.g_tiu.MainActivity;
import com.example.g_tiu.R;
import com.example.g_tiu.adapter.TransactionsAdapter;
import com.example.g_tiu.databinding.FragmentTransactionsBinding;
import com.example.g_tiu.item.Transactions;

public class TransactionsFragment extends Fragment implements TransactionsAdapter.OnClickTransaction {

    private FragmentTransactionsBinding binding;
    private TransactionsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTransactionsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);
        viewModel.init(requireActivity().getApplication());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.fabAddTransactions.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).hideMenu();
            Navigation.findNavController(view).navigate(R.id.action_navigation_transactions_to_navigation_from_transactions);
        });

        viewModel.getTransactionsLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result.isEmpty()) {
                binding.tvEmpty.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
            } else {
                binding.tvEmpty.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.recyclerView.setAdapter(new TransactionsAdapter(result, this));
            }
        });

        viewModel.getAll();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onClick(Transactions transactions) {

    }
}

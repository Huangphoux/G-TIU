package com.example.g_tiu.ui.transactions;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.g_tiu.MainActivity;
import com.example.g_tiu.R;
import com.example.g_tiu.adapter.TransactionsAdapter;
import com.example.g_tiu.databinding.FragmentTransactionsBinding;
import com.example.g_tiu.helper.TransactionSorter;
import com.example.g_tiu.item.Keyword;
import com.example.g_tiu.item.Transactions;
import com.example.g_tiu.ui.tag.TagFragment;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class TransactionsFragment extends Fragment
        implements TransactionsAdapter.OnClickTransaction, TagFragment.OnTagClickListener {

    private FragmentTransactionsBinding binding;
    private TransactionsViewModel viewModel;
    private LocalDate currentDate = LocalDate.now();

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
        updateMonthYearDisplay();
        binding.fabAddTransactions.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).hideMenu();
            Navigation.findNavController(view).navigate(R.id.action_navigation_transactions_to_navigation_from_transactions);
        });
        binding.tagMore.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("is_select", true);
            bundle.putSerializable("on_listener", this);
            findNavController(view).navigate(R.id.action_navigation_transactions_to_navigation_tag, bundle);
        });
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.applyFilterKeySearch(editable.toString().trim());
            }
        });
        binding.ivNext.setOnClickListener(v -> {
            currentDate = currentDate.plusMonths(1);
            updateMonthYearDisplay();
            fetchData();
        });
        binding.ivPrev.setOnClickListener(v -> {
            currentDate = currentDate.minusMonths(1);
            updateMonthYearDisplay();
            fetchData();
        });
        viewModel.getTransactionsLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result == null) return;
            if (result.isEmpty()) {
                binding.tvEmpty.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
            } else {
                TransactionSorter.sortTransactions(result);
                binding.tvEmpty.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                binding.recyclerView.setAdapter(new TransactionsAdapter(result, this));
            }

            calTotal(result);
        });
        viewModel.getKeyFilterLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result == null) {
                binding.tagText.setVisibility(View.INVISIBLE);
                binding.tagClose.setVisibility(View.GONE);
                binding.tagMore.setVisibility(View.VISIBLE);
            } else {
                binding.tagText.setVisibility(View.VISIBLE);
                binding.tagClose.setVisibility(View.VISIBLE);
                binding.tagMore.setVisibility(View.GONE);
                binding.tagClose.setOnClickListener(v -> {
                    viewModel.applyFilterKeyword(null);
                });
                binding.tagText.setText(result.getName());
            };
        });
        fetchData();
    }

    private void calTotal(List<Transactions> result) {
        long totalExpenses = 0;
        long totalIncome = 0;
        for (Transactions transactions : result) {
            if (transactions.getCategory().getType().equalsIgnoreCase("expense")) {
                totalExpenses += transactions.getAmount();
            } else if (transactions.getCategory().getType().equalsIgnoreCase("income")) {
                totalIncome += transactions.getAmount();
            }
        }
        try {
            long parsed = totalIncome - totalExpenses;
            String formatted = NumberFormat.getInstance(Locale.US).format(parsed);
            binding.tvCount.setText(formatted);
        } catch (NumberFormatException e) {
            Log.e("GT456_x", "Error: " + e);
        }
    }

    private void fetchData() {
        viewModel.getAll(currentDate.getYear(), currentDate.getMonthValue());
    }

    private void updateMonthYearDisplay() {
        String monthOfYear = "Tháng " + currentDate.getMonthValue() + ", " + currentDate.getYear();
        binding.tvMonthYear.setText(monthOfYear);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onClick(Transactions transactions) {

    }

    @Override
    public void onClickListener(Keyword keyword) {
        viewModel.applyFilterKeyword(keyword);
    }
}

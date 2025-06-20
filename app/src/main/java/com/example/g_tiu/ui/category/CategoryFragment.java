package com.example.g_tiu.ui.category;

import static androidx.navigation.Navigation.findNavController;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_tiu.MainActivity;
import com.example.g_tiu.R;
import com.example.g_tiu.adapter.CategoryAdapter;
import com.example.g_tiu.databinding.FragmentCategoryBinding;
import com.example.g_tiu.item.Category;
import com.example.g_tiu.item.Transactions;
import com.google.android.material.snackbar.Snackbar;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    private CategoryAdapter adapter;
    private List<Category> categories;

    private CategoryViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        viewModel.init(requireActivity().getApplication());
        return binding.getRoot();
    }

    private void updateMonthYearDisplay() {
        String monthOfYear = "Tháng " + viewModel.currentDate.getMonthValue() + ", " + viewModel.currentDate.getYear();
        binding.tvMonthYear.setText(monthOfYear);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateMonthYearDisplay();

        binding.ivNext.setOnClickListener(v -> {
            viewModel.currentDate = viewModel.currentDate.plusMonths(1);
            updateMonthYearDisplay();
            for (Category category : categories) {
                category.setActual(0);
            }
            viewModel.getAllTransactions();
        });
        binding.ivPrev.setOnClickListener(v -> {
            viewModel.currentDate = viewModel.currentDate.minusMonths(1);
            updateMonthYearDisplay();
            for (Category category : categories) {
                category.setActual(0);
            }
            viewModel.getAllTransactions();
        });

        viewModel.getDeleteResult().observe(getViewLifecycleOwner(), result -> {
            if (result == null) return;
            viewModel.clearDeleteResult();
            Snackbar snackbar;
            if (result) {
                viewModel.getAll();
                snackbar = Snackbar
                        .make(binding.recyclerView, "Đã xoá", Snackbar.LENGTH_SHORT);
            } else {
                snackbar = Snackbar
                        .make(binding.recyclerView, "Có lỗi xảy ra", Snackbar.LENGTH_SHORT);
            }
            snackbar.setAnchorView(binding.recyclerView);
            snackbar.show();
        });

        viewModel.getTransactionsLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result == null) return;
            long totalExpenses = 0;
            long totalIncome = 0;
            for (Transactions transactions : result) {
                if (transactions.getCategory().getType().equalsIgnoreCase("expense")) {
                    totalExpenses += transactions.getAmount();
                } else if (transactions.getCategory().getType().equalsIgnoreCase("income")) {
                    totalIncome += transactions.getAmount();
                }

                for (Category category : categories) {
                    long lastTime = 0;
                    if (category.getId() == transactions.getCategoryId()) {
                        if (lastTime < transactions.getCreateTime()) {
                            lastTime = transactions.getCreateTime();
                        }
                        category.setActual(category.getActual() + transactions.getAmount());
                    }
                    category.setLastTime(lastTime);
                }
            }
            adapter.notifyDataSetChanged();
            try {
                long parsed = totalIncome - totalExpenses;
                String formatted = NumberFormat.getInstance(Locale.US).format(parsed);
                binding.tvCount.setText(formatted);
            } catch (NumberFormatException e) {
                Log.e("GT456_x", "Error: " + e);
            }
        });

        viewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result == null) return;
            List<Category> expenseList = result.stream()
                    .filter(c -> "expense".equalsIgnoreCase(c.getType()))
                    .collect(Collectors.toList());

            long totalExpenseBudget = expenseList.stream()
                    .mapToLong(Category::getBudget)
                    .sum();

            List<Category> incomeList = result.stream()
                    .filter(c -> "income".equalsIgnoreCase(c.getType()))
                    .collect(Collectors.toList());

            long totalIncomeBudget = incomeList.stream()
                    .mapToLong(Category::getBudget)
                    .sum();

            List<Category> savingList = result.stream()
                    .filter(c -> "saving".equalsIgnoreCase(c.getType()))
                    .collect(Collectors.toList());

            long totalSavingBudget = savingList.stream()
                    .mapToLong(Category::getBudget)
                    .sum();

            categories = new ArrayList<>();
            categories.add(new Category(-1, "expenses", true, totalExpenseBudget));
            categories.addAll(expenseList);
            categories.add(new Category(-1, "income", true, totalIncomeBudget));
            categories.addAll(incomeList);
            categories.add(new Category(-1, "saving", true, totalSavingBudget));
            categories.addAll(savingList);
            adapter.setCategories(categories);
        });

        categories = new ArrayList<>();
        categories.add(new Category(-1, "expenses", true));
        categories.add(new Category(-1, "income", true));
        categories.add(new Category(-1, "saving", true));

        adapter = new CategoryAdapter(categories, new CategoryAdapter.OnCategoryListener() {
            @Override
            public void onClickItemCategory(Category category) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("category", category);
                findNavController(view).navigate(R.id.action_navigation_category_to_navigation_from_transactions, bundle);
                ((MainActivity) requireActivity()).hideMenu();
            }

            @Override
            public void onLongClickItemCategory(Category category) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("category", category);

                ((MainActivity) requireActivity()).hideMenu();
                findNavController(view).navigate(R.id.action_navigation_category_to_navigation_add_category, bundle);
            }
        });
        viewModel.getAll();

        swipeToRemove();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.fabAddCategory.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).hideMenu();
            findNavController(view).navigate(R.id.action_navigation_category_to_navigation_add_category);
        });
    }

    private void swipeToRemove() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int viewType = viewHolder.getItemViewType();
                if (viewType == CategoryAdapter.TYPE_HEADER) {
                    return 0;
                } else {
                    return super.getSwipeDirs(recyclerView, viewHolder);
                }
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Category deletedItem = categories.get(viewHolder.getAdapterPosition());

                int position = viewHolder.getAdapterPosition();

                new AlertDialog.Builder(requireContext())
                        .setMessage("Bạn có chắc chắn muốn xóa " + deletedItem.getName() + "?")
                        .setCancelable(false)
                        .setPositiveButton("Có", (dialog, which) -> {
                            viewModel.deleteCategory(deletedItem);
                            Snackbar snackbar = Snackbar
                                    .make(binding.recyclerView, "Đang xoá" + deletedItem.getName(), Snackbar.LENGTH_SHORT);
                            snackbar.setAnchorView(binding.recyclerView);

                            snackbar.show();
                        })
                        .setNegativeButton("Không", (dialog, which) -> adapter.notifyItemChanged(position))
                        .create()
                        .show();

            }
        }).attachToRecyclerView(binding.recyclerView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

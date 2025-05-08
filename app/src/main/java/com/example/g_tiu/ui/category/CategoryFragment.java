package com.example.g_tiu.ui.category;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_tiu.MainActivity;
import com.example.g_tiu.R;
import com.example.g_tiu.adapter.CategoryAdapter;
import com.example.g_tiu.databinding.FragmentCategoryBinding;
import com.example.g_tiu.item.Category;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    private CategoryAdapter adapter;
    private List<Category> categories;
    private LocalDate currentDate = LocalDate.now();

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
        String monthOfYear = "Tháng " + currentDate.getMonthValue() + ", " + currentDate.getYear();
        binding.tvMonthYear.setText(monthOfYear);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateMonthYearDisplay();

        binding.ivNext.setOnClickListener(v -> {
            currentDate = currentDate.plusMonths(1);
            updateMonthYearDisplay();
        });
        binding.ivPrev.setOnClickListener(v -> {
            currentDate = currentDate.minusMonths(1);
            updateMonthYearDisplay();
        });

        viewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result == null || result.isEmpty()) return;
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

        adapter = new CategoryAdapter(categories);
        viewModel.getAll();

        swipeToRemove();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.fabAddCategory.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).hideMenu();
            Navigation.findNavController(view).navigate(R.id.action_navigation_category_to_navigation_add_category);
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
                            viewModel.getAll();

                            Snackbar snackbar = Snackbar
                                    .make(binding.recyclerView, "Deleted " + deletedItem.getName(), Snackbar.LENGTH_SHORT);
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

package com.example.g_tiu.ui.transactions;

import static androidx.navigation.Navigation.findNavController;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.g_tiu.MainActivity;
import com.example.g_tiu.R;
import com.example.g_tiu.adapter.CategoryAdapter;
import com.example.g_tiu.databinding.FragmentFromTransactionsBinding;
import com.example.g_tiu.item.Category;
import com.example.g_tiu.item.Keyword;
import com.example.g_tiu.ui.tag.TagFragment;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import kotlin.collections.ArrayDeque;

public class FromTransactionsFragment extends Fragment implements TagFragment.OnTagClickListener {

    private FragmentFromTransactionsBinding binding;
    private TransactionsViewModel viewModel;

    private LocalDate currentDate = LocalDate.now();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFromTransactionsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(TransactionsViewModel.class);
        viewModel.init(requireActivity().getApplication());
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("category")) {
            viewModel.setCategory((Category) requireArguments().getSerializable("category"));
        }
        binding.ivBack.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).showMenu();
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });
        binding.cardViewKeyWord.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("on_listener", this);
            findNavController(view).navigate(R.id.action_navigation_from_transactions_to_navigation_tag, bundle);
        });
        binding.cardViewCategory.setOnClickListener(v -> {
            CategoryBottomSheet categoryBottomSheet = new CategoryBottomSheet();
            categoryBottomSheet.setOnCategoryListener(new CategoryAdapter.OnCategoryListener() {
                @Override
                public void onClickItemCategory(Category category) {
                    viewModel.setCategory(category);
                    categoryBottomSheet.dismiss();
                }

                @Override
                public void onLongClickItemCategory(Category category) {

                }
            });
            categoryBottomSheet.show(getChildFragmentManager(), "CategoryBottomSheet");
        });
        binding.edtAmount.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    binding.edtAmount.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll(",", "");
                    try {
                        long parsed = Long.parseLong(cleanString);
                        String formatted = NumberFormat.getInstance(Locale.US).format(parsed);
                        current = formatted;
                        binding.edtAmount.setText(formatted);
                        binding.edtAmount.setSelection(formatted.length());
                    } catch (NumberFormatException e) {
                        Log.e("GT456_x", "Error: " + e);
                    }

                    binding.edtAmount.addTextChangedListener(this);
                }
            }
        });
        binding.ivNext.setOnClickListener(v -> {
            currentDate = currentDate.plusDays(1);
            updateMonthYearDisplay();
        });
        binding.ivPrev.setOnClickListener(v -> {
            currentDate = currentDate.minusDays(1);
            updateMonthYearDisplay();
        });
        binding.tvDayMonthYear.setOnClickListener(v1 -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(),
                    (v2, selectedYear, selectedMonth, selectedDay) -> {
                        currentDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay);
                        updateMonthYearDisplay();
                    },
                    currentDate.getYear(), currentDate.getMonthValue() - 1, currentDate.getDayOfMonth()
            );
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });
        binding.ivDone.setOnClickListener(v -> {
            String amount = binding.edtAmount.getText().toString();
            if (TextUtils.isEmpty(amount)) {
                binding.edtAmount.setError("Vui lòng nhập số tiền");
                return;
            }
            String note = binding.edtNote.getText().toString();
            if (viewModel.getCategoryLiveData().getValue() == null) {
                Toast.makeText(requireContext(), "Vui lòng chọn danh mục", Toast.LENGTH_SHORT).show();
                return;
            }
            if (viewModel.getTransactionsResult().getValue() != null) {
                viewModel.updateTransaction(currentDate, amount, note);
            } else {
                viewModel.addTransaction(currentDate, amount, note);
            }
        });
        binding.ivDelete.setOnClickListener(v -> new AlertDialog.Builder(requireContext())
                .setMessage("Bạn có chắc chắn muốn xóa giao dịch này?")
                .setCancelable(false)
                .setPositiveButton("Có", (dialog, which) -> {
                    viewModel.deleteTransactions();
                    Toast.makeText(requireContext(), "Đã xoá", Toast.LENGTH_SHORT).show();
                    ((MainActivity) requireActivity()).showMenu();
                    requireActivity().getOnBackPressedDispatcher().onBackPressed();
                })
                .setNegativeButton("Không", (dialog, which) -> {
                })
                .create()
                .show());
        updateMonthYearDisplay();
        viewModel.getCategoryLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                binding.layoutSelectCategory.setVisibility(View.GONE);
                binding.layoutCategory.setVisibility(View.VISIBLE);

                binding.tvCategoryType.setText(result.getType());
                binding.tvCategoryName.setText(result.getName());

                switch (result.getType().toLowerCase(Locale.ROOT)) {
                    case "expenses":
                        binding.ivIcon.setImageResource(R.drawable.icons_expenses);
                        break;
                    case "income":
                        binding.ivIcon.setImageResource(R.drawable.icons_income);
                        break;
                    case "saving":
                        binding.ivIcon.setImageResource(R.drawable.icons_saving);
                        break;
                }
            } else {
                binding.layoutSelectCategory.setVisibility(View.VISIBLE);
                binding.layoutCategory.setVisibility(View.GONE);
            }
        });
        viewModel.getInsertTransactions().observe(getViewLifecycleOwner(), result -> {
            if (result == null || !result) {
                Toast.makeText(requireContext(), "Thêm giao dịch thất bại", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Thêm giao dịch thành công", Toast.LENGTH_SHORT).show();
                ((MainActivity) requireActivity()).showMenu();
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
        viewModel.getUpdateTransactions().observe(getViewLifecycleOwner(), result -> {
            if (result == null || !result) {
                Toast.makeText(requireContext(), "Sửa giao dịch thất bại", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Sửa giao dịch thành công", Toast.LENGTH_SHORT).show();
                ((MainActivity) requireActivity()).showMenu();
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
        viewModel.getTransactionsResult().observe(getViewLifecycleOwner(), result -> {
            if (result == null) return;

            binding.tvTitle.setText("Sửa giao dịch");
            binding.ivDelete.setVisibility(View.VISIBLE);
            binding.edtAmount.setText(String.valueOf(result.getAmount()));
            binding.edtNote.setText(result.getNote());

            binding.layoutSelectCategory.setVisibility(View.GONE);
            binding.layoutCategory.setVisibility(View.VISIBLE);

            if (result.getCategory() != null){
                    viewModel.setCategory(result.getCategory());

            }
//            binding.tvCategoryType.setText(result.getCategory().getType());
//            binding.tvCategoryName.setText(result.getCategory().getName());

            currentDate = LocalDate.parse(result.getDate());

            updateMonthYearDisplay();

            if (TextUtils.isEmpty(result.getKeys())) {
                return;
            }
            String[] keys = result.getKeys().split(",");
            binding.flexboxLayout.setVisibility(View.VISIBLE);
            binding.tvEmptyKeyword.setVisibility(View.GONE);
            List<Keyword> keywords = new ArrayDeque<>();

            for (String key : keys) {
                key = key.trim();
                if (TextUtils.isEmpty(key)) continue;
                for (Keyword keyword : viewModel.keywordList) {
                    if (keyword.getName().equals(key)) {
                        keywords.add(keyword);
                        viewModel.addKeyword(keyword, true);
                    }
                }
            }
            addKeyword(keywords);
        });
        viewModel.getKeywordLiveData().observe(getViewLifecycleOwner(), result -> {
            binding.flexboxLayout.setVisibility(View.VISIBLE);
            binding.tvEmptyKeyword.setVisibility(View.GONE);

            binding.flexboxLayout.removeAllViews();
            addKeyword(result);
        });
        viewModel.setArguments(getArguments());
    }

    private void addKeyword(List<Keyword> keywords) {
        for (Keyword key : keywords) {
            View tagView = LayoutInflater.from(requireContext()).inflate(R.layout.tag_item, binding.flexboxLayout, false);

            TextView textView = tagView.findViewById(R.id.tagText);
            AppCompatImageView closeButton = tagView.findViewById(R.id.tag_close);

            textView.setText(key.getName());

            closeButton.setOnClickListener(v -> {
                binding.flexboxLayout.removeView(tagView);
                viewModel.removeKeyword(key);

                if (binding.flexboxLayout.getChildCount() == 0) {
                    binding.flexboxLayout.setVisibility(View.GONE);
                    binding.tvEmptyKeyword.setVisibility(View.VISIBLE);
                } else {
                    binding.flexboxLayout.setVisibility(View.VISIBLE);
                    binding.tvEmptyKeyword.setVisibility(View.GONE);
                }
            });
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 8, 8, 8);
            tagView.setLayoutParams(params);

            binding.flexboxLayout.addView(tagView);
        }
    }

    private void updateMonthYearDisplay() {
        String monthOfYear = currentDate.getDayOfMonth() + " tháng " + currentDate.getMonthValue() + ", " + currentDate.getYear();
        binding.tvDayMonthYear.setText(monthOfYear);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            ((MainActivity) requireActivity()).showMenu();
        } catch (Exception ignored) {

        }
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onClickListener(Keyword keyword) {
        viewModel.addKeyword(keyword, false);
    }
}

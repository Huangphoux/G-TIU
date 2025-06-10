package com.example.g_tiu.ui.tag;

import static androidx.navigation.Navigation.findNavController;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.g_tiu.R;
import com.example.g_tiu.databinding.FragmentTagBinding;
import com.example.g_tiu.item.Keyword;

import java.io.Serializable;

public class TagFragment extends Fragment {

    private FragmentTagBinding binding;
    private TagViewModel viewModel;
    private OnTagClickListener onTagClickListener;
    private boolean isSelect;

    public interface OnTagClickListener extends Serializable {
        void onClickListener(Keyword keyword);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTagBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(TagViewModel.class);
        viewModel.init(requireActivity().getApplication());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            isSelect = getArguments().getBoolean("is_select");
            onTagClickListener = (OnTagClickListener) getArguments().getSerializable("on_listener");
        }
        binding.ivBack.setOnClickListener(v -> {
            findNavController(view).popBackStack();
        });
        if (isSelect) {
            binding.ivDone.setVisibility(View.GONE);
            binding.edtName.setVisibility(View.GONE);
            binding.flexboxLayout.setVisibility(View.VISIBLE);
            binding.tvTitle.setText("Chọn Tag");
        }
        viewModel.getInsertKeywordLiveData().observe(getViewLifecycleOwner(), result -> {
            if (onTagClickListener != null) {
                onTagClickListener.onClickListener(result);
            }
            findNavController(view).popBackStack();
        });
        viewModel.getKeywordLiveData().observe(getViewLifecycleOwner(), result -> {
            for (Keyword keyword : result) {
                TextView textView = new TextView(requireContext());
                textView.setText(keyword.getName());
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                textView.setPadding(40, 16, 40, 16);
                textView.setBackgroundResource(R.drawable.tag_background);

                textView.setOnClickListener(v -> {
                    if (onTagClickListener != null) {
                        onTagClickListener.onClickListener(keyword);
                    }
                    findNavController(view).popBackStack();
                });
                ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(8, 8, 8, 8);
                textView.setLayoutParams(params);

                binding.flexboxLayout.addView(textView);
            }
        });
        viewModel.getAll();

        binding.ivDone.setOnClickListener(v -> {
            String name = binding.edtName.getText().toString().trim();
            if (!TextUtils.isEmpty(name)) {
                viewModel.addKeyword(name.toUpperCase());
            } else {
                findNavController(view).popBackStack();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

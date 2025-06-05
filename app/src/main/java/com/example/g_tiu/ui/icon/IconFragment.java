package com.example.g_tiu.ui.icon;

import static androidx.navigation.Navigation.findNavController;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.g_tiu.R;
import com.example.g_tiu.databinding.FragmentColorBinding;
import com.example.g_tiu.databinding.FragmentIconBinding;
import com.example.g_tiu.helper.AppConstants;
import com.example.g_tiu.item.Color;
import com.example.g_tiu.item.IconModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IconFragment extends Fragment {

    private FragmentIconBinding binding;

    public interface OnIconListener extends Serializable {
        void onIconSelected(IconModel icon);
    }

    private OnIconListener onIconListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentIconBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("on_listener")) {
            onIconListener = (OnIconListener) getArguments().getSerializable("on_listener");
        }
        binding.ivBack.setOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        for (IconModel icon : AppConstants.getIcons()) {
            View colorView = LayoutInflater.from(requireContext()).inflate(R.layout.icon_item, binding.flexboxLayout, false);
            AppCompatImageView ivIcon = colorView.findViewById(R.id.ivIcon);
            ivIcon.setImageResource(icon.getResId());
            ivIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.black)));

            colorView.setOnClickListener(v -> {
                if (onIconListener != null) {
                    findNavController(view).popBackStack();
                    onIconListener.onIconSelected(icon);
                }
            });
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 8, 8, 8);
            colorView.setLayoutParams(params);

            binding.flexboxLayout.addView(colorView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

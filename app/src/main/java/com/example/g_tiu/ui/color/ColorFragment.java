package com.example.g_tiu.ui.color;

import static androidx.navigation.Navigation.findNavController;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.g_tiu.R;
import com.example.g_tiu.databinding.FragmentColorBinding;
import com.example.g_tiu.helper.AppConstants;
import com.example.g_tiu.item.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ColorFragment extends Fragment {

    private FragmentColorBinding binding;

    public interface OnColorListener extends Serializable {
        void onColorSelected(Color color);
    }

    private OnColorListener onColorListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentColorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("on_listener")) {
            onColorListener = (OnColorListener) getArguments().getSerializable("on_listener");
        }
        binding.ivBack.setOnClickListener(v -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        for (Color color : AppConstants.getColors()) {
            View colorView = LayoutInflater.from(requireContext()).inflate(R.layout.color_item, binding.flexboxLayout, false);
            CardView cardView = colorView.findViewById(R.id.card_view);
            cardView.setCardBackgroundColor(ColorStateList.valueOf(android.graphics.Color.parseColor(color.getHex())));

            colorView.setOnClickListener(v -> {
                if (onColorListener != null) {
                    findNavController(view).popBackStack();
                    onColorListener.onColorSelected(color);
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


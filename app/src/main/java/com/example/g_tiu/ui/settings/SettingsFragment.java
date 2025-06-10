package com.example.g_tiu.ui.settings;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.g_tiu.R;
import com.example.g_tiu.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private static final String PREFS_NAME = "app_settings";
    private static final String KEY_THEME_MODE = "theme_mode";

    private FragmentSettingsBinding binding;
    private SharedPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        prefs = requireContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int themeMode = prefs.getInt(KEY_THEME_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        switch (themeMode) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                binding.rbLight.setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                binding.rbDark.setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
            default:
                binding.rbSystem.setChecked(true);
                break;
        }

        binding.btnApply.setOnClickListener(view1 -> {
            SharedPreferences.Editor editor = prefs.edit();
            int checkedId = binding.rgTheme.getCheckedRadioButtonId();
            if (checkedId == R.id.rbLight) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putInt(KEY_THEME_MODE, AppCompatDelegate.MODE_NIGHT_NO);
            } else if (checkedId == R.id.rbDark) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putInt(KEY_THEME_MODE, AppCompatDelegate.MODE_NIGHT_YES);
            } else if (checkedId == R.id.rbSystem) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                editor.putInt(KEY_THEME_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            }
            editor.apply();
        });
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}

package com.example.g_tiu;

import android.os.Bundle;

import com.example.g_tiu.db_helper.loaiGD_DBHelper;
import com.example.g_tiu.item.loaiGD;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.g_tiu.databinding.ActivityMainBinding;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    loaiGD_DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_phanloai, R.id.navigation_giaodich, R.id.navigation_baocao)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        db = new loaiGD_DBHelper(this);

        String id;
        for (int i = 0; i < 1; i++) {
            id = UUID.randomUUID().toString();
            db.add(new loaiGD(id, "John Cena", "thunhap", 0.0));
        }
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

}
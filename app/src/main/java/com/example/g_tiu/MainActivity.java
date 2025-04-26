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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    loaiGD_DBHelper db;
    private List<loaiGD> listLGD = new ArrayList<>();

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
        listLGD.add(new loaiGD(UUID.randomUUID().toString(), "Ăn Uống", "chitieu", 0.0));
        listLGD.add(new loaiGD(UUID.randomUUID().toString(), "Tạp Hoá", "chitieu", 0.0));
        listLGD.add(new loaiGD(UUID.randomUUID().toString(), "Giao Thông", "chitieu", 0.0));
        listLGD.add(new loaiGD(UUID.randomUUID().toString(), "Mua Sắm", "chitieu", 0.0));
        listLGD.add(new loaiGD(UUID.randomUUID().toString(), "Giải trí", "chitieu", 0.0));
        listLGD.add(new loaiGD(UUID.randomUUID().toString(), "Làm Đẹp", "chitieu", 0.0));
        listLGD.add(new loaiGD(UUID.randomUUID().toString(), "Sức Khoẻ", "chitieu", 0.0));
        listLGD.add(new loaiGD(UUID.randomUUID().toString(), "Từ Thiện", "chitieu", 0.0));
        listLGD.add(new loaiGD(UUID.randomUUID().toString(), "Hoá Đơn", "chitieu", 0.0));
        listLGD.add(new loaiGD(UUID.randomUUID().toString(), "Nhà Ở", "chitieu", 0.0));
        listLGD.add(new loaiGD(UUID.randomUUID().toString(), "Gia Đình", "chitieu", 0.0));
        listLGD.add(new loaiGD(UUID.randomUUID().toString(), "Đầu Tư", "chitieu", 0.0));
        listLGD.add(new loaiGD(UUID.randomUUID().toString(), "Học Tập", "chitieu", 0.0));
        listLGD.add(new loaiGD(UUID.randomUUID().toString(), "Tiền Nợ", "thunhap", 0.0));
        listLGD.add(new loaiGD(UUID.randomUUID().toString(), "Kinh Doanh", "thunhap", 0.0));
        listLGD.add(new loaiGD(UUID.randomUUID().toString(), "Trợ Cấp", "thunhap", 0.0));
        listLGD.add(new loaiGD(UUID.randomUUID().toString(), "Lương", "thunhap", 0.0));
        listLGD.add(new loaiGD(UUID.randomUUID().toString(), "Tiền Lãi", "thunhap", 0.0));
        listLGD.add(new loaiGD(UUID.randomUUID().toString(), "Tiết Kiệm", "tietkiem", 0.0));

        if (db.getAll().isEmpty()) {
            for (loaiGD lgd : listLGD) {
                db.add(lgd);
            }
        }
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

}
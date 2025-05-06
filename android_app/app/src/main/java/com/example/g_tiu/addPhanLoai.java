package com.example.g_tiu;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.g_tiu.domain.CategoryService;
import com.example.g_tiu.domain.data.PhanLoai;
import com.example.g_tiu.domain.response.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class addPhanLoai extends AppCompatActivity {

    private EditText editText_tenPhanLoai;
    private EditText editText_nganSach;

    private Button button_phanLoai;
    private Button button_thuNhap;
    private Button button_tietKiem;

    private String type = "EXPENSE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_phan_loai);

        editText_tenPhanLoai = findViewById(R.id.editText_tenPhanLoai);
        editText_nganSach = findViewById(R.id.editText_nganSach);

        button_phanLoai = findViewById(R.id.button_phanLoai);
        button_thuNhap = findViewById(R.id.button_thuNhap);
        button_tietKiem = findViewById(R.id.button_tietKiem);

        button_phanLoai.setOnClickListener(v -> {
            button_phanLoai.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black10)));
            button_thuNhap.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
            button_tietKiem.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));

            type = "EXPENSE";
        });
        button_thuNhap.setOnClickListener(v -> {
            button_thuNhap.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black10)));
            button_phanLoai.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
            button_tietKiem.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));

            type = "INCOME";
        });
        button_tietKiem.setOnClickListener(v -> {
            button_tietKiem.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black10)));
            button_thuNhap.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
            button_phanLoai.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));

            type = "SAVING";
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar_addPhanLoai);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.add_phanloai, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_yes) {

            String name = editText_tenPhanLoai.getText().toString().trim();
            String nganSach = editText_nganSach.getText().toString().trim();

            if (name.isEmpty() || nganSach.isEmpty()) {
                Toast.makeText(this, "Điền thông tin tên và ngân sách", Toast.LENGTH_SHORT).show();
                return true;
            }
            long budget = Long.parseLong(nganSach);

            Gson gson = new GsonBuilder().create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.39:8080/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            CategoryService service = retrofit.create(CategoryService.class);

            Call<BaseResponse<PhanLoai>> repos = service.addCategory(new PhanLoai(name, type, budget));
            repos.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<BaseResponse<PhanLoai>> call, @NonNull Response<BaseResponse<PhanLoai>> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(addPhanLoai.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        setResult(1);
                        finish();
                    } else {
                        Toast.makeText(addPhanLoai.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<BaseResponse<PhanLoai>> call, @NonNull Throwable throwable) {
                    Toast.makeText(addPhanLoai.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return super.onOptionsItemSelected(item);

    }
}
package com.example.g_tiu.ui.phanloai;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.g_tiu.domain.CategoryService;
import com.example.g_tiu.domain.data.PhanLoai;
import com.example.g_tiu.domain.response.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhanLoaiViewModel extends ViewModel {

    private final MutableLiveData<List<PhanLoai>> mCategoriesLiveData;
    public final LiveData<List<PhanLoai>> categoriesLiveData;

    public PhanLoaiViewModel() {
        mCategoriesLiveData = new MutableLiveData<>();
        categoriesLiveData = mCategoriesLiveData;
    }

    public void getCategories() {
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.39:8080/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CategoryService service = retrofit.create(CategoryService.class);

        Call<BaseResponse<List<PhanLoai>>> repos = service.listCategories();
        repos.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<List<PhanLoai>>> call, @NonNull Response<BaseResponse<List<PhanLoai>>> response) {
                mCategoriesLiveData.postValue(response.body() != null ? response.body().getData() : Collections.emptyList());
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<List<PhanLoai>>> call, @NonNull Throwable throwable) {
                mCategoriesLiveData.postValue(Collections.emptyList());
            }
        });
    }
}
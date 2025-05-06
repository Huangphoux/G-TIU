package com.example.g_tiu.domain;

import com.example.g_tiu.domain.data.PhanLoai;
import com.example.g_tiu.domain.response.BaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoryService {

    @GET("api/categories")
    Call<BaseResponse<List<PhanLoai>>> listCategories();

    @POST("api/categories")
    Call<BaseResponse<PhanLoai>> addCategory(@Body PhanLoai phanLoai);

    @PUT("api/categories/{id}")
    Call<BaseResponse<PhanLoai>> updateCategory(@Path("id") int id, @Body PhanLoai phanLoai);

    @DELETE("api/categories/{id}")
    Call<BaseResponse<PhanLoai>> deleteCategory(@Path("id") int id);
}

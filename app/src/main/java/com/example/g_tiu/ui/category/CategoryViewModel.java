package com.example.g_tiu.ui.category;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.g_tiu.db_helper.GTiuDBHelper;
import com.example.g_tiu.item.Category;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryViewModel extends ViewModel {

    private GTiuDBHelper dbHelper;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<Boolean> insertResult = new MutableLiveData<>();
    private final MutableLiveData<List<Category>> categories = new MutableLiveData<>();

    public LiveData<Boolean> getInsertResultLiveData() {
        return insertResult;
    }

    public LiveData<List<Category>> getCategoriesLiveData() {
        return categories;
    }

    public void init(Application application) {
        dbHelper = new GTiuDBHelper(application.getApplicationContext());
    }

    public void insertCategory(Category category) {
        executor.execute(() -> {
            try {
                dbHelper.add(category);
                insertResult.postValue(true);
            } catch (Exception e) {
                insertResult.postValue(false);
            }
        });
    }

    public void getAll() {
        executor.execute(() -> {
            try {
                categories.postValue(dbHelper.getAll());
            } catch (Exception e) {
                categories.postValue(null);
            }
        });
    }

    public void deleteCategory(Category category) {
        executor.execute(() -> {
            try {
                dbHelper.delete(String.valueOf(category.getId()));
            } catch (Exception e) {
                Log.e("GT456_x", "Error");
            }
        });
    }
}

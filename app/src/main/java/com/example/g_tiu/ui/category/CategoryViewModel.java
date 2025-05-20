package com.example.g_tiu.ui.category;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.g_tiu.db_helper.GTiuDBHelper;
import com.example.g_tiu.item.Category;
import com.example.g_tiu.item.Transactions;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryViewModel extends ViewModel {

    public LocalDate currentDate = LocalDate.now();
    private GTiuDBHelper dbHelper;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<Boolean> insertResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> updateResult = new MutableLiveData<>();
    private final MutableLiveData<List<Category>> categories = new MutableLiveData<>();

    public LiveData<Boolean> getInsertResultLiveData() {
        return insertResult;
    }

    public LiveData<Boolean> getUpdateResultLiveData() {
        return updateResult;
    }

    public LiveData<List<Category>> getCategoriesLiveData() {
        return categories;
    }

    public void init(Application application) {
        dbHelper = new GTiuDBHelper(application.getApplicationContext());
    }

    private final MutableLiveData<List<Transactions>> transactions = new MutableLiveData<>();

    public LiveData<List<Transactions>> getTransactionsLiveData() {
        return transactions;
    }

    private final MutableLiveData<Category> categoryLiveData = new MutableLiveData<>();

    public LiveData<Category> getCategoryLiveData() {
        return categoryLiveData;
    }

    public void setCategory(Category category) {
        categoryLiveData.postValue(category);
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

    public void updateCategory(Category category) {
        executor.execute(() -> {
            try {
                dbHelper.update(category);
                updateResult.postValue(true);
            } catch (Exception e) {
                updateResult.postValue(false);
            }
        });
    }

    public void getAll() {
        executor.execute(() -> {
            try {
                categories.postValue(dbHelper.getAll());
                getAllTransactions();
            } catch (Exception e) {
                categories.postValue(null);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    void getAllTransactions() {
        executor.execute(() -> {
            try {
                String monthStr = String.format("%02d", currentDate.getMonthValue());
                String datePrefix = currentDate.getYear() + "-" + monthStr + "-";

                transactions.postValue(dbHelper.getAllTransactions(datePrefix));
            } catch (Exception e) {
                transactions.postValue(null);
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

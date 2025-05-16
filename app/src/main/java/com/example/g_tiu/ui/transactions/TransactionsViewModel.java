package com.example.g_tiu.ui.transactions;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.g_tiu.db_helper.GTiuDBHelper;
import com.example.g_tiu.item.Category;
import com.example.g_tiu.item.Transactions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionsViewModel extends ViewModel {

    private GTiuDBHelper dbHelper;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<List<Transactions>> transactions = new MutableLiveData<>();

    public LiveData<List<Transactions>> getTransactionsLiveData() {
        return transactions;
    }

    private final MutableLiveData<Category> categoryLiveData = new MutableLiveData<>();

    public LiveData<Category> getCategoryLiveData() {
        return categoryLiveData;
    }

    private final MutableLiveData<Boolean> insertLiveData = new MutableLiveData<>();

    public LiveData<Boolean> getInsertTransactions() {
        return insertLiveData;
    }

    public void init(Application application) {
        dbHelper = new GTiuDBHelper(application.getApplicationContext());
    }

    @SuppressLint("DefaultLocale")
    public void getAll(int year, int month) {
        executor.execute(() -> {
            try {
                String monthStr = String.format("%02d", month);
                String datePrefix = year + "-" + monthStr + "-";

                transactions.postValue(dbHelper.getAllTransactions(datePrefix));
            } catch (Exception e) {
                transactions.postValue(null);
            }
        });
    }

    public void setCategory(Category category) {
        categoryLiveData.postValue(category);
    }

    public void addTransaction(LocalDate date, String amount, String note) {
        executor.execute(() -> {
            try {
                insertLiveData.postValue(
                        dbHelper.add(
                                new Transactions(
                                        date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                        Long.parseLong(amount.replaceAll(",", "")),
                                        Objects.requireNonNull(categoryLiveData.getValue()).getId(),
                                        note
                                ))
                );
            } catch (Exception e) {
                insertLiveData.postValue(null);
            }
        });
    }
}

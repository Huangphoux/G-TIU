package com.example.g_tiu.ui.report.statistic;

import android.annotation.SuppressLint;
import android.app.Application;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.g_tiu.db_helper.GTiuDBHelper;
import com.example.g_tiu.item.Keyword;
import com.example.g_tiu.item.Transactions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StatisticViewModel extends ViewModel {

    public LocalDate currentDate = LocalDate.now();
    private GTiuDBHelper dbHelper;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final List<String> tagNames = new ArrayList<>();
    private String keyword = "";

    public void init(Application application) {
        dbHelper = new GTiuDBHelper(application.getApplicationContext());
    }

    private final MutableLiveData<List<Transactions>> transactions = new MutableLiveData<>();

    public LiveData<List<Transactions>> getTransactionsLiveData() {
        return transactions;
    }

    private final MutableLiveData<List<String>> tagLiveData = new MutableLiveData<>();

    public LiveData<List<String>> getTagLiveData() {
        return tagLiveData;
    }

    public void clearKeyword() {
        keyword = "";
    }

    @SuppressLint("DefaultLocale")
    public void getAllTransactions(int indexKeyword) {
        executor.execute(() -> {
            try {
                keyword = tagNames.get(indexKeyword);
                String monthStr = String.format("%02d", currentDate.getMonthValue());
                String datePrefix = currentDate.getYear() + "-" + monthStr + "-";

                transactions.postValue(dbHelper.getAllTransactions(datePrefix, keyword, null));
            } catch (Exception e) {
                transactions.postValue(null);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    public void getAllTransactions() {
        executor.execute(() -> {
            try {
                if (TextUtils.isEmpty(keyword)) return;
                String monthStr = String.format("%02d", currentDate.getMonthValue());
                String datePrefix = currentDate.getYear() + "-" + monthStr + "-";

                transactions.postValue(dbHelper.getAllTransactions(datePrefix, keyword, null));
            } catch (Exception e) {
                transactions.postValue(null);
            }
        });
    }

    public void getTags() {
        executor.execute(() -> {
            try {
                tagNames.clear();
                tagNames.add("Chọn từ khoá");
                List<Keyword> result = dbHelper.getAllKeyWords();
                if (result != null) {
                    for (Keyword tag : result) {
                        tagNames.add(tag.getName());
                    }
                }
                tagLiveData.postValue(tagNames);
            } catch (Exception e) {
                tagLiveData.postValue(null);
            }
        });
    }
}


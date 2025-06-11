package com.example.g_tiu.ui.transactions;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.g_tiu.db_helper.GTiuDBHelper;
import com.example.g_tiu.item.Category;
import com.example.g_tiu.item.Keyword;
import com.example.g_tiu.item.Transactions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class TransactionsViewModel extends ViewModel {

    private GTiuDBHelper dbHelper;
    private String keySearch;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<List<Transactions>> transactions = new MutableLiveData<>();

    public LiveData<List<Transactions>> getTransactionsLiveData() {
        return transactions;
    }

    private final MutableLiveData<Category> categoryLiveData = new MutableLiveData<>();

    public LiveData<Category> getCategoryLiveData() {
        return categoryLiveData;
    }

    private final MutableLiveData<Transactions> transactionsResult = new MutableLiveData<>();

    public LiveData<Transactions> getTransactionsResult() {
        return transactionsResult;
    }

    private final MutableLiveData<Boolean> insertLiveData = new MutableLiveData<>();

    public LiveData<Boolean> getInsertTransactions() {
        return insertLiveData;
    }
    private final MutableLiveData<Boolean> updateLiveData = new MutableLiveData<>();

    public LiveData<Boolean> getUpdateTransactions() {
        return updateLiveData;
    }

    private final MutableLiveData<List<Keyword>> keywordLiveData = new MutableLiveData<>();

    public LiveData<List<Keyword>> getKeywordLiveData() {
        return keywordLiveData;
    }

    public void init(Application application) {
        dbHelper = new GTiuDBHelper(application.getApplicationContext());
        getAllKeys();
    }

    public void setArguments(Bundle bundle) {
        if (bundle != null && bundle.containsKey("transactions")) {
            transactionsResult.postValue((Transactions) bundle.getSerializable("transactions"));
        }
    }

    private int year, month;

    @SuppressLint("DefaultLocale")
    public void getAll(int year, int month) {
        this.year = year;
        this.month = month;
        executor.execute(() -> {
            try {
                String monthStr = String.format("%02d", month);
                String datePrefix = year + "-" + monthStr + "-";
                String keyword = "";
                if (keyFilterLiveData.getValue() != null) {
                    keyword = keyFilterLiveData.getValue().getName();
                }
                String mKeySearch = "";
                if (!TextUtils.isEmpty(keySearch)) {
                    mKeySearch = keySearch;
                }
                transactions.postValue(dbHelper.getAllTransactions(datePrefix, keyword, mKeySearch));
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
                Transactions tr = new Transactions(
                        date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        Long.parseLong(amount.replaceAll(",", "")),
                        Objects.requireNonNull(categoryLiveData.getValue()).getId(),
                        note
                );
                String result = keywords.stream()
                        .map(Keyword::getName)
                        .collect(Collectors.joining(", "));

                tr.setKeys(result);
                Log.d("GT345_x:", "addTransaction is call");
                insertLiveData.postValue(dbHelper.add(tr));
            } catch (Exception e) {
                insertLiveData.postValue(null);
            }
        });
    }

    public void updateTransaction(LocalDate date, String amount, String note) {
        executor.execute(() -> {
            try {
                Transactions tr = new Transactions(
                        date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        Long.parseLong(amount.replaceAll(",", "")),
                        Objects.requireNonNull(categoryLiveData.getValue()).getId(),
                        note
                );
                String result = keywords.stream()
                        .map(Keyword::getName)
                        .collect(Collectors.joining(", "));

                tr.setKeys(result);
                if (transactionsResult.getValue() != null) {
                    tr.setId(transactionsResult.getValue().getId());
                }
                updateLiveData.postValue(dbHelper.update(tr));
            } catch (Exception e) {
                updateLiveData.postValue(null);
            }
        });
    }

    public void deleteTransactions() {
        executor.execute(() -> {
            try {
                if (transactionsResult.getValue() != null) {
                    dbHelper.deleteTransaction(transactionsResult.getValue().getId());
                }
            } catch (Exception ignored) {

            }
        });
    }

    private final ArrayList<Keyword> keywords = new ArrayList<>();

    public void addKeyword(Keyword keyword, boolean isSkipObs) {
        if (!keywords.contains(keyword)) {
            keywords.add(keyword);
        }
        if (!isSkipObs) {
            keywordLiveData.postValue(keywords);
        }
    }

    public void removeKeyword(Keyword keyword) {
        keywords.remove(keyword);
    }

    public final List<Keyword> keywordList = new ArrayList<>();

    public void getAllKeys() {
        executor.execute(() -> {
            try {
                keywordList.addAll(dbHelper.getAllKeyWords());
            } catch (Exception ignored) {

            }
        });
    }

    private final MutableLiveData<Keyword> keyFilterLiveData = new MutableLiveData<>();

    public LiveData<Keyword> getKeyFilterLiveData() {
        return keyFilterLiveData;
    }

    public void applyFilterKeyword(Keyword keyword) {
        keyFilterLiveData.postValue(keyword);

        executor.shutdown();
        executor = Executors.newSingleThreadExecutor();
        getAll(year, month);
    }

    public void applyFilterKeySearch(String keySearch) {
        this.keySearch = keySearch;
        executor.shutdown();
        executor = Executors.newSingleThreadExecutor();
        getAll(year, month);
    }
}

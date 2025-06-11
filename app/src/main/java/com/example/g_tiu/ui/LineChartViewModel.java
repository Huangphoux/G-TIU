package com.example.g_tiu.ui;

import android.annotation.SuppressLint;
import android.app.Application;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.g_tiu.db_helper.GTiuDBHelper;
import com.example.g_tiu.item.Transactions;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LineChartViewModel extends ViewModel {

    private GTiuDBHelper dbHelper;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public void init(Application application) {
        dbHelper = new GTiuDBHelper(application.getApplicationContext());
    }

    private final MutableLiveData<List<Transactions>> transactions = new MutableLiveData<>();

    public LiveData<List<Transactions>> getTransactionsLiveData() {
        return transactions;
    }

    @SuppressLint("DefaultLocale")
    public void getAll(int count) {

        executor.execute(() -> {
            try {
                List<Transactions> result = new ArrayList<>();
                YearMonth currentMonth = YearMonth.now();

                for (int i = 0; i < 3; i++) {
                    YearMonth month = currentMonth.minusMonths(i);
                    String monthStr = String.format("%02d", month.getMonthValue());
                    String datePrefix = month.getYear() + "-" + monthStr + "-";

                    result.addAll(dbHelper.getAllTransactions(datePrefix));
                }

                transactions.postValue(result);
            } catch (Exception e) {
                transactions.postValue(null);
            }
        });
    }
}

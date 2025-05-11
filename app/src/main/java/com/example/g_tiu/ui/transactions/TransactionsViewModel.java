package com.example.g_tiu.ui.transactions;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.g_tiu.db_helper.TransactionsDBHelper;
import com.example.g_tiu.item.Transactions;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionsViewModel extends ViewModel {

    private TransactionsDBHelper dbHelper;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<List<Transactions>> transactions = new MutableLiveData<>();

    public LiveData<List<Transactions>> getTransactionsLiveData() {
        return transactions;
    }

    public void init(Application application) {
        dbHelper = new TransactionsDBHelper(application.getApplicationContext());
    }

    public void getAll() {
        executor.execute(() -> {
            try {
                transactions.postValue(dbHelper.getAll());
            } catch (Exception e) {
                transactions.postValue(null);
            }
        });
    }
}

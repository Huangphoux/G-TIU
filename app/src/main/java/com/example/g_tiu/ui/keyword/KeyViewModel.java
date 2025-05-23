package com.example.g_tiu.ui.keyword;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.g_tiu.db_helper.GTiuDBHelper;
import com.example.g_tiu.item.Keyword;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KeyViewModel extends ViewModel {

    private GTiuDBHelper dbHelper;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<List<Keyword>> keywordsLiveData = new MutableLiveData<>();

    public LiveData<List<Keyword>> getKeywordLiveData() {
        return keywordsLiveData;
    }

    public void init(Application application) {
        dbHelper = new GTiuDBHelper(application.getApplicationContext());
    }

    public void getAll() {
        executor.execute(() -> {
            try {
                keywordsLiveData.postValue(dbHelper.getAllKeyWords());
            } catch (Exception e) {
                keywordsLiveData.postValue(null);
            }
        });
    }
}

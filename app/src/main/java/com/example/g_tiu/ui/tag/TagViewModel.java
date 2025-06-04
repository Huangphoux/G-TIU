package com.example.g_tiu.ui.tag;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.g_tiu.db_helper.GTiuDBHelper;
import com.example.g_tiu.item.Keyword;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TagViewModel extends ViewModel {

    private GTiuDBHelper dbHelper;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<List<Keyword>> keywords = new MutableLiveData<>();
    private final MutableLiveData<Keyword> insertKeyword = new MutableLiveData<>();

    public LiveData<List<Keyword>> getKeywordLiveData() {
        return keywords;
    }

    public LiveData<Keyword> getInsertKeywordLiveData() {
        return insertKeyword;
    }

    public void init(Application application) {
        dbHelper = new GTiuDBHelper(application.getApplicationContext());
        dbHelper.addKeyword("BASE");
    }

    public void getAll() {
        executor.execute(() -> {
            try {
                keywords.postValue(dbHelper.getAllKeyWords());
            } catch (Exception e) {
                keywords.postValue(null);
            }
        });
    }

    public void addKeyword(String keyword) {
        executor.execute(() -> {
            try {
                dbHelper.addKeyword(keyword);
                List<Keyword> list = dbHelper.getAllKeyWords();
                for (Keyword key : list) {
                    if (key.getName().equals(keyword)) {
                        insertKeyword.postValue(key);
                        return;
                    }
                }
                insertKeyword.postValue(null);
            } catch (Exception e) {
                insertKeyword.postValue(null);
            }
        });
    }
}

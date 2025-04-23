package com.example.g_tiu.ui.baocao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaoCaoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BaoCaoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
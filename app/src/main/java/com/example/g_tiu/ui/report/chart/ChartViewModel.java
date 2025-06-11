package com.example.g_tiu.ui.report.chart;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.g_tiu.db_helper.GTiuDBHelper;
import com.example.g_tiu.helper.AppConstants;
import com.example.g_tiu.item.Category;
import com.example.g_tiu.item.Color;
import com.example.g_tiu.item.IconModel;
import com.example.g_tiu.item.Keyword;
import com.example.g_tiu.item.Transactions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChartViewModel extends ViewModel {

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

    private final MutableLiveData<Boolean> deleteResult = new MutableLiveData<>();

    public LiveData<Boolean> getDeleteResult() {
        return deleteResult;
    }

    public void clearDeleteResult() {
        deleteResult.postValue(null);
    }

    private final MutableLiveData<Category> categoryLiveData = new MutableLiveData<>();

    public LiveData<Category> getCategoryLiveData() {
        return categoryLiveData;
    }

    private final MutableLiveData<List<Keyword>> keywordLiveData = new MutableLiveData<>();

    public LiveData<List<Keyword>> getKeywordLiveData() {
        return keywordLiveData;
    }

    private final MutableLiveData<Color> colorLiveData = new MutableLiveData<>(AppConstants.getColors().get(0));

    public LiveData<Color> getColorLiveData() {
        return colorLiveData;
    }

    private final MutableLiveData<IconModel> iconLiveData = new MutableLiveData<>(AppConstants.getIcons().get(0));

    public LiveData<IconModel> getIconLiveData() {
        return iconLiveData;
    }

    public void setCategory(Category category) {
        if (categoryLiveData.getValue() != null) return;
        categoryLiveData.postValue(category);
        for (IconModel iconModel : AppConstants.getIcons()) {
            if (iconModel.getId() == category.getIcon()) {
                iconLiveData.postValue(iconModel);
                break;
            }
        }
        for (Color color : AppConstants.getColors()) {
            if (color.getHex().equals(category.getHex())) {
                colorLiveData.postValue(color);
                break;
            }
        }
    }

    private final ArrayList<Keyword> keywords = new ArrayList<>();

    public void addKeyword(Keyword keyword) {
        if (!keywords.contains(keyword)) {
            keywords.add(keyword);
        }
        keywordLiveData.postValue(keywords);
    }

    public void removeKeyword(Keyword keyword) {
        keywords.remove(keyword);
        // keywordLiveData.postValue(keywords);
    }

    public void setColor(Color color) {
        colorLiveData.postValue(color);
    }

    public void setIcon(IconModel icon) {
        iconLiveData.postValue(icon);
    }

    public void insertCategory(Category category) {
        executor.execute(() -> {
            try {
                if (iconLiveData.getValue() != null) {
                    category.setIcon(iconLiveData.getValue().getId());
                }
                if (colorLiveData.getValue() != null) {
                    category.setHex(colorLiveData.getValue().getHex());
                }
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
                if (iconLiveData.getValue() != null) {
                    category.setIcon(iconLiveData.getValue().getId());
                }
                if (colorLiveData.getValue() != null) {
                    category.setHex(colorLiveData.getValue().getHex());
                }
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
                deleteResult.postValue(true);
            } catch (Exception e) {
                deleteResult.postValue(false);
                Log.e("GT456_x", "Error " + e);
            }
        });
    }
}

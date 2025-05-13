package com.example.g_tiu.db_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.g_tiu.item.Category;

import java.util.ArrayList;

public class CategoryDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "g-tiu.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_CATEGORY = "tb_category";
    public static final String COL_ID = "col_id";
    public static final String COL_NAME = "col_name";
    public static final String COL_TYPE = "col_type";
    public static final String COL_BUDGET = "col_budget";

    public CategoryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_CATEGORY + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " TEXT," +
                COL_TYPE + " TEXT," +
                COL_BUDGET + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        onCreate(db);
    }

    public boolean add(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, category.getName());
        values.put(COL_TYPE, category.getType());
        values.put(COL_BUDGET, category.getBudget());

        long result = db.insert(TABLE_CATEGORY, null, values);
        return result != -1;
    }

    public boolean update(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, category.getName());
        values.put(COL_TYPE, category.getType());
        values.put(COL_BUDGET, category.getBudget());

        int result = db.update(TABLE_CATEGORY, values, COL_ID + "=?", new String[]{String.valueOf(category.getId())});
        return result > 0;
    }

    public boolean delete(String categoryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_CATEGORY, COL_ID + "=?", new String[]{categoryId});
        return result > 0;
    }

    public ArrayList<Category> getAll() {
        ArrayList<Category> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(COL_TYPE));
                long budget = cursor.getLong(cursor.getColumnIndexOrThrow(COL_BUDGET));

                list.add(new Category(id, name, type, budget));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    public Category getOneById(int categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY + " WHERE " + COL_ID + "=?", new String[]{String.valueOf(categoryId)});

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
            String type = cursor.getString(cursor.getColumnIndexOrThrow(COL_TYPE));
            long budget = cursor.getLong(cursor.getColumnIndexOrThrow(COL_BUDGET));

            return new Category(id, name, type, budget);
        }

        cursor.close();
        return null;
    }
}

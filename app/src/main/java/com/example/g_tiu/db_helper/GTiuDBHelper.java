package com.example.g_tiu.db_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.g_tiu.item.Category;
import com.example.g_tiu.item.Transactions;

import java.util.ArrayList;

public class GTiuDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "g-tiu.db";
    public static final int DATABASE_VERSION = 2;

    public static final String TABLE_CATEGORY = "tb_category";
    public static final String COL_ID = "col_id";
    public static final String COL_NAME = "col_name";
    public static final String COL_TYPE = "col_type";
    public static final String COL_BUDGET = "col_budget";

    public static final String TABLE_TRANSACTIONS = "tb_transactions";
    public static final String COL_TRANSACTION_ID = "col_transaction_id";
    public static final String COL_TRANSACTION_DATE = "col_transaction_date";
    public static final String COL_TRANSACTION_AMOUNT = "col_transaction_amount";
    public static final String COL_TRANSACTION_CATEGORY_ID = "col_transaction_category_id";
    public static final String COL_TRANSACTION_NOTE = "col_transaction_note";
    public static final String COL_TRANSACTION_CREATE_AT = "col_transaction_create_at";

    public GTiuDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCategoryTable = "CREATE TABLE " + TABLE_CATEGORY + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " TEXT," +
                COL_TYPE + " TEXT," +
                COL_BUDGET + " REAL)";
        db.execSQL(createCategoryTable);

        String createTransactionsTable = "CREATE TABLE " + TABLE_TRANSACTIONS + "(" +
                COL_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_TRANSACTION_DATE + " TEXT," +
                COL_TRANSACTION_AMOUNT + " REAL," +
                COL_TRANSACTION_CATEGORY_ID + " INTEGER," +
                COL_TRANSACTION_NOTE + " TEXT," +
                COL_TRANSACTION_CREATE_AT + " REAL," +
                "FOREIGN KEY (" + COL_TRANSACTION_CATEGORY_ID + ") REFERENCES " +
                TABLE_CATEGORY + "(" + COL_ID + "))";
        db.execSQL(createTransactionsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
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

    public boolean add(Transactions transactions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_TRANSACTION_DATE, transactions.getDate());
        values.put(COL_TRANSACTION_AMOUNT, transactions.getAmount());
        values.put(COL_TRANSACTION_CATEGORY_ID, transactions.getCategoryId());
        values.put(COL_TRANSACTION_NOTE, transactions.getNote());
        values.put(COL_TRANSACTION_CREATE_AT, transactions.getCreateTime());

        long result = db.insert(TABLE_TRANSACTIONS, null, values);
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

    public boolean update(Transactions transactions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_TRANSACTION_DATE, transactions.getDate());
        values.put(COL_TRANSACTION_AMOUNT, transactions.getAmount());
        values.put(COL_TRANSACTION_CATEGORY_ID, transactions.getCategoryId());
        values.put(COL_TRANSACTION_NOTE, transactions.getNote());

        int result = db.update(TABLE_TRANSACTIONS, values, COL_TRANSACTION_ID + "=?", new String[]{String.valueOf(transactions.getId())});
        return result > 0;
    }

    public void delete(String categoryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY, COL_ID + "=?", new String[]{categoryId});
    }

    public void delete(int transactionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRANSACTIONS, COL_TRANSACTION_ID + "=?", new String[]{String.valueOf(transactionId)});
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

    public ArrayList<Transactions> getAllTransactions(String datePrefix) {
        ArrayList<Transactions> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tb_transactions WHERE col_transaction_date LIKE ?",
                new String[]{datePrefix + "%"});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_TRANSACTION_ID));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_TRANSACTION_DATE));
                long amount = cursor.getLong(cursor.getColumnIndexOrThrow(COL_TRANSACTION_AMOUNT));
                int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_TRANSACTION_CATEGORY_ID));
                String note = cursor.getString(cursor.getColumnIndexOrThrow(COL_TRANSACTION_NOTE));
                long createAt = cursor.getLong(cursor.getColumnIndexOrThrow(COL_TRANSACTION_CREATE_AT));

                Category category = getOneById(categoryId);
                list.add(new Transactions(id, date, amount, categoryId, note, createAt, category));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

//    public ArrayList<Transactions> getAllTransactionsWhereCategory(String datePrefix, int categoryId) {
//        ArrayList<Transactions> list = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT * FROM tb_transactions WHERE col_transaction_date LIKE ? AND col_transaction_category_id = ?",
//                new String[]{datePrefix + "%", String.valueOf(categoryId)});
//
//        if (cursor.moveToFirst()) {
//            do {
//                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_TRANSACTION_ID));
//                String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_TRANSACTION_DATE));
//                long amount = cursor.getLong(cursor.getColumnIndexOrThrow(COL_TRANSACTION_AMOUNT));
//                int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_TRANSACTION_CATEGORY_ID));
//                String note = cursor.getString(cursor.getColumnIndexOrThrow(COL_TRANSACTION_NOTE));
//
//                Category category = getOneById(categoryId);
//                list.add(new Transactions(id, date, amount, categoryId, note, category));
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        return list;
//    }

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

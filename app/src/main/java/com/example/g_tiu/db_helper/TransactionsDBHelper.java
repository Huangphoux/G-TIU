package com.example.g_tiu.db_helper;

import static com.example.g_tiu.db_helper.CategoryDBHelper.COL_ID;
import static com.example.g_tiu.db_helper.CategoryDBHelper.TABLE_CATEGORY;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.g_tiu.item.Category;
import com.example.g_tiu.item.Transactions;

import java.util.ArrayList;

public class TransactionsDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "g-tiu.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_TRANSACTIONS = "tb_transactions";
    public static final String COL_TRANSACTION_ID = "col_transaction_id";
    public static final String COL_TRANSACTION_DATE = "col_transaction_date";
    public static final String COL_TRANSACTION_AMOUNT = "col_transaction_amount";
    public static final String COL_TRANSACTION_CATEGORY_ID = "col_transaction_category_id";
    public static final String COL_TRANSACTION_NOTE = "col_transaction_note";

    private CategoryDBHelper categoryDBHelper;

    public TransactionsDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");

        categoryDBHelper = new CategoryDBHelper(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_TRANSACTIONS + "(" +
                COL_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_TRANSACTION_DATE + " TEXT," +
                COL_TRANSACTION_AMOUNT + " REAL," +
                COL_TRANSACTION_CATEGORY_ID + " INTEGER," +
                COL_TRANSACTION_NOTE + " TEXT," +
                "FOREIGN KEY (" + COL_TRANSACTION_CATEGORY_ID + ") REFERENCES " +
                TABLE_CATEGORY + "(" + COL_ID + "))";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(db);
    }

    public boolean add(Transactions transactions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_TRANSACTION_DATE, transactions.getDate());
        values.put(COL_TRANSACTION_AMOUNT, transactions.getAmount());
        values.put(COL_TRANSACTION_CATEGORY_ID, transactions.getCategoryId());
        values.put(COL_TRANSACTION_NOTE, transactions.getNote());

        long result = db.insert(TABLE_TRANSACTIONS, null, values);
        return result != -1;
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

    public boolean delete(int transactionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_TRANSACTIONS, COL_TRANSACTION_ID + "=?", new String[]{String.valueOf(transactionId)});
        return result > 0;
    }

    public ArrayList<Transactions> getAll() {
        ArrayList<Transactions> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TRANSACTIONS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_TRANSACTION_ID));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_TRANSACTION_DATE));
                double amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_TRANSACTION_AMOUNT));
                int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_TRANSACTION_CATEGORY_ID));
                String note = cursor.getString(cursor.getColumnIndexOrThrow(COL_TRANSACTION_NOTE));

                Category category = null;
                if (categoryDBHelper != null) {
                    category = categoryDBHelper.getOneById(categoryId);
                }
                list.add(new Transactions(id, date, amount, categoryId, note, category));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }
}

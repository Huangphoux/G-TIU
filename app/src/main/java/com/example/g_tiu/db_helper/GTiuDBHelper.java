package com.example.g_tiu.db_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.example.g_tiu.item.Category;
import com.example.g_tiu.item.Keyword;
import com.example.g_tiu.item.Transactions;

import java.util.ArrayList;

public class GTiuDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "g-tiu.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_CATEGORY = "tb_category";
    public static final String COL_ID = "col_id";
    public static final String COL_NAME = "col_name";
    public static final String COL_TYPE = "col_type";
    public static final String COL_COLOR = "col_color";
    public static final String COL_ICON = "col_icon";
    public static final String COL_BUDGET = "col_budget";

    public static final String TABLE_TRANSACTIONS = "tb_transactions";
    public static final String COL_TRANSACTION_ID = "col_transaction_id";
    public static final String COL_TRANSACTION_DATE = "col_transaction_date";
    public static final String COL_TRANSACTION_AMOUNT = "col_transaction_amount";
    public static final String COL_TRANSACTION_CATEGORY_ID = "col_transaction_category_id";
    public static final String COL_TRANSACTION_KEYWORD = "col_transaction_keyword";
    public static final String COL_TRANSACTION_NOTE = "col_transaction_note";
    public static final String COL_TRANSACTION_CREATE_AT = "col_transaction_create_at";

    public static final String TABLE_KEYWORD = "tb_keyword";
    public static final String COL_KEYWORD_ID = "col_keyword_id";
    public static final String COL_KEYWORD_NAME = "col_keyword_name";

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
                COL_COLOR + " TEXT," +
                COL_ICON + " INTEGER," +
                COL_BUDGET + " REAL)";
        db.execSQL(createCategoryTable);

        String createTransactionsTable = "CREATE TABLE " + TABLE_TRANSACTIONS + "(" +
                COL_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_TRANSACTION_DATE + " TEXT," +
                COL_TRANSACTION_AMOUNT + " REAL," +
                COL_TRANSACTION_CATEGORY_ID + " INTEGER," +
                COL_TRANSACTION_KEYWORD + " TEXT," +
                COL_TRANSACTION_NOTE + " TEXT," +
                COL_TRANSACTION_CREATE_AT + " REAL," +
                "FOREIGN KEY (" + COL_TRANSACTION_CATEGORY_ID + ") REFERENCES " +
                TABLE_CATEGORY + "(" + COL_ID + "))";
        db.execSQL(createTransactionsTable);

        String createKeywordTable = "CREATE TABLE " + TABLE_KEYWORD + "(" +
                COL_KEYWORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_KEYWORD_NAME + " TEXT)";
        db.execSQL(createKeywordTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KEYWORD);
        onCreate(db);
    }

    public boolean add(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, category.getName());
        values.put(COL_TYPE, category.getType());
        values.put(COL_COLOR, category.getHex());
        values.put(COL_ICON, category.getIcon());
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
        values.put(COL_TRANSACTION_KEYWORD, transactions.getKeys());
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
        values.put(COL_COLOR, category.getHex());
        values.put(COL_ICON, category.getIcon());

        int result = db.update(TABLE_CATEGORY, values, COL_ID + "=?", new String[]{String.valueOf(category.getId())});
        return result > 0;
    }

    public boolean update(Transactions transactions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_TRANSACTION_DATE, transactions.getDate());
        values.put(COL_TRANSACTION_AMOUNT, transactions.getAmount());
        values.put(COL_TRANSACTION_CATEGORY_ID, transactions.getCategoryId());
        values.put(COL_TRANSACTION_KEYWORD, transactions.getKeys());
        values.put(COL_TRANSACTION_NOTE, transactions.getNote());

        int result = db.update(TABLE_TRANSACTIONS, values, COL_TRANSACTION_ID + "=?", new String[]{String.valueOf(transactions.getId())});
        return result > 0;
    }

    public void delete(String categoryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRANSACTIONS, COL_TRANSACTION_CATEGORY_ID + "=?", new String[]{categoryId});
        db.delete(TABLE_CATEGORY, COL_ID + "=?", new String[]{categoryId});
    }

    public void deleteTransaction(long transactionId) {
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
                String hex = cursor.getString(cursor.getColumnIndexOrThrow(COL_COLOR));
                int icon = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ICON));
                long budget = cursor.getLong(cursor.getColumnIndexOrThrow(COL_BUDGET));

                list.add(new Category(id, name, false, type, budget, hex, icon));
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
                String keys = cursor.getString(cursor.getColumnIndexOrThrow(COL_TRANSACTION_KEYWORD));
                long createAt = cursor.getLong(cursor.getColumnIndexOrThrow(COL_TRANSACTION_CREATE_AT));

                Category category = getOneById(categoryId);
                list.add(new Transactions(id, date, amount, categoryId, note, keys, createAt, category));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    public ArrayList<Transactions> getAllTransactions(String datePrefix, String keyword, String keySearch) {
        String mSearch = "";
        if (!TextUtils.isEmpty(keySearch)) {
            mSearch = keySearch;
        }
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
                if (TextUtils.isEmpty(note)) {
                    note = "";
                }
                String keys = cursor.getString(cursor.getColumnIndexOrThrow(COL_TRANSACTION_KEYWORD));
                if (TextUtils.isEmpty(keys)) {
                    keys = "";
                }
                long createAt = cursor.getLong(cursor.getColumnIndexOrThrow(COL_TRANSACTION_CREATE_AT));
                Category category = getOneById(categoryId);
                String[] keySplit = keys.split(",");

                boolean isMatchKeySearch = note.toLowerCase().contains(mSearch.toLowerCase())
                        || category.getName().toLowerCase().contains(mSearch.toLowerCase());

                if (keySplit.length == 0) {
                    if (isMatchKeySearch) {
                        list.add(new Transactions(id, date, amount, categoryId, note, keys, createAt, category));
                    }
                } else {
                    boolean isAddTrans = false;
                    for (String key : keySplit) {
                        if (!isMatchKeySearch) continue;

                        if (TextUtils.isEmpty(keyword) || key.equals(keyword)) {
                            isAddTrans = true;
                            break;
                        }
                    }
                    if (isAddTrans) {
                        list.add(new Transactions(id, date, amount, categoryId, note, keys, createAt, category));
                    }
                }
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
            int icon = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ICON));
            long budget = cursor.getLong(cursor.getColumnIndexOrThrow(COL_BUDGET));

            Category category = new Category(id, name, type, budget);
            category.setIcon(icon);
            return category;
        }

        cursor.close();
        return null;
    }

    // region -> keyword

    public ArrayList<Keyword> getAllKeyWords() {
        ArrayList<Keyword> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_KEYWORD, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_KEYWORD_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_KEYWORD_NAME));
                list.add(new Keyword(id, name));
            }
            while (cursor.moveToNext());

            cursor.close();
            return list;
        }
        return null;
    }

    public void addKeyword(String name) {
        if (checkKeywordName(name)) return;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_KEYWORD_NAME, name);
        db.insert(TABLE_KEYWORD, null, values);
    }

    private boolean checkKeywordName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_KEYWORD + " WHERE " + COL_KEYWORD_NAME + "=?", new String[]{name});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    // endregion
}

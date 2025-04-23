package com.example.g_tiu.db_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.g_tiu.item.*;

import java.util.ArrayList;


public class loaiGD_DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "g-tiu.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "loaiGD";
    public static final String COL_ID = "MaLGD";
    public static final String COL_NAME = "TenLGD";
    public static final String COL_TYPE = "Loai";
    public static final String COL_BUDGET = "NganSach";

    public loaiGD_DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " TEXT PRIMARY KEY," +
                COL_NAME + " TEXT," +
                COL_TYPE + " TEXT," +
                COL_BUDGET + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // ADD
    public boolean add(loaiGD lgd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_ID, lgd.getMaLGD());
        values.put(COL_NAME, lgd.getTenLGD());
        values.put(COL_TYPE, lgd.getLoai());
        values.put(COL_BUDGET, lgd.getNganSach());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    // UPDATE
    public boolean update(loaiGD lgd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, lgd.getTenLGD());
        values.put(COL_TYPE, lgd.getLoai());
        values.put(COL_BUDGET, lgd.getNganSach());

        int result = db.update(TABLE_NAME, values, COL_ID + "=?", new String[]{lgd.getMaLGD()});
        db.close();
        return result > 0;
    }

    // DELETE
    public boolean delete(String maLGD) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COL_ID + "=?", new String[]{maLGD});
        db.close();
        return result > 0;
    }

    // GET ALL
    public ArrayList<loaiGD> getAll() {
        ArrayList<loaiGD> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                String ma = cursor.getString(0);
                String ten = cursor.getString(1);
                String loai = cursor.getString(2);
                double nganSach = cursor.getDouble(3);
                list.add(new loaiGD(ma, ten, loai, nganSach));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }
}

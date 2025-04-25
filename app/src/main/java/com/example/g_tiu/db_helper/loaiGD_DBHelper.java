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
    public static final String TABLE_LOAIGD = "loaiGD";
    public static final String COL_MALGD = "MaLGD";
    public static final String COL_TENLGD = "TenLGD";
    public static final String COL_KIEUGD = "KieuGD";
    public static final String COL_NGANSACH = "NganSach";

    public loaiGD_DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_LOAIGD + "(" +
                COL_MALGD + " TEXT PRIMARY KEY," +
                COL_TENLGD + " TEXT," +
                COL_KIEUGD + " TEXT," +
                COL_NGANSACH + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOAIGD);
        onCreate(db);
    }

    // ADD
    public boolean add(loaiGD lgd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_MALGD, lgd.getMaLGD());
        values.put(COL_TENLGD, lgd.getTenLGD());
        values.put(COL_KIEUGD, lgd.getKieuGD());
        values.put(COL_NGANSACH, lgd.getNganSach());

        long result = db.insert(TABLE_LOAIGD, null, values);
//        db.close();
        return result != -1;
    }

    // UPDATE
    public boolean update(loaiGD lgd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_TENLGD, lgd.getTenLGD());
        values.put(COL_KIEUGD, lgd.getKieuGD());
        values.put(COL_NGANSACH, lgd.getNganSach());

        int result = db.update(TABLE_LOAIGD, values, COL_MALGD + "=?", new String[]{lgd.getMaLGD()});
//        db.close();
        return result > 0;
    }

    // DELETE
    public boolean delete(String maLGD) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_LOAIGD, COL_MALGD + "=?", new String[]{maLGD});
//        db.close();
        return result > 0;
    }

    // GET ALL
    public ArrayList<loaiGD> getAll() {
        ArrayList<loaiGD> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LOAIGD, null);

        if (cursor.moveToFirst()) {
            do {
                String ma = cursor.getString(0);
                String ten = cursor.getString(1);
                String kieu = cursor.getString(2);
                double nganSach = cursor.getDouble(3);
                list.add(new loaiGD(ma, ten, kieu, nganSach));
            } while (cursor.moveToNext());
        }

        cursor.close();
//        db.close();
        return list;
    }
}

package com.example.g_tiu.db_helper;

import static com.example.g_tiu.db_helper.loaiGD_DBHelper.COL_MALGD;
import static com.example.g_tiu.db_helper.loaiGD_DBHelper.TABLE_LOAIGD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.g_tiu.item.*;

import java.util.ArrayList;

public class giaoDich_DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "g-tiu.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_GIAODICH = "giaoDich";

    public static final String COL_MAGD = "MaGD";
    public static final String COL_TENGD = "TenGD";
    public static final String COL_NGAYGD = "NgayGD";
    public static final String COL_SOTIEN = "SoTien";
    public static final String COL_GHICHU = "GhiChu";
    public static final String COL_TYPE = "LoaiGD";

    public giaoDich_DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_GIAODICH + "(" +
                COL_MAGD + " TEXT PRIMARY KEY," +
                COL_TENGD + " TEXT," +
                COL_NGAYGD + " TEXT," +
                COL_SOTIEN + " REAL," +
                COL_GHICHU + " TEXT," +
                COL_TYPE + " TEXT," +
                "FOREIGN KEY (" + COL_TYPE + ") REFERENCES " +
                TABLE_LOAIGD + "(" + COL_MALGD + "))";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GIAODICH);
        onCreate(db);
    }


    // ADD
    public boolean add(giaoDich gd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put(COL_MAGD, gd.getMaGD());
        values.put(COL_TENGD, gd.getTenGD());
        values.put(COL_NGAYGD, gd.getNgayGD());
        values.put(COL_SOTIEN, gd.getSoTien());
        values.put(COL_GHICHU, gd.getGhiChu());
        values.put(COL_TYPE, gd.getLoaiGD());

        long result = db.insert(TABLE_GIAODICH, null, values);
        db.close();
        return result != -1;
    }

    // UPDATE
    public boolean update(giaoDich gd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TENGD, gd.getTenGD());
        values.put(COL_NGAYGD, gd.getNgayGD());
        values.put(COL_SOTIEN, gd.getSoTien());
        values.put(COL_GHICHU, gd.getGhiChu());
        values.put(COL_TYPE, gd.getLoaiGD());

        int result = db.update(TABLE_GIAODICH, values, COL_MAGD + "=?", new String[]{gd.getMaGD()});
        db.close();
        return result > 0;
    }

    // DELETE
    public boolean delete(String maGD) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_GIAODICH, COL_MAGD + "=?", new String[]{maGD});
        db.close();
        return result > 0;
    }

    // GET ALL
    public ArrayList<giaoDich> getAll() {
        ArrayList<giaoDich> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GIAODICH, null);

        if (cursor.moveToFirst()) {
            do {
                String ma = cursor.getString(0);
                String ten = cursor.getString(1);
                String ngay = cursor.getString(2);
                double tien = cursor.getDouble(3);
                String ghiChu = cursor.getString(4);
                String loai = cursor.getString(5);

                list.add(new giaoDich(ma, ten, ngay, tien, ghiChu, loai));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }
}

package com.example.nanapiyasa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "fees.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE = "students";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT, paid INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void addStudent(String name, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("phone", phone);
        cv.put("paid", 0);
        db.insert(TABLE, null, cv);
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE, null);
        while (c.moveToNext()) {
            list.add(new Student(
                c.getInt(0),
                c.getString(1),
                c.getString(2),
                c.getInt(3) == 1
            ));
        }
        c.close();
        return list;
    }

    public ArrayList<Student> getUnpaidStudents() {
        ArrayList<Student> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE + " WHERE paid = 0", null);
        while (c.moveToNext()) {
            list.add(new Student(
                c.getInt(0),
                c.getString(1),
                c.getString(2),
                false
            ));
        }
        c.close();
        return list;
    }

    public void updatePaymentStatus(int id, boolean paid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("paid", paid ? 1 : 0);
        db.update(TABLE, cv, "id=?", new String[]{String.valueOf(id)});
    }
}

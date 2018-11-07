package com.romodaniel.fitness.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.romodaniel.fitness.data.Contract.TABLE_EATS.*;

public class CalDbUtils {

    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }

    public static long InsertToDb(SQLiteDatabase db, Cal_Record record){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_DATE, record.getDate());
        cv.put(COLUMN_NAME_DESC, record.getDesc());
        cv.put(COLUMN_NAME_VALUE, record.getValue());
        return db.insert(TABLE_NAME, null,cv);
    }
}
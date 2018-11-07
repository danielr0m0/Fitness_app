package com.romodaniel.fitness.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.romodaniel.fitness.data.Contract.TABLE_USER.*;

/**
 * Created by drdan on 7/30/2017.
 */

public class UserDbUtils {

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

    public static long InsertToDb(SQLiteDatabase db, User user){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_FIRST_NAME, user.getfName());
        cv.put(COLUMN_NAME_LAST_NAME, user.getlName());
        cv.put(COLUMN_NAME_GENDER, user.getSex());
        cv.put(COLUMN_NAME_HEIGHT, user.getHeight());
        cv.put(COLUMN_NAME_WEIGHT, user.getLbs());
        return db.insert(TABLE_NAME, null,cv);

    }
}

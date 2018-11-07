package com.romodaniel.fitness.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import static com.romodaniel.fitness.data.Contract.TABLE_RUNS.*;

/**
 * Created by drdan on 7/26/2017.
 */

public class RunsDatabaseUtils {

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

    public static long InsertToDb(SQLiteDatabase db, Runs r){
        ContentValues cv = new ContentValues();
        cv.put(Contract.TABLE_RUNS.COLUMN_NAME_TIME, r.getTime());
        cv.put(Contract.TABLE_RUNS.COLUMN_NAME_MILES, r.getMiles());
        cv.put(Contract.TABLE_RUNS.COLUMN_NAME_CAL, r.getCal());
        cv.put(Contract.TABLE_RUNS.COLUMN_NAME_STEPS, r.getSteps());
        return db.insert(TABLE_NAME, null,cv);

    }


    public static void deleteAll(SQLiteDatabase db) {
        db.delete(TABLE_NAME, null, null);
    }
}

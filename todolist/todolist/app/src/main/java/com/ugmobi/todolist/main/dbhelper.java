package com.ugmobi.todolist.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
public class dbhelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    public static final String TASK = "TASK";
    public static final String TASK_STATUS = "TASK_STATUS";
    public static final String TASK_ID = "TASK_ID";
    public static final String TASK_TABLE_NAME = "TASK_TABLE_NAME";
    private static final String DROP_TASK_LIST_TABLE = " DROP TABLE IF EXISTS " + TASK_TABLE_NAME;
    private static final String SELECT_TASK_TABLE_LIST = " SELECT * FROM " + TASK_TABLE_NAME;
    public static final String CREATE_TASK_TABLE_LIST =
            "CREATE TABLE " + TASK_TABLE_NAME + "( " +
                    TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    TASK + " TEXT NOT NULL, " +
                    TASK_STATUS + " TEXT NOT NULL " +
                    ");";

    public dbhelper(@Nullable Context context) {
        super(context, "Task.db", null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASK_TABLE_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TASK_LIST_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public long addTASK(String task, String status) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK, task);
        values.put(TASK_STATUS, status);
        return database.insert(TASK_TABLE_NAME, null, values);
    }

    public int deleteTask(long tid) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(TASK_TABLE_NAME, TASK_ID + "=?", new String[]{String.valueOf(tid)});
    }

    public long updateTask(long tid, String task, String status) {
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK, task);
        values.put(TASK_STATUS, status);
        return database.update(TASK_TABLE_NAME, values, TASK_ID + "=?", new String[]{String.valueOf(tid)});
    }

    Cursor getTaskTable(){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery(SELECT_TASK_TABLE_LIST,null);
    }

}

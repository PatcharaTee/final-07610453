package com.example.speedrecords.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.speedrecords.model.SpeedRecord;

@Database(entities = {SpeedRecord.class}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    //private static final String TAG = "AppDatabase";
    private static final String DB_NAME = "speedrecord.db";

    private static AppDatabase sInstance;

    public static synchronized AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME).build();
        }
        return sInstance;
    }

    public abstract SpeedRecordDao speedRecordDao();
}
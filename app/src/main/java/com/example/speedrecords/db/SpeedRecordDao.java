package com.example.speedrecords.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.speedrecords.model.SpeedRecord;

@Dao
public interface SpeedRecordDao {

    @Query("SELECT * FROM speedrecords")
    SpeedRecord[] getAllSpeedRecords();

    @Insert
    void addRecord(SpeedRecord... speed_records);
}

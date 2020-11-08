package com.example.speedrecords;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.speedrecords.db.AppDatabase;
import com.example.speedrecords.model.SpeedRecord;
import com.example.speedrecords.util.AppExecutors;

public class AddRecordsActivity extends AppCompatActivity {

    private EditText duration;
    private EditText distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_records);

        distance = findViewById(R.id.meter_edittext);
        duration = findViewById(R.id.second_edittext);

        Button saveButt = findViewById(R.id.save_button);
        saveButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final double dDistance = Double.parseDouble(distance.getText().toString());
                final double dDuration = Double.parseDouble(duration.getText().toString());

                AppExecutors executors = new AppExecutors();
                executors.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        SpeedRecord record = new SpeedRecord(0, dDistance, dDuration);
                        AppDatabase db = AppDatabase.getInstance(AddRecordsActivity.this);
                        db.speedRecordDao().addRecord(record);
                        finish();
                    }
                });
            }
        });
    }
}
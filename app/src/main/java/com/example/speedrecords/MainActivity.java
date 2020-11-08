package com.example.speedrecords;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.speedrecords.adapter.SpeedRecordAdapter;
import com.example.speedrecords.db.AppDatabase;
import com.example.speedrecords.model.SpeedRecord;
import com.example.speedrecords.util.AppExecutors;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TextView total;
    private TextView over_limit;
    private int over_count = 0;

    @Override
    protected void onResume() {
        super.onResume();
        final AppExecutors executors = new AppExecutors();
        // worker thread
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getInstance(MainActivity.this);
                final SpeedRecord[] records = db.speedRecordDao().getAllSpeedRecords();
                for (SpeedRecord r : records) {
                    double dMeter = r.distance;
                    double dSecond = r.duration;
                    double dSpeedKM = (dMeter / 1000) / (dSecond / 3600);
                    if (dSpeedKM > 80) {
                        over_count++;
                    }
                }
                executors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        SpeedRecordAdapter adapter = new SpeedRecordAdapter(records);
                        mRecyclerView.setAdapter(adapter);
                        over_limit.setText(String.valueOf(over_count));
                        total.setText(String.valueOf(records.length));
                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        total = findViewById(R.id.total_count_textview);
        over_limit = findViewById(R.id.overlimit_count_textview);

        mRecyclerView = findViewById(R.id.speed_record_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddRecordsActivity.class);
                startActivity(i);
            }
        });
    }
}
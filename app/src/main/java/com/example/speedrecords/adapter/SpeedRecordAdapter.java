package com.example.speedrecords.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.speedrecords.R;
import com.example.speedrecords.model.SpeedRecord;

import java.util.Locale;

public class SpeedRecordAdapter extends RecyclerView.Adapter<SpeedRecordAdapter.MyViewHolder> {

    SpeedRecord[] speed_records;

    public SpeedRecordAdapter(SpeedRecord[] speed_records) {
        this.speed_records = speed_records;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_speedrecord, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        double dMeter = speed_records[position].distance;
        double dSecond = speed_records[position].duration;
        double dSpeedKM = (dMeter / 1000) / (dSecond / 3600);
        String result = String.format(
                Locale.getDefault(), "%.1f", dSpeedKM
        );
        holder.speed.setText(result);
        holder.distance.setText(String.valueOf(dMeter));
        holder.duration.setText(String.valueOf(dSecond));
        if (dSpeedKM > 80) {
            holder.alert.setImageResource(R.drawable.red_cow);
        }
    }

    @Override
    public int getItemCount() {
        return speed_records.length;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView speed;
        TextView distance;
        TextView duration;
        ImageView alert;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            speed = itemView.findViewById(R.id.speed_km_textview);
            distance = itemView.findViewById(R.id.distance_textview);
            duration = itemView.findViewById(R.id.duration_textview);
            alert = itemView.findViewById(R.id.alert_imageview);
        }
    }
}

package com.example.beatyourbestapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class HistoryAdapter extends ArrayAdapter<String> {

    public HistoryAdapter(Context context, List<String> historyList) {
        super(context, 0, historyList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_history, parent, false);
        }

        String workoutInfo = getItem(position);

        TextView textViewWorkoutName = itemView.findViewById(R.id.textViewWorkoutName);
        TextView textViewDateTime = itemView.findViewById(R.id.textViewDateTime);

        if (workoutInfo != null) {
            String[] parts = workoutInfo.split(" - ");
            textViewWorkoutName.setText(parts[0]);
            textViewDateTime.setText(parts[1]);
        }

        return itemView;
    }
}


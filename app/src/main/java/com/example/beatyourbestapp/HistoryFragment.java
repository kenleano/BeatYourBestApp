package com.example.beatyourbestapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link HistoryFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class HistoryFragment extends Fragment {

    private ListView listViewHistory;
    private ArrayAdapter<String> historyAdapter;
    private List<String> historyList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        listViewHistory = rootView.findViewById(R.id.listViewHistory);
        historyList = new ArrayList<>();
        historyAdapter = new HistoryAdapter(getContext(), historyList);
        listViewHistory.setAdapter(historyAdapter);

        // Load workout history data from Firebase
        loadWorkoutHistory();

        return rootView;
    }

    private void loadWorkoutHistory() {
        DatabaseReference historyRef = FirebaseDatabase.getInstance().getReference("WorkoutHistory");

        historyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                historyList.clear();

                for (DataSnapshot historySnapshot : dataSnapshot.getChildren()) {
                    WorkoutHistory workoutHistory = historySnapshot.getValue(WorkoutHistory.class);
                    if (workoutHistory != null) {
                        String workoutInfo = workoutHistory.getWorkoutName() + " - " + workoutHistory.getDateTime();
                        historyList.add(workoutInfo);
                    }
                }

                historyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });
    }
}
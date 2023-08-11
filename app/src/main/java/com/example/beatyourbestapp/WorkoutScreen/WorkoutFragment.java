package com.example.beatyourbestapp.WorkoutScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.beatyourbestapp.AddWorkout;
import com.example.beatyourbestapp.ExercisesScreen.ExercisesLayout;
import com.example.beatyourbestapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutFragment extends Fragment implements AddWorkout.AddWorkoutListener {
    private WorkoutAdapter workoutAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutFragment newInstance(String param1, String param2) {
        WorkoutFragment fragment = new WorkoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.workout_routines, container, false);

        // Find the "Add Workout" button inside the fragment's layout
        Button addWorkoutBtn = rootView.findViewById(R.id.addWorkoutBtn);

        // Set a click listener for the "Add Workout" button
        addWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the AddWorkout dialog when the button is clicked
                AddWorkout addWorkout = new AddWorkout();
                addWorkout.setListener((AddWorkout.AddWorkoutListener) WorkoutFragment.this); // Pass the WorkoutFragment as the listener
                addWorkout.show(getChildFragmentManager(), "Add Workout");
            }
        });

        //Retrieve data from database

        ArrayList<WorkoutItem> workoutList = new ArrayList<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Workouts");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                workoutList.clear();
                for(DataSnapshot workoutSnapshot : snapshot.getChildren()){
                    String workoutName = workoutSnapshot.getKey();
                    String workoutDay = workoutSnapshot.child("Day").getValue(String.class);
                    String workoutID = workoutSnapshot.child("ID").getValue(String.class);
                    WorkoutItem workoutItem = new WorkoutItem(workoutName, workoutDay, workoutID);
                    workoutList.add(workoutItem);
                }
                workoutAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Create the WorkoutAdapter and pass the WorkoutFragment as a reference
        workoutAdapter = new WorkoutAdapter(workoutList, this);

        // Set the adapter to the RecyclerView
        RecyclerView recyclerView = rootView.findViewById(R.id.workoutRoutinesRecyclerView);
        recyclerView.setAdapter(workoutAdapter);

        // Set the item click listener
        workoutAdapter.setOnItemClickListener(new WorkoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle the click event here
                // For example, you can navigate to a new XML file using Intent:
                //pass data to next activity
                WorkoutItem clickedItem = workoutList.get(position);
                String workoutName = clickedItem.getWorkoutName();
                String workoutID = clickedItem.getWorkoutId();
                Bundle bundle = new Bundle();
                bundle.putString("workoutName", workoutName);
                bundle.putString("workoutID", workoutID);

                Intent intent = new Intent(getContext(), ExercisesLayout.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // Set LinearLayoutManager to the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        return rootView;
    }
    public WorkoutAdapter getWorkoutAdapter() {
        return workoutAdapter;
    }

    public void onCancel() {
        // Implement the logic to handle the cancellation
    }
    public void onWorkoutAdded(String workoutName, String workoutDay) {
        // Implement the logic to handle the workout name when it's added
        // to the database
        workoutAdapter.addWorkout(new WorkoutItem(workoutName, workoutDay));
    }
}
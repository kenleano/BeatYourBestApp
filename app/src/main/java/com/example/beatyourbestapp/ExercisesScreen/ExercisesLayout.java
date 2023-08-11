package com.example.beatyourbestapp.ExercisesScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beatyourbestapp.Exercises;
import com.example.beatyourbestapp.R;
import com.example.beatyourbestapp.SearchExercises;
import com.example.beatyourbestapp.WorkoutScreen.WorkoutItem;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ExercisesLayout extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExercisesAdapter exercisesAdapter;
    private static ArrayList<Exercises> exerciseList = new ArrayList<>();
    private Button finishWorkoutButton;
    private Button cancelWorkoutButton;

    private Chronometer chronometer;
    private boolean running;
    public void startChronometer(View v) {
        if(!running) {
            chronometer.start();
            running = true;
        }
    }

    public void stopChronometer(View v) {
        finishWorkoutButton.setVisibility(View.GONE);
        cancelWorkoutButton.setVisibility(View.GONE);
        chronometer.setVisibility(View.GONE);
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        running = false;


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_layout);
        TextView workoutName = findViewById(R.id.workout_title);

        chronometer = findViewById(R.id.timeDisplay);

        finishWorkoutButton = findViewById(R.id.finish_workout_button);
        cancelWorkoutButton = findViewById(R.id.cancel_workout_button);

        // ... (rest of your code)

        // Set click listeners for finishWorkoutButton and cancelWorkoutButton
        finishWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopChronometer(v);
            }
        });

        cancelWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopChronometer(v);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String wName = bundle.getString("workoutName");
            String wID = bundle.getString("workoutID");

            if (wName != null && wID != null) {
                workoutName.setText(wName + " ID: " + wID);
            } else {
                // Handle the case where either wName or wID is null
                workoutName.setText(wName);
            }
        } else {
            // Handle the case where bundle is null
            workoutName.setText("Workout Name Null");
        }
//        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Workouts");
//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                exerciseList.clear();
//                for(DataSnapshot workoutSnapshot : snapshot.getChildren()){
//                    String workoutName = workoutSnapshot.getKey();
//                    String workoutDay = workoutSnapshot.child("Day").getValue(String.class);
//                    String workoutID = workoutSnapshot.child("ID").getValue(String.class);
//                    WorkoutItem workoutItem = new WorkoutItem(workoutName, workoutDay, workoutID);
//                    exerciseList.add(workoutItem);
//                }
//                exercisesAdapter.notifyDataSetChanged();
//            } @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        recyclerView = findViewById(R.id.exercises_recycler_view);
        exercisesAdapter = new ExercisesAdapter(exerciseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(exercisesAdapter);

        exercisesAdapter.notifyDataSetChanged();

    }

    public void addExercise(View view) {
        //exerciseList.add(exercise);
        Intent intent = new Intent(this, SearchExercises.class);
        startActivity(intent);
    }

    public static void insertExercise(Exercises exercise) {
        Bundle bundle = new Bundle();

        String workoutID = bundle.getString("workoutID");
        ; // Replace with the desired workout ID

        DatabaseReference workoutsRef = FirebaseDatabase.getInstance().getReference().child("Workouts");

        Query query = workoutsRef.orderByChild("ID").equalTo(workoutID);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // The snapshot should contain the matched workout node
                    DataSnapshot workoutSnapshot = snapshot.getChildren().iterator().next();

                    // Retrieve workout details
                    String workoutName = workoutSnapshot.child("Name").getValue(String.class);
                    String workoutDay = workoutSnapshot.child("Day").getValue(String.class);

                    // You can also retrieve exercises data if needed
                    DataSnapshot exercisesSnapshot = workoutSnapshot.child("Exercises");
                    for (DataSnapshot exerciseSnapshot : exercisesSnapshot.getChildren()) {
                        String exerciseName = exerciseSnapshot.child("Name").getValue(String.class);
                        // Retrieve other exercise properties as needed
                    }

                    // Now you have the workout details, you can use them as required
                } else {
                    // Workout with the specified ID was not found

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
        exerciseList.add(exercise);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle bundle = getIntent().getExtras();

        // Save your data to the outState bundle
//        outState.putString("workoutName", workoutName.getText().toString());
//        outState.putString("workoutID", workoutID); // Assuming workoutID is a String variable
        // ... Save any other data you want to retain
    }

    public void startExercise(View view) {
        finishWorkoutButton.setVisibility(View.VISIBLE);
        cancelWorkoutButton.setVisibility(View.VISIBLE);
        chronometer.setVisibility(View.VISIBLE);
        startChronometer(view);
    }
}

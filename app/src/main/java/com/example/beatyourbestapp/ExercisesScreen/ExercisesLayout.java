package com.example.beatyourbestapp.ExercisesScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExercisesLayout extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExercisesAdapter exercisesAdapter;
    private static ArrayList<Exercises> exerciseList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_layout);
        TextView workoutName = findViewById(R.id.workout_title);

        Bundle bundle = getIntent().getExtras();
        //String wName = bundle.getString("workoutName");
       // String wID = bundle.getString("workoutID");

        //workoutName.setText(wName + " " + wID);

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

        String workoutID = bundle.getString("workoutID");; // Replace with the desired workout ID

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

    public void startExercise(View view) {
    }

}

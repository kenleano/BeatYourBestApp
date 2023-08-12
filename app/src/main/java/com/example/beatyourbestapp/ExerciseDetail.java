package com.example.beatyourbestapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.beatyourbestapp.ExercisesScreen.ExercisesAdapter;
import com.example.beatyourbestapp.ExercisesScreen.ExercisesLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ExerciseDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        ImageView image = findViewById(R.id.detail_exercise_image);
        TextView target = findViewById(R.id.detail_exercise_target);
        TextView name = findViewById(R.id.detail_exercise_name);
        TextView equipment = findViewById(R.id.detail_exercise_equipment);



        Bundle bundle = getIntent().getExtras();

        String dName = bundle.getString("name");
        String dTarget = bundle.getString("target");
        String dEquipment = bundle.getString("equipment");
        String dGifUrl = bundle.getString("gif");

        Integer exerciseID = bundle.getInt("exerciseID");
        Glide.with(this)
                .load(dGifUrl) // Replace with the actual image URL
                .apply(RequestOptions.fitCenterTransform()) // Maintain aspect ratio
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(image);

        Picasso.get().load(dGifUrl).into(image);
        equipment.setText(dEquipment);
        target.setText(dTarget);
        name.setText(dName);

    }

//    public void addExercise(View view) {
//
//        Bundle bundle = getIntent().getExtras();
//        String exerciseName = bundle.getString("name");
//        String equipment = bundle.getString("equipment");
//        String target = bundle.getString("target");
//        int id = bundle.getInt("exerciseID");
//        Exercises newExercise = new Exercises(exerciseName, equipment, target, id);
//
//        ExercisesAdapter.addExercise(newExercise);
//        String message = newExercise.getExerciseName() +" ID: "+ newExercise.getId();
//
//        Toast.makeText(ExerciseDetail.this, message, Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(this, ExercisesLayout.class);
//        startActivity(intent);
//    }
public void addExercise(View view) {
    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    String workoutID = sharedPreferences.getString("selectedWorkoutID", null);

    Bundle bundle = getIntent().getExtras();
    String tempExercise = bundle.getString("name");
    if(tempExercise.equals("3/4 sit-up")){
        tempExercise = "Sit-up";
    }
    String equipment = bundle.getString("equipment");
    String target = bundle.getString("target");
    int id = bundle.getInt("exerciseID");

    // Generate a unique ID for the new exercise
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    final String exerciseName = tempExercise;
    rootRef.child("Workouts").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot workoutSnapshot : dataSnapshot.getChildren()) {
                String currentWorkoutID = workoutSnapshot.child("ID").getValue(String.class);
                if (currentWorkoutID != null && currentWorkoutID.equals(workoutID)) {
                    DatabaseReference exercisesRef = workoutSnapshot.child("Exercises").getRef();
                    String exerciseID = exercisesRef.push().getKey();

                    Exercises newExercise = new Exercises(exerciseName, equipment, target, id);
                    HashMap<String, Object> exerciseMap = new HashMap<>();
                    exerciseMap.put("exerciseName", exerciseName);
                    exerciseMap.put("equipment", equipment);
                    exerciseMap.put("target", target);
                    exerciseMap.put("id", id);

                    // Add the exercise to the specific workout's "Exercises" child
                    exercisesRef.child(exerciseName).setValue(exerciseMap);

                    // Rest of your code remains unchanged
                    ExercisesAdapter.addExercise(newExercise);
                    String message = "Added: " + workoutID + newExercise.getExerciseName() + " ID: " + newExercise.getId();
                    Toast.makeText(ExerciseDetail.this, message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ExerciseDetail.this, ExercisesLayout.class);
                    startActivity(intent);

                    break; // Exit the loop once the matching workout is found
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            // Handle errors here
        }
    });

}




}
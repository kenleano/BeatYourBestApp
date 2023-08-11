package com.example.beatyourbestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beatyourbestapp.ExercisesScreen.ExercisesAdapter;
import com.example.beatyourbestapp.ExercisesScreen.ExercisesLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

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

        Picasso.get().load(dGifUrl).into(image);
        equipment.setText(dEquipment);
        target.setText(dTarget);
        name.setText(dName);

    }

    public void addExercise(View view) {
//        Bundle bundle = getIntent().getExtras();
//        String exerciseName = bundle.getString("name");
//        String workoutID = bundle.getString("workoutID"); // Get the workout ID from the bundle
//        Integer exerciseID = bundle.getInt("exerciseID"); // Get the exercise ID from the bundle
//        // Update the "Exercises" child under the specific workout
//        DatabaseReference exercisesRef = FirebaseDatabase.getInstance().getReference()
//                .child("Workouts")
//                .child(workoutID)
//                .child("Exercises");
//
        Bundle bundle = getIntent().getExtras();
        String exerciseName = bundle.getString("name");
        String equipment = bundle.getString("equipment");
        String target = bundle.getString("target");
        int id = bundle.getInt("exerciseID");
        Exercises newExercise = new Exercises(exerciseName, equipment, target, id);

        ExercisesAdapter.addExercise(newExercise);
        String message = newExercise.getExerciseName() +" ID: "+ newExercise.getId();
        Toast.makeText(ExerciseDetail.this, message, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ExercisesLayout.class);
        startActivity(intent);
    }
}
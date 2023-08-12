package com.example.beatyourbestapp.ExercisesScreen;

import static com.example.beatyourbestapp.SearchExercisesAdapter.exercisesList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beatyourbestapp.ExerciseDetail;
import com.example.beatyourbestapp.Exercises;
import com.example.beatyourbestapp.MainMenu;
import com.example.beatyourbestapp.R;
import com.example.beatyourbestapp.SearchExercises;
import com.example.beatyourbestapp.WorkoutHistory;
import com.example.beatyourbestapp.WorkoutScreen.WorkoutItem;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ExercisesLayout extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExercisesAdapter exercisesAdapter;
    private static ArrayList<Exercises> exercisesList = new ArrayList<>();
    private Button finishWorkoutButton;
    private Button cancelWorkoutButton;

    private DatabaseReference workoutsRef;

    private Chronometer chronometer;
    private boolean running;

    public void startChronometer(View v) {
        if (!running) {
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


        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String workoutName = sharedPreferences.getString("selectedWorkoutName", "");

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Define the desired date and time format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String dateTime = dateFormat.format(currentDate);

        DatabaseReference historyRef = FirebaseDatabase.getInstance().getReference("WorkoutHistory");

        String historyId = historyRef.push().getKey();

        WorkoutHistory workoutHistory = new WorkoutHistory();
        workoutHistory.setWorkoutName(workoutName);
        workoutHistory.setExerciseName(exercisesList);
        workoutHistory.setDateTime(dateTime);

        historyRef.child(workoutName + " " + currentDate).setValue(workoutHistory);

        showPopupMessage("Workout Finished!");



    }

    public void cancelWorkout(View v) {
        finishWorkoutButton.setVisibility(View.GONE);
        cancelWorkoutButton.setVisibility(View.GONE);
        chronometer.setVisibility(View.GONE);
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        running = false;

        showPopupMessage("Workout Cancelled");
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
                cancelWorkout(v);
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String workoutID = sharedPreferences.getString("selectedWorkoutID", "");
        String workoutNameString = sharedPreferences.getString("selectedWorkoutName", "");

        editor.putString("workoutID", workoutID);
        editor.apply();
        workoutName.setText(workoutNameString);


        // ID of the workout you want to retrieve
        String workoutId = sharedPreferences.getString("selectedWorkoutID", "");

        // Retrieve "Exercises" array for the specified workout ID

        //ArrayList<Exercises> exercisesList = new ArrayList<>();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Workouts").child(workoutNameString).child("Exercises");

        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exercisesList.clear();
                for (DataSnapshot exerciseSnapshot : snapshot.getChildren()) {
                    String exerciseName = exerciseSnapshot.child("exerciseName").getValue(String.class);
                    String equipment = exerciseSnapshot.child("equipment").getValue(String.class);
                    String target = exerciseSnapshot.child("target").getValue(String.class);
                    String Reps = exerciseSnapshot.child("Sets").child("Reps").getValue(String.class);
                    String Weight = exerciseSnapshot.child("Sets").child("Weight").getValue(String.class);

                    if(Reps == null){
                        Reps = "0";
                    }
                    if(Weight == null){
                        Weight = "0";
                    }

                    String SetReps = Weight + "kg x " + Reps;
                    int id = exerciseSnapshot.child("id").getValue(Integer.class);

                    Exercises newExercise = new Exercises(exerciseName, equipment, target, id, SetReps);
                    exercisesList.add(newExercise);
                }
                exercisesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        recyclerView = findViewById(R.id.exercises_recycler_view);
        exercisesAdapter = new ExercisesAdapter(this, exercisesList); // if inside an activity


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(exercisesAdapter);

        exercisesAdapter.notifyDataSetChanged();

    }

    //Redirects to SearchExercises
    public void addExercise(View view) {

        Bundle bundle = getIntent().getExtras();
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
        exercisesList.add(exercise);
    }

    public void startExercise(View view) {
        finishWorkoutButton.setVisibility(View.VISIBLE);
        cancelWorkoutButton.setVisibility(View.VISIBLE);
        chronometer.setVisibility(View.VISIBLE);
        startChronometer(view);
    }

    public void backToWorkouts(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    private void showPopupMessage(String message) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_message, null);
        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        // Set animation style (optional)
        //popupWindow.setAnimationStyle(R.style.PopupAnimation);

        // Set the message text
        TextView textViewMessage = popupView.findViewById(R.id.textViewTitle);
        textViewMessage.setText(message);

        // Find and set the close button click listener
        Button btnClose = popupView.findViewById(R.id.btnClosePopup);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent intent = new Intent(ExercisesLayout.this, MainMenu.class);
                startActivity(intent);
            }
        });

        // Show the popup
        popupWindow.showAtLocation(findViewById(R.id.exercise_linear_layout), Gravity.CENTER, 0, 0);
    }

}

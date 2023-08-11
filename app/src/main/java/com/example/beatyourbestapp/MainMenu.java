package com.example.beatyourbestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.beatyourbestapp.WorkoutScreen.WorkoutAdapter;
import com.example.beatyourbestapp.WorkoutScreen.WorkoutFragment;
import com.example.beatyourbestapp.WorkoutScreen.WorkoutItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenu extends AppCompatActivity implements AddWorkout.AddWorkoutListener  {
    BottomNavigationView bottomNavigationView;
    HistoryFragment historyFragment = new HistoryFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    WorkoutFragment workoutFragment = new WorkoutFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new WorkoutFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.workouts:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new WorkoutFragment()).commit();
                    break;
                case R.id.profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
                    break;
                case R.id.history:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new HistoryFragment()).commit();
                    break;
            }
            return true;
        });

        int iconSizeInDp = 30; // Replace 60 with your desired size in dp
        int iconSizeInPixels = (int) (iconSizeInDp * getResources().getDisplayMetrics().density);
        bottomNavigationView.setItemIconSize(iconSizeInPixels);
    }

    public void addWorkout(View view) {
        AddWorkout addWorkout = new AddWorkout();
        addWorkout.setListener(this); // Pass the MainMenu activity as the listener
        addWorkout.show(getSupportFragmentManager(), "Add Workout");
    }

    @Override
    public void onWorkoutAdded(String workoutName, String workoutDay) {
        // Implement the logic to handle the workout name when it's added
        // to the database
        // Create a new WorkoutItem with the provided name and day
        WorkoutItem newWorkoutItem = new WorkoutItem(workoutName, workoutDay);

        // Get the WorkoutFragment instance
        WorkoutFragment workoutFragment = (WorkoutFragment) getSupportFragmentManager().findFragmentById(R.id.container);

        if (workoutFragment != null) {
            // Get the RecyclerView adapter from the WorkoutFragment
            WorkoutAdapter adapter = workoutFragment.getWorkoutAdapter();

            // Check if the adapter is not null
            if (adapter != null) {
                // Call the addWorkout method in the adapter to add the new workout
                adapter.addWorkout(newWorkoutItem);
            }
        }

        // Dismiss the dialog after handling the button click
        // (this can also be done in the WorkoutFragment after adding the workout)
        AddWorkout addWorkout = (AddWorkout) getSupportFragmentManager().findFragmentByTag("Add Workout");
        if (addWorkout != null) {
            addWorkout.dismiss();
        }
    }

    @Override
    public void onCancel() {
        // Implement the logic to handle the cancellation
    }

}

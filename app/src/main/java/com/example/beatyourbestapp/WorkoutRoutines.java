package com.example.beatyourbestapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WorkoutRoutines extends AppCompatActivity implements AddWorkout.AddWorkoutListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_routines);
    }

    public void addWorkout(View view) {
        openDialog();
//        PopUpClass popUpClass = new PopUpClass();
//        popUpClass.showPopupWindow(view);
    }
    public void openDialog() {
        AddWorkout addWorkout = new AddWorkout();
        addWorkout.show(getSupportFragmentManager(), "Add Workout");
    }

    @Override
    public void applyTexts(String workoutName) {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onWorkoutAdded(String workoutName) {

    }
}

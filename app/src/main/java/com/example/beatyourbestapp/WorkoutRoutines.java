package com.example.beatyourbestapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class WorkoutRoutines extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_routines);
    }

    public void addWorkout(View view) {

        PopUpClass popUpClass = new PopUpClass();
        popUpClass.showPopupWindow(view);
    }
}

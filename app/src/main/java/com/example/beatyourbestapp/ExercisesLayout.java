package com.example.beatyourbestapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ExercisesLayout extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_layout);
    }

    public void addExercise(View view) {
        Intent intent = new Intent(this, AddExercise.class);
        startActivity(intent);
    }

    public void startExercise(View view) {
    }































    
}
